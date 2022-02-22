package com.mvp.challenge.service;

import com.mvp.challenge.domain.Product;
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
import com.mvp.challenge.service.impl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    private PurchaseService purchaseService;

    private final String PRODUCT_NAME = "product";

    private final int productCost = 5;
    private final int productAvailability = 10;

    private final Product product = Product.builder().productName(PRODUCT_NAME).cost(productCost).amountAvailable(productAvailability).sellerId(1).build();

    @BeforeEach
    public void setUp() {
        purchaseService = new PurchaseServiceImpl(productRepository, userRepository);
    }

    @Test
    public void shouldBuyProduct() throws NotEnoughDepositException, ProductTemporarilyNotAvailable, UserCredentialException, ProductNotExistsException, TooManyProductPurchaseException {

        when(productRepository.getByProduct(product.getProductName())).thenReturn(product);
        String userName = "userName";

        int purchaseAmount = 1;

        when(userRepository.get(userName)).thenReturn(User.builder().deposit(productCost * purchaseAmount).build());

        PurchaseResult purchaseResult = purchaseService.buy(userName, List.of(Purchase.builder().productId(product.getProductName()).amount(purchaseAmount).build()));

        assertEquals(product.getCost() * purchaseAmount, purchaseResult.getTotal());
    }

    @Test
    public void shouldBNotBuyProductNotEnoughMoney() throws ProductNotExistsException {

        when(productRepository.getByProduct(product.getProductName())).thenReturn(product);
        String userName = "userName";

        int purchaseAmount = 2;

        when(userRepository.get(userName)).thenReturn(User.builder().deposit(productCost * (purchaseAmount - 1)).build());

        assertThrows(NotEnoughDepositException.class, () -> purchaseService.buy(userName, List.of(Purchase.builder().productId(product.getProductName()).amount(purchaseAmount).build())));
    }

    @Test
    public void shouldBNotBuyProductNotItemsAvailable() throws ProductNotExistsException {

        when(productRepository.getByProduct(product.getProductName())).thenReturn(product);
        String userName = "userName";

        when(userRepository.get(userName)).thenReturn(User.builder().deposit(productCost * productAvailability).build());

        assertThrows(NotEnoughDepositException.class, () -> purchaseService.buy(userName, List.of(Purchase.builder().productId(product.getProductName()).amount(productAvailability + 1).build())));
    }
}
