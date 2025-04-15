package io.ten1010.gpumetricmonitor.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CompareType {
    GREATER_THAN_OR_EQUAL(">=", "이상"),
    LESS_THAN_OR_EQUAL("<=", "이하"),
    GREATER_THAN(">", "초과"),
    LESS_THAN("<", "미만"),
    ;

    private final String compareType;
    private final String compareValue;

    CompareType(String compareType, String compareValue) {
        this.compareType = compareType;
        this.compareValue = compareValue;
    }

    public static CompareType fromByCmpareValue(String compareValue) {
        return Arrays.stream(values())
                .filter(type -> type.getCompareValue().equals(compareValue))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown CompareType: " + compareValue));
    }
}
