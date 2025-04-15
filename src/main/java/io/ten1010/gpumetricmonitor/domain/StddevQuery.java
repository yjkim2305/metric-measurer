package io.ten1010.gpumetricmonitor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StddevQuery implements PrometheusQuery {
    String metricName;
    CompareMetricValue stddev;
    String label;

    @Builder
    private StddevQuery(String metricName, CompareMetricValue stddev, String label) {
        this.metricName = metricName;
        this.stddev = stddev;
        this.label = label;
    }

    public static StddevQuery createStddevQuery(String metricName, CompareMetricValue stddev, String label) {
        return StddevQuery.builder()
                .metricName(metricName)
                .stddev(stddev)
                .label(label)
                .build();
    }

    @Override
    public String buildQuery(String queryRange) {
        return String.format(
                "avg by(gpu, instance) (stddev_over_time(%s[%s])) %s %.1f",
                metricName, queryRange, stddev.getCompareType().getCompareType(), stddev.getValue()
        );
    }

    @Override
    public String getLabel() {
        return label;
    }
}
