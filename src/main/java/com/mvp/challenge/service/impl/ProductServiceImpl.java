package com.mvp.challenge.service.impl;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  @Override
  public Product add(Product product) throws ProductAlreadyExistsException, CoinInputException {
    return productRepository.add(product);
  }

  @Override
  public Product delete(Product product) {
    return productRepository.delete(product);
  }

  @Override
  public Product updateProduct(Product productUpdate) {
    return productRepository.updateProduct(productUpdate);
  }
}
