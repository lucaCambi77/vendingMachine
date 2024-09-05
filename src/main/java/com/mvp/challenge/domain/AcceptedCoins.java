package com.mvp.challenge.domain;

import java.util.Arrays;

public enum AcceptedCoins {
    FIVE(5),
    TEN(10),
    TWENTY(20),
    FIFTY(50),
    HUNDRED(100);

    final int value;

    AcceptedCoins(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static AcceptedCoins getByValue(int value) {
        return Arrays.stream(AcceptedCoins.values()).filter(v -> v.getValue() - value == 0).findFirst().orElse(null);
    }
}
