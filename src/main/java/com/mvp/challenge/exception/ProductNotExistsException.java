package com.mvp.challenge.exception;

public class ProductNotExistsException extends Exception {

    public ProductNotExistsException(String productName) {
        super(productName + " already exists");
    }
}
