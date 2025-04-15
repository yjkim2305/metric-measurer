package io.ten1010.gpumetricmonitor.domain;

import io.ten1010.gpumetricmonitor.common.parser.DcgmMetricParser;
import io.ten1010.gpumetricmonitor.common.parser.MetricParser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DcgmMetric {
    private Map<String, List<Metric>> dcgmMetricMap = new HashMap<>();

    @Builder
    private DcgmMetric(Map<String, List<Metric>> dcgmMetricMap) {
        this.dcgmMetricMap = dcgmMetricMap;
    }


    public static DcgmMetric ofmap(Map<String, String> dcgmMetricMap) {
        Map<String, List<Metric>> parsedMetrics = new HashMap<>();

        dcgmMetricMap.forEach((label, json) -> {
            MetricParser parser = new DcgmMetricParser();
            parser.parser(json);
            parsedMetrics.put(label, parser.getMetricValues());
        });

        return DcgmMetric.builder()
                .dcgmMetricMap(parsedMetrics)
                .build();
    }

    public void print() {
        dcgmMetricMap.forEach((key, valueList) -> {
            log.info("Metric Group: {}", key);
            valueList.forEach(metric ->
                    log.info("{} = {}", metric.getMetricName(), metric.getValue()));
        });
    }
}
