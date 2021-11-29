package com.mvp.challenge.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseResult {
    private int total;
    private List<String> productList = new ArrayList<>();
    private Map<AcceptedCoins, Integer> change = new HashMap<>();
}
