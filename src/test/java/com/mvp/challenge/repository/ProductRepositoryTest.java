package com.mvp.challenge.repository;

import com.mvp.challenge.domain.Product;
import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.UserCredentialException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    private static final String PRODUCT_ONE = "product";
    private static final int SELLER_ONE = 1;

    private static final String PRODUCT_TWO = "productTwo";
    private static final int SELLER_TWO = 2;

    @Test
    public void shouldCreateProducts() throws CoinInputException, ProductAlreadyExistsException, ProductNotExistsException {
        int amountAvailable = 5;
        int cost = 20;

        int amountAvailableTwo = 10;
        int costTwo = 25;

        Product productOne = Product.builder().productName(PRODUCT_ONE).amountAvailable(amountAvailable).cost(cost).sellerId(SELLER_ONE).build();

        productRepository.add(productOne);

        assertEquals(productRepository.getAll().size(), 1);
        assertEquals(productRepository.getByProduct(PRODUCT_ONE), productOne);
        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);
        assertEquals(productRepository.getBySeller(SELLER_ONE).stream().filter(p -> p.getProductName().equals(PRODUCT_ONE)).findFirst().orElse(new Product()).getAmountAvailable(), amountAvailable);
        assertEquals(productRepository.getBySeller(SELLER_ONE).stream().filter(p -> p.getProductName().equals(PRODUCT_ONE)).findFirst().orElse(new Product()).getCost(), cost);

        Product productTwo = Product.builder().productName(PRODUCT_TWO).amountAvailable(amountAvailableTwo).cost(costTwo).sellerId(SELLER_TWO).build();

        productRepository.add(productTwo);

        assertEquals(productRepository.getAll().size(), 2);
        assertEquals(productRepository.getByProduct(PRODUCT_TWO), productTwo);
        assertEquals(productRepository.getBySeller(SELLER_TWO).size(), 1);
        assertEquals(productRepository.getBySeller(SELLER_TWO).stream().filter(p -> p.getProductName().equals(PRODUCT_TWO)).findFirst().orElse(new Product()).getAmountAvailable(), amountAvailableTwo);
        assertEquals(productRepository.getBySeller(SELLER_TWO).stream().filter(p -> p.getProductName().equals(PRODUCT_TWO)).findFirst().orElse(new Product()).getCost(), costTwo);

    }

    @Test
    public void shouldNotCreateProductInvalidCost() {
        Product product = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(8).sellerId(SELLER_ONE).build();
        assertThrows(CoinInputException.class, () -> productRepository.add(product));
    }

    @Test
    public void shouldNotCreateProductAlreadyExists() throws CoinInputException, ProductAlreadyExistsException {
        Product product = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(20).sellerId(SELLER_ONE).build();
        productRepository.add(product);
        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);

        Product sameProduct = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(20).sellerId(SELLER_ONE).build();
        assertThrows(ProductAlreadyExistsException.class, () -> productRepository.add(sameProduct));

        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);
    }

    @Test
    public void shouldDeleteProduct() throws CoinInputException, ProductAlreadyExistsException {
        Product product = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(20).sellerId(SELLER_ONE).build();
        productRepository.add(product);
        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);

        productRepository.delete(product);

        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 0);
        assertThrows(ProductNotExistsException.class, () -> productRepository.getByProduct(PRODUCT_ONE));
    }

    @Test
    public void shouldNotDeleteProduct() throws CoinInputException, ProductAlreadyExistsException, ProductNotExistsException {
        Product product = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(20).sellerId(SELLER_ONE).build();
        productRepository.add(product);
        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);

        Product productDelete = Product.builder().productName("wrong_product").sellerId(SELLER_ONE).build();

        productRepository.delete(productDelete);

        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);
        assertEquals(productRepository.getByProduct(PRODUCT_ONE), product);
    }

    @Test
    public void shouldUpdateProduct() throws CoinInputException, ProductAlreadyExistsException, ProductNotExistsException {
        Product product = Product.builder().productName(PRODUCT_ONE).amountAvailable(5).cost(20).sellerId(SELLER_ONE).build();
        productRepository.add(product);
        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);

        Product productUpdate = Product.builder().productName(PRODUCT_ONE).cost(25).sellerId(SELLER_ONE).build();

        productRepository.updateProduct(productUpdate);

        assertEquals(productRepository.getBySeller(SELLER_ONE).size(), 1);
        assertEquals(productRepository.getByProduct(PRODUCT_ONE).getCost(), 25);
    }

    @Test
    public void shouldBuyProduct() throws CoinInputException, ProductAlreadyExistsException, NotEnoughDepositException, ProductTemporarilyNotAvailable, ProductNotExistsException, UserCredentialException {
        int available = 5;
        int purchase = 1;

        Product productOne = Product.builder().productName(PRODUCT_ONE).amountAvailable(available).cost(5).sellerId(SELLER_ONE).build();

        productRepository.add(productOne);

        Purchase purchaseInput = Purchase.builder().amount(purchase).productId(PRODUCT_ONE).build();

        productRepository.buy(purchaseInput);

        assertEquals(available - purchase, productRepository.getByProduct(PRODUCT_ONE).getAmountAvailable());
    }

    @Test
    public void shouldNotBuyProductProductNotAvailable() throws CoinInputException, ProductAlreadyExistsException {
        int available = 0;
        int purchase = 1;
        int cost = 5;

        Product productOne = Product.builder().productName(PRODUCT_ONE).amountAvailable(available).cost(cost).sellerId(SELLER_ONE).build();

        productRepository.add(productOne);

        Purchase purchaseInput = Purchase.builder().amount(purchase).productId(PRODUCT_ONE).build();

        assertThrows(ProductTemporarilyNotAvailable.class, () -> productRepository.buy(purchaseInput));
    }
}
