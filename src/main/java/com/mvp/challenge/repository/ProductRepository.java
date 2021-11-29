package com.mvp.challenge.repository;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class ProductRepository {

    private Map<String, Product> productMap = new HashMap<>();

    public Product add(Product product) throws ProductAlreadyExistsException, CoinInputException {
        if (product.getCost() >= 5 && product.getCost() % 5 == 0) {
            if (!productMap.containsKey(product.getProductName())) {
                productMap.put(product.getProductName(), product);
                return product;
            } else {
                throw new ProductAlreadyExistsException(product.getProductName());
            }
        } else {
            throw new CoinInputException();
        }
    }

    public List<Product> getAll() {
        return new ArrayList<>(productMap.values());
    }

    public Product getByProduct(String product) throws ProductNotExistsException {
        return Optional.ofNullable(productMap.get(product)).orElseThrow(() -> new ProductNotExistsException(product));
    }

    public List<Product> getBySeller(int seller) {
        return productMap.values().stream().filter(product -> product.getSellerId().equals(seller)).collect(Collectors.toList());
    }

    public Product delete(Product product) {
        return productMap.remove(product.getProductName());
    }

    public Product updateProduct(Product product) throws ProductNotExistsException {
        update(product);
        return product;
    }

    public void buy(Purchase purchaseInput) throws ProductNotExistsException, ProductTemporarilyNotAvailable {
        Product product = getByProduct(purchaseInput.getProductId());

        if (product.getAmountAvailable() <= 0)
            throw new ProductTemporarilyNotAvailable(product.getProductName());

        product.setAmountAvailable(product.getAmountAvailable() - purchaseInput.getAmount());
        update(product);
    }

    private void update(Product product) {
        productMap.put(product.getProductName(), product);
    }

}
