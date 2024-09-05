package com.mvp.challenge.domain;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseResult {
  private int total;
  private List<String> productList;
  private Map<AcceptedCoins, Integer> change;
}
