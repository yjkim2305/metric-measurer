package io.ten1010.gpumetricmonitor.domain.enums;

import lombok.Getter;

@Getter
public enum MetricType {
    GPU_UTIL("DCGM_FI_DEV_GPU_UTIL", "GPU 사용률"),
    GPU_TEMP("DCGM_FI_DEV_GPU_TEMP", "GPU 온도"),
    MEMORY_TEMP("DCGM_FI_DEV_MEMORY_TEMP", "메모리 온도"),
    POWER_USAGE("DCGM_FI_DEV_POWER_USAGE", "GPU 전력소비"),
    GPU_CLOCK("DCGM_FI_DEV_GPU_CLOCK", "GPU 클럭속도")
    ;

    private final String metricName;
    private final String label;

    MetricType(String metricName, String label) {
        this.metricName = metricName;
        this.label = label;
    }
}
