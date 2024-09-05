package com.mvp.challenge.exception;

public class UserNotAuthorizedException extends Exception {
  public UserNotAuthorizedException(String message) {
    super(message);
  }
}
