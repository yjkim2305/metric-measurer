package io.ten1010.gpumetricmonitor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThresholdQuery implements PrometheusQuery {
    String metricName;
    double threshold;
    String label;

    @Builder
    private ThresholdQuery(String metricName, double threshold, String label) {
        this.metricName = metricName;
        this.threshold = threshold;
        this.label = label;
    }

    public static ThresholdQuery createThresholdQuery(String metricName, double threshold, String label) {
        return ThresholdQuery.builder()
                .metricName(metricName)
                .threshold(threshold)
                .label(label)
                .build();
    }


    @Override
    public String buildQuery(String queryRange) {
        return String.format(
                "avg by(gpu, instance) (avg_over_time(%s[%s])) >= %.1f",
                metricName, queryRange, threshold
        );
    }

    @Override
    public String getLabel() {
        return label;
    }
}
