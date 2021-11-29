package com.mvp.challenge.exception;

public class NotEnoughDepositException extends Exception {

    public NotEnoughDepositException(String productName) {
        super("User has not enough money to buy product " + productName);
    }
}
