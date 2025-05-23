package io.ten1010.gpumetricmonitor.service;


import io.ten1010.gpumetricmonitor.common.util.TimeUtils;
import io.ten1010.gpumetricmonitor.domain.*;
import io.ten1010.gpumetricmonitor.domain.enums.MetricType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public Mono<Void> analizeThresholdMetrics(String start, String end, GpuThreshold gpuThreshold) {
        List<PrometheusQuery> prometheusQueryList = List.of(
                ThresholdQuery.createThresholdQuery(MetricType.GPU_UTIL.getMetricName(), gpuThreshold.getGpuUtilThreshold(), MetricType.GPU_UTIL.getLabel())
                , ThresholdQuery.createThresholdQuery(MetricType.GPU_TEMP.getMetricName(), gpuThreshold.getGpuTempThreshold(), MetricType.GPU_TEMP.getLabel())
                , ThresholdQuery.createThresholdQuery(MetricType.MEMORY_TEMP.getMetricName(), gpuThreshold.getGpuUtilThreshold(), MetricType.MEMORY_TEMP.getLabel())
        );

        return prometheusService.fetchAllMetrics(prometheusQueryList, TimeUtils.convertStartTimestamp(start), TimeUtils.convertEndTimestamp(end))
                .doOnSuccess(DcgmMetric::print)
                .then();
    }

    public Mono<Void> analizeStddevMetrics(String start, String end, GpuStddev gpuStddev) {
        List<PrometheusQuery> prometheusQueryList = List.of(
                StddevQuery.createStddevQuery(MetricType.GPU_UTIL.getMetricName(), gpuStddev.getGpuUtilStddev(), MetricType.GPU_UTIL.getLabel())
                , StddevQuery.createStddevQuery(MetricType.GPU_TEMP.getMetricName(), gpuStddev.getGpuTempStddev(), MetricType.GPU_TEMP.getLabel())
                , StddevQuery.createStddevQuery(MetricType.MEMORY_TEMP.getMetricName(), gpuStddev.getGpuUtilStddev(), MetricType.MEMORY_TEMP.getLabel())
        );

        return prometheusService.fetchAllMetrics(prometheusQueryList, TimeUtils.convertStartTimestamp(start), TimeUtils.convertEndTimestamp(end))
                .doOnSuccess(DcgmMetric::print)
                .then();
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            //사용자로부터 설정된 임계치 값
            GpuThreshold gpuThreshold = GpuThreshold.createThreshold(
                    90, "이상",
                    50, "이하",
                    0, "초과");
            //사용자로부터 설정된 표준편차 값
            GpuStddev gpuStddev = GpuStddev.createStddev(
                    30, "이상",
                    20, "이상",
                    10, "이상");

            log.info("시작 날짜를 입력해주세요(yyyymmdd)");
            String startTime = br.readLine();
            log.info("끝 날짜를 입력해주세요(yyyymmdd)");
            String endTime = br.readLine();
            analizeThresholdMetrics(startTime, endTime, gpuThreshold).block();
            analizeStddevMetrics(startTime, endTime, gpuStddev).block();
        }
    }


}
