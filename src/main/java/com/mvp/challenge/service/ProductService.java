package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;

import java.util.List;

public interface ProductService {

    Product add(Product product) throws ProductAlreadyExistsException, CoinInputException;

    List<Product> getAll();

    Product getByProduct(String product) throws ProductNotExistsException;

    List<Product> getBySeller(int seller);

    Product delete(Product product);

    Product updateProduct(Product productUpdate) throws ProductNotExistsException;
}
