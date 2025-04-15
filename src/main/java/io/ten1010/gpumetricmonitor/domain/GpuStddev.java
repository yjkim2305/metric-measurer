package io.ten1010.gpumetricmonitor.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GpuStddev {
    private double gpuUtilStddev;
    private double gpuTempStddev;
    private double memTempStddev;

    @Builder
    private GpuStddev(double gpuUtilStddev, double gpuTempStddev, double memTempStddev) {
        this.gpuUtilStddev = gpuUtilStddev;
        this.gpuTempStddev = gpuTempStddev;
        this.memTempStddev = memTempStddev;
    }

    public static GpuStddev createStddev(double gpuUtilStddev, double gpuTempStddev, double memTempStddev) {
        return GpuStddev.builder()
                .gpuUtilStddev(gpuUtilStddev)
                .gpuTempStddev(gpuTempStddev)
                .memTempStddev(memTempStddev)
                .build();
    }

}
