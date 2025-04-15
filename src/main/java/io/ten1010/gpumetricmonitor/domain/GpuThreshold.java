package io.ten1010.gpumetricmonitor.domain;

import io.ten1010.gpumetricmonitor.domain.enums.CompareType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GpuThreshold {
    private CompareMetricValue gpuUtilThreshold;
    private CompareMetricValue gpuTempThreshold;
    private CompareMetricValue memTempThreshold;

    @Builder
    private GpuThreshold(CompareMetricValue gpuUtilThreshold, CompareMetricValue gpuTempThreshold, CompareMetricValue memTempThreshold) {
        this.gpuUtilThreshold = gpuUtilThreshold;
        this.gpuTempThreshold = gpuTempThreshold;
        this.memTempThreshold = memTempThreshold;
    }


    public static GpuThreshold createThreshold(
            double gpuUtilThreshold, String gpuUtilThresholdCompare
            , double gpuTempThreshold, String gpuTempThresholdCompare
            , double memTempThreshold, String memTempThresholdCompare) {

        return GpuThreshold.builder()
                .gpuUtilThreshold(CompareMetricValue.create(gpuUtilThreshold, CompareType.fromByCmpareValue(gpuUtilThresholdCompare)))
                .gpuTempThreshold(CompareMetricValue.create(gpuTempThreshold, CompareType.fromByCmpareValue(gpuTempThresholdCompare)))
                .memTempThreshold(CompareMetricValue.create(memTempThreshold, CompareType.fromByCmpareValue(memTempThresholdCompare)))
                .build();
    }
}
