package io.ten1010.gpumetricmonitor.service;

import io.ten1010.gpumetricmonitor.domain.DcgmMetric;
import io.ten1010.gpumetricmonitor.domain.PrometheusQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrometheusService {
    private final WebClient webClient;

    @Value("${prometheus.query-range.step}")
    private String queryRangeStep;

    @Value("${prometheus.query.range}")
    private String queryRange;

    public Mono<String> getMetric(String query, long startTime, long endTime) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/v1/query_range")
                        .queryParam("query", query)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .queryParam("step", queryRangeStep)
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<DcgmMetric> fetchAllMetrics(List<PrometheusQuery> queryList, long startTime, long endTime) {
        return Flux.fromIterable(queryList)
                .index()
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(tuple -> {
                            long index = tuple.getT1();
                            PrometheusQuery query = tuple.getT2();
                            return getMetric(query.buildQuery(queryRange), startTime, endTime)
                                    .map(result -> Tuples.of(query.getLabel(), result));
                        }
                )
                .sequential()
                .collectMap(Tuple2::getT1, Tuple2::getT2)
                .map(DcgmMetric::createByMap);

    }

}
