package com.mvp.challenge.exception;

public class ProductTemporarilyNotAvailable extends Exception {
    public ProductTemporarilyNotAvailable(String productName) {
        super("Product " + productName + " is temporarily out of stock");
    }
}
