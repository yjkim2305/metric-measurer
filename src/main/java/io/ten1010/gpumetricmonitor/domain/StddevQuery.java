package io.ten1010.gpumetricmonitor.domain;

public class StddevQuery implements PrometheusQuery {
    String metricName;
    double threshold;
    String label;

    @Override
    public String buildQuery(String queryRange) {
        return String.format(
                "avg by(gpu, instance) (stddev_over_time(%s[%s])) >= %.1f",
                metricName, queryRange, threshold
        );
    }

    @Override
    public String getLabel() {
        return label;
    }
}
