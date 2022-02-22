package com.mvp.challenge.exception;

public class NotEnoughDepositException extends Exception {

    public NotEnoughDepositException(String userName) {
        super("User " + userName + " has not enough deposit for current purchase");
    }
}
