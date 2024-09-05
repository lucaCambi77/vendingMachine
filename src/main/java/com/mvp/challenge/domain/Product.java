package com.mvp.challenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(of = {"productName", "sellerId"})
public class Product {
  private Integer amountAvailable;
  private Integer cost;
  private String productName;
  private Integer sellerId;
}
