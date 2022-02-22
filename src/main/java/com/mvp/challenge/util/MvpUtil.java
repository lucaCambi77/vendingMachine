package com.mvp.challenge.util;

import com.mvp.challenge.domain.AcceptedCoins;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MvpUtil {
    private static final List<Integer> list = Arrays.stream(AcceptedCoins.values()).map(AcceptedCoins::getValue).sorted(Comparator.reverseOrder()).collect(Collectors.toList());

    public static Map<AcceptedCoins, Integer> getChangeMap(int change) {

        if (change == 0)
            return new HashMap<>();

        Map<AcceptedCoins, Integer> changeMap = new HashMap<>();

        for (Integer value : list) {
            if (value > change)
                continue;

            int rem = change / value;

            change = change - value * rem;
            changeMap.put(AcceptedCoins.getByValue(value), rem);
        }

        return changeMap;
    }
}
