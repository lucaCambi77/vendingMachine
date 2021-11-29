package com.mvp.challenge.exception;

public class CoinInputException extends Exception {
    public CoinInputException() {
        super("Input cost or money is not allowed");
    }
}
