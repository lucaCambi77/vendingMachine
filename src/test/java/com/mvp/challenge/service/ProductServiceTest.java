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
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    private ProductService productService;

    private final String PRDDUCT_NAME = "product";

    private final int productCost = 5;
    private final int productAvailability = 10;

    private final Product product = Product.builder().productName(PRDDUCT_NAME).cost(productCost).amountAvailable(productAvailability).sellerId(1).build();

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl(productRepository, userRepository);
    }

    @Test
    public void shouldAddProduct() throws CoinInputException, ProductAlreadyExistsException {
        productService.add(product);

        verify(productRepository).add(product);
    }

    @Test
    public void shouldNotAddProductAlreadyExists() throws CoinInputException, ProductAlreadyExistsException {
        doThrow(new ProductAlreadyExistsException(product.getProductName())).when(productRepository).add(product);

        assertThrows(ProductAlreadyExistsException.class, () -> productService.add(product));

        verify(productRepository).add(product);
    }

    @Test
    public void shouldBuyProduct() throws NotEnoughDepositException, ProductTemporarilyNotAvailable, UserCredentialException, ProductNotExistsException {

        when(productRepository.getByProduct(product.getProductName())).thenReturn(product);
        String userName = "userName";

        int purchaseAmount = 1;

        when(userRepository.get(userName)).thenReturn(User.builder().deposit(productCost * purchaseAmount).build());

        PurchaseResult purchaseResult = productService.buy(userName, List.of(Purchase.builder().productId(product.getProductName()).amount(purchaseAmount).build()));

        assertEquals(product.getCost() * purchaseAmount, purchaseResult.getTotal());
    }

    @Test
    public void shouldBNotBuyProductNotEnoughMoney() throws ProductNotExistsException {

        when(productRepository.getByProduct(product.getProductName())).thenReturn(product);
        String userName = "userName";

        int purchaseAmount = 2;

        when(userRepository.get(userName)).thenReturn(User.builder().deposit(productCost * (purchaseAmount - 1)).build());

        assertThrows(NotEnoughDepositException.class, () -> productService.buy(userName, List.of(Purchase.builder().productId(product.getProductName()).amount(purchaseAmount).build())));
    }

    @Test
    public void shouldDeleteProduct() {
        productService.delete(product);
        verify(productRepository).delete(product);
    }

    @Test
    public void shouldUpdateProduct() throws ProductNotExistsException {
        Product productUpdate = Product.builder().productName(PRDDUCT_NAME).cost(productCost + 1).amountAvailable(productAvailability).sellerId(1).build();

        productService.updateProduct(productUpdate);

        verify(productRepository).updateProduct(productUpdate);
    }
}
