package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.domain.PurchaseResult;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.TooManyProductPurchaseException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.repository.UserRepository;
import com.mvp.challenge.util.MvpUtil;
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
    public Product updateProduct(Product productUpdate) throws ProductNotExistsException {
        return productRepository.updateProduct(productUpdate);
    }

    @Override
    public PurchaseResult buy(String userName, List<Purchase> purchaseInput) throws ProductNotExistsException, NotEnoughDepositException, ProductTemporarilyNotAvailable, UserCredentialException, TooManyProductPurchaseException {

        int total = 0;
        for (Purchase purchase : purchaseInput) {
            total += purchase.getAmount() * productRepository.getByProduct(purchase.getProductId()).getCost();
        }

        User user = userRepository.get(userName);

        if (user.getDeposit() < total) {
            throw new NotEnoughDepositException("User " + userName + " has not enough deposit for this purchase");
        }

        PurchaseResult purchaseResult = new PurchaseResult();

        for (Purchase purchase : purchaseInput) {
            productRepository.buy(purchase);
            purchaseResult.getProductList().add(purchase.getProductId());
        }

        purchaseResult.setTotal(total);
        purchaseResult.setChange(MvpUtil.getChangeMap(user.getDeposit() - total));

        user.setDeposit(user.getDeposit() - total);
        userRepository.mergeUser(user);

        return purchaseResult;
    }
}
