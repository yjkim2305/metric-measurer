package io.ten1010.gpumetricmonitor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Metric {
    private String metricName;
    private double value;

    @Builder
    private Metric(String metricName, double value) {
        this.metricName = metricName;
        this.value = value;
    }

    public static Metric of(String metricName, double value) {
        return Metric.builder()
                .metricName(metricName)
                .value(value)
                .build();
    }
}
