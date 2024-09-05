package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;

public interface ProductService {

    Product add(Product product) throws ProductAlreadyExistsException, CoinInputException;

    Product delete(Product product);

    Product updateProduct(Product productUpdate) throws ProductNotExistsException;
}
