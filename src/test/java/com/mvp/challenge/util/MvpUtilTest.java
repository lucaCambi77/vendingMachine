package com.mvp.challenge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mvp.challenge.domain.AcceptedCoins;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class MvpUtilTest {

    @Test
    public void shouldGiveCorrectChange() {

        Map<AcceptedCoins, Integer> map = MvpUtil.getChangeMap(50);

        assertEquals(1, map.get(AcceptedCoins.FIFTY));

        map = MvpUtil.getChangeMap(10);

        assertEquals(1, map.get(AcceptedCoins.TEN));

        map = MvpUtil.getChangeMap(25);

        assertEquals(1, map.get(AcceptedCoins.TWENTY));
        assertEquals(1, map.get(AcceptedCoins.FIVE));

        map = MvpUtil.getChangeMap(15);

        assertEquals(1, map.get(AcceptedCoins.TEN));
        assertEquals(1, map.get(AcceptedCoins.FIVE));

        map = MvpUtil.getChangeMap(5);

        assertEquals(1, map.get(AcceptedCoins.FIVE));
    }
}
