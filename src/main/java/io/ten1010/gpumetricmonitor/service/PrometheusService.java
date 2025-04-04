package io.ten1010.gpumetricmonitor.service;

import io.ten1010.gpumetricmonitor.domain.DcgmMetric;
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

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrometheusService {
    private final WebClient webClient;

    @Value("${prometheus.query-range.step}")
    private String queryRangeStep;

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

    public Mono<DcgmMetric> fetchAllMetrics(List<String> queryList, long startTime, long endTime) {
        return Flux.fromIterable(queryList)
                .index()
                .parallel()
                .runOn(Schedulers.boundedElastic())
                .flatMap(tuple ->
                        getMetric(tuple.getT2(), startTime, endTime)
                                .map(result -> Tuples.of(tuple.getT1(), result))
                )
                .sequential()
                .sort(Comparator.comparing(Tuple2::getT1))
                .map(Tuple2::getT2)
                .collectList()
                .map(results -> DcgmMetric.of(
                        results.get(0),
                        results.get(1),
                        results.get(2)
                ));
    }

}
