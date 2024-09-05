package com.mvp.challenge.service.impl;

import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.domain.PurchaseResult;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.TooManyProductPurchaseException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.repository.UserRepository;
import com.mvp.challenge.service.PurchaseService;
import com.mvp.challenge.util.MvpUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PurchaseServiceImpl implements PurchaseService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  @Override
  public PurchaseResult buy(String userName, List<Purchase> purchaseInput)
      throws ProductNotExistsException,
          NotEnoughDepositException,
          ProductTemporarilyNotAvailable,
          UserCredentialException,
          TooManyProductPurchaseException {

    int total = 0;
    for (Purchase purchase : purchaseInput) {
      total +=
          purchase.getAmount() * productRepository.getByProduct(purchase.getProductId()).getCost();
    }

    User user = userRepository.get(userName);

    if (user.getDeposit() < total) {
      throw new NotEnoughDepositException(userName);
    }

    List<String> productList = new ArrayList<>();
    for (Purchase purchase : purchaseInput) {
      productRepository.buy(purchase);
      productList.add(purchase.getProductId());
    }

    user.setDeposit(user.getDeposit() - total);
    userRepository.mergeUser(user);

    return PurchaseResult.builder()
        .productList(productList)
        .total(total)
        .change(MvpUtil.getChangeMap(user.getDeposit() - total))
        .build();
  }
}
