package com.mvp.challenge.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.repository.ProductRepository;
import com.mvp.challenge.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

  @Mock private ProductRepository productRepository;

  private ProductService productService;

  private final String PRODUCT_NAME = "product";

  private final int productCost = 5;

  private final int productAvailability = 10;

  private final Product product =
      Product.builder()
          .productName(PRODUCT_NAME)
          .cost(productCost)
          .amountAvailable(productAvailability)
          .sellerId(1)
          .build();

  @BeforeEach
  public void setUp() {
    productService = new ProductServiceImpl(productRepository);
  }

  @Test
  public void shouldAddProduct() throws CoinInputException, ProductAlreadyExistsException {
    productService.add(product);

    verify(productRepository).add(product);
  }

  @Test
  public void shouldNotAddProductAlreadyExists()
      throws CoinInputException, ProductAlreadyExistsException {
    doThrow(new ProductAlreadyExistsException(product.getProductName()))
        .when(productRepository)
        .add(product);

    assertThrows(ProductAlreadyExistsException.class, () -> productService.add(product));

    verify(productRepository).add(product);
  }

  @Test
  public void shouldDeleteProduct() {
    productService.delete(product);
    verify(productRepository).delete(product);
  }

  @Test
  public void shouldUpdateProduct() throws ProductNotExistsException {
    Product productUpdate =
        Product.builder()
            .productName(PRODUCT_NAME)
            .cost(productCost + 1)
            .amountAvailable(productAvailability)
            .sellerId(1)
            .build();

    productService.updateProduct(productUpdate);

    verify(productRepository).updateProduct(productUpdate);
  }
}
