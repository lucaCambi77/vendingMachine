package com.mvp.challenge.exception;

public class ProductAlreadyExistsException extends Exception {

    public ProductAlreadyExistsException(String productName) {
        super(productName + " already exists");
    }
}
