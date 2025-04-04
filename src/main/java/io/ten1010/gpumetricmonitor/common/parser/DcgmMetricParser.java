package io.ten1010.gpumetricmonitor.common.parser;


import io.ten1010.gpumetricmonitor.domain.Metric;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
public class DcgmMetricParser implements MetricParser {

    private final List<Metric> averageMetricValueList = new ArrayList<>();

    @Override
    public void parser(String json) throws JSONException {
        log.debug(json);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject data = jsonObject.getJSONObject("data");
        JSONArray results = data.getJSONArray("result");

        if (results.isEmpty()) {
            return;
        }

        for (int i = 0; i < results.length(); i++) {
            JSONObject metric = results.getJSONObject(i).getJSONObject("metric");
            JSONArray values = results.getJSONObject(i).getJSONArray("values");

            double sum = IntStream.range(0, values.length())
                    .mapToDouble(j -> {
                        try {
                            JSONArray pair = values.getJSONArray(j);
                            return Double.parseDouble(pair.getString(1));
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .sum();

            String metricName = String.format("%s_gpu%s", metric.getString("instance"), metric.getString("gpu"));

            averageMetricValueList.add(Metric.of(metricName, sum / values.length()));
        }
        averageMetricValueList.sort(Comparator.comparing(Metric::getMetricName));
    }

    @Override
    public List<Metric> getMetricValues() {
        return averageMetricValueList;
    }
}
