package io.ten1010.gpumetricmonitor.common.parser;

import io.ten1010.gpumetricmonitor.domain.Metric;
import org.json.JSONException;

import java.util.List;

public interface MetricParser {
    void parser(String json) throws JSONException;
    List<Metric> getMetricValues();
}
