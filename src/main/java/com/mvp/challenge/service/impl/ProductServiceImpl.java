package com.mvp.challenge.service.impl;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.repository.UserRepository;
import com.mvp.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Product add(Product product) throws ProductAlreadyExistsException, CoinInputException {
        return productRepository.add(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.getAll();
    }

    @Override
    public Product getByProduct(String product) throws ProductNotExistsException {
        return productRepository.getByProduct(product);
    }

    @Override
    public List<Product> getBySeller(int seller) {
        return productRepository.getBySeller(seller);
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
