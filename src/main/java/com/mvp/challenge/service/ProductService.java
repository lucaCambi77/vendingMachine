package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.domain.PurchaseResult;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.UserCredentialException;

import java.util.List;

public interface ProductService {

    Product add(Product product) throws ProductAlreadyExistsException, CoinInputException;

    List<Product> getAll();

    Product getByProduct(String product) throws ProductNotExistsException;

    List<Product> getBySeller(int seller);

    Product delete(Product product);

    Product updateProduct(Product productUpdate) throws ProductNotExistsException;

    PurchaseResult buy(String userName, List<Purchase> purchaseInput) throws ProductNotExistsException, NotEnoughDepositException, ProductTemporarilyNotAvailable, UserCredentialException;
}
