package io.ten1010.gpumetricmonitor.domain;

import io.ten1010.gpumetricmonitor.domain.enums.CompareType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompareMetricValue {
    private double value;
    private CompareType compareType;


    @Builder
    private CompareMetricValue(double value, CompareType compareType) {
        this.value = value;
        this.compareType = compareType;
    }

    public static CompareMetricValue create(double value, CompareType compareType) {
        return CompareMetricValue.builder()
                .value(value)
                .compareType(compareType)
                .build();
    }
}
