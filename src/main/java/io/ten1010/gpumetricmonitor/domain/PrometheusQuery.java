package io.ten1010.gpumetricmonitor.domain;

public interface PrometheusQuery {
    String buildQuery(String queryRange);
    String getLabel();
}
