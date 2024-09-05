package com.mvp.challenge.controller;

import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.MvpRoles;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping(
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.CREATED)
  public User addUser(@RequestBody User user) throws UserCredentialException {
    return userService.save(user);
  }

  @PutMapping(
      value = "/deposit",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.OK)
  @Secured({MvpRoles.Code.BUYER})
  public User deposit(@RequestBody Deposit deposit, Authentication authentication)
      throws CoinInputException {
    return userService.deposit(authentication.getName(), deposit);
  }

  @PutMapping(
      value = "/reset",
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.OK)
  @Secured({MvpRoles.Code.BUYER})
  public User reset(Authentication authentication) {
    return userService.resetDeposit(authentication.getName());
  }
}
