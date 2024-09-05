package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService {

  private final ProductRepository productRepository;

  public Product add(Product product) throws ProductAlreadyExistsException, CoinInputException {
    return productRepository.add(product);
  }

  public Product delete(Product product) {
    return productRepository.delete(product);
  }

  public Product updateProduct(Product productUpdate) {
    return productRepository.updateProduct(productUpdate);
  }
}
