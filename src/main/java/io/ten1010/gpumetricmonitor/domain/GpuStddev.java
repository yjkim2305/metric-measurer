package io.ten1010.gpumetricmonitor.domain;

import io.ten1010.gpumetricmonitor.domain.enums.CompareType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GpuStddev {
    private CompareMetricValue gpuUtilStddev;
    private CompareMetricValue gpuTempStddev;
    private CompareMetricValue memTempStddev;

    @Builder
    private GpuStddev(CompareMetricValue gpuUtilStddev, CompareMetricValue gpuTempStddev, CompareMetricValue memTempStddev) {
        this.gpuUtilStddev = gpuUtilStddev;
        this.gpuTempStddev = gpuTempStddev;
        this.memTempStddev = memTempStddev;
    }

    public static GpuStddev createStddev(
            double gpuUtilStddev, String gpuUtilStddevCompare,
            double gpuTempStddev, String gpuTempStddevCompare,
            double memTempStddev, String memTempStddevCompare) {
        return GpuStddev.builder()
                .gpuUtilStddev(CompareMetricValue.create(gpuUtilStddev, CompareType.fromByCmpareValue(gpuUtilStddevCompare)))
                .gpuTempStddev(CompareMetricValue.create(gpuTempStddev, CompareType.fromByCmpareValue(gpuTempStddevCompare)))
                .memTempStddev(CompareMetricValue.create(memTempStddev, CompareType.fromByCmpareValue(memTempStddevCompare)))
                .build();
    }

}
