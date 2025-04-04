package io.ten1010.gpumetricmonitor.domain;

import io.ten1010.gpumetricmonitor.common.parser.DcgmMetricParser;
import io.ten1010.gpumetricmonitor.common.parser.MetricParser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DcgmMetric {
    private List<Metric> dcgmGpuUtilList;
    private List<Metric> dcgmGpuTempList;
    private List<Metric> dcgmMemTempList;

    @Builder
    private DcgmMetric(List<Metric> dcgmGpuUtil, List<Metric> dcgmGpuTemp, List<Metric> dcgmMemTemp) {
        this.dcgmGpuUtilList = dcgmGpuUtil;
        this.dcgmGpuTempList = dcgmGpuTemp;
        this.dcgmMemTempList = dcgmMemTemp;
    }

    public static DcgmMetric of(String dcgmGpuUtilJson, String dcgmGpuTempJson, String dcgmMemTempJson) {
        MetricParser gpuUtilParser = new DcgmMetricParser();
        MetricParser gpuTempParser = new DcgmMetricParser();
        MetricParser memTempParser = new DcgmMetricParser();

        gpuUtilParser.parser(dcgmGpuUtilJson);
        gpuTempParser.parser(dcgmGpuTempJson);
        memTempParser.parser(dcgmMemTempJson);

        return DcgmMetric.builder()
                .dcgmGpuUtil(gpuUtilParser.getMetricValues())
                .dcgmGpuTemp(gpuTempParser.getMetricValues())
                .dcgmMemTemp(memTempParser.getMetricValues())
                .build();
    }

    public void print() {
        log.info("GPU 평균 사용률");
        dcgmGpuUtilList.forEach(dcgmGpuUtil -> {
            log.info("{} = {}", dcgmGpuUtil.getMetricName(), dcgmGpuUtil.getValue());
        });

        log.info("GPU 평균 온도");
        dcgmGpuTempList.forEach(dcgmGpuTemp -> {
            log.info("{} = {}", dcgmGpuTemp.getMetricName(), dcgmGpuTemp.getValue());
        });

        log.info("메모리 평균 온도");
        dcgmMemTempList.forEach(dcgmMemTemp -> {
            log.info("{} = {}", dcgmMemTemp.getMetricName(), dcgmMemTemp.getValue());
        });
    }
}
