package io.ten1010.gpumetricmonitor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GpuThreshold {
    private double gpuUtilThreshold;
    private double gpuTempThreshold;
    private double memTempThreshold;

    @Builder
    private GpuThreshold(double gpuUtilThreshold, double gpuTempThreshold, double memTempThreshold) {
        this.gpuUtilThreshold = gpuUtilThreshold;
        this.gpuTempThreshold = gpuTempThreshold;
        this.memTempThreshold = memTempThreshold;
    }


    public static GpuThreshold createThreshold(double gpuUtilThreshold, double gpuTempThreshold, double memTempThreshold) {
        return GpuThreshold.builder()
                .gpuUtilThreshold(gpuUtilThreshold)
                .gpuTempThreshold(gpuTempThreshold)
                .memTempThreshold(memTempThreshold)
                .build();
    }
}
