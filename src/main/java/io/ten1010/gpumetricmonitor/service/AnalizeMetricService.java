package io.ten1010.gpumetricmonitor.service;


import io.ten1010.gpumetricmonitor.common.util.TimeUtils;
import io.ten1010.gpumetricmonitor.domain.DcgmMetric;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnalizeMetricService implements ApplicationRunner {
    private final PrometheusService prometheusService;

    @Value("${prometheus.query.range}")
    private String queryRange;

    public Mono<Void> analizeMetrics(String start, String end) {
        long startTime = TimeUtils.convertStartTimestamp(start);
        long endTime = TimeUtils.convertEndTimestamp(end);

        log.debug("Start: {}, End: {}", startTime, endTime);

        //GPU 평균 사용률
        String GPU_UTIL_QUERY = "avg(avg_over_time(DCGM_FI_DEV_GPU_UTIL[" + queryRange + "])) by(gpu, instance)";
        //GPU 평균 온도
        String GPU_TEMP_QUERY = "avg(avg_over_time(DCGM_FI_DEV_GPU_TEMP[" + queryRange + "])) by(gpu, instance)";
        //메모리 평균 온도
        String MEM_TEMP_QUERY = "avg(avg_over_time(DCGM_FI_DEV_MEMORY_TEMP[" + queryRange + "])) by(gpu, instance)";

        List<String> queryList = List.of(GPU_UTIL_QUERY, GPU_TEMP_QUERY, MEM_TEMP_QUERY);

        return prometheusService.fetchAllMetrics(queryList, startTime, endTime)
                .doOnSuccess(DcgmMetric::print)
                .then();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            log.info("시작 날짜를 입력해주세요(yyyymmdd)");
            String startTime = br.readLine();
            log.info("끝 날짜를 입력해주세요(yyyymmdd)");
            String endTime = br.readLine();
            analizeMetrics(startTime, endTime).block();
        }
    }
}
