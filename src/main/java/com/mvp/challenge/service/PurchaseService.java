package com.mvp.challenge.service;

import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.domain.PurchaseResult;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.TooManyProductPurchaseException;
import com.mvp.challenge.exception.UserCredentialException;

import java.util.List;

public interface PurchaseService {
    PurchaseResult buy(String userName, List<Purchase> purchaseInput) throws ProductNotExistsException, NotEnoughDepositException, ProductTemporarilyNotAvailable, UserCredentialException, TooManyProductPurchaseException;
}
