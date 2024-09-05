package com.mvp.challenge.controller;

import com.mvp.challenge.domain.MvpRoles;
import com.mvp.challenge.domain.Purchase;
import com.mvp.challenge.domain.PurchaseResult;
import com.mvp.challenge.exception.NotEnoughDepositException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.ProductTemporarilyNotAvailable;
import com.mvp.challenge.exception.TooManyProductPurchaseException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.service.PurchaseService;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/buy")
@RequiredArgsConstructor
public class PurchaseController {

  private final PurchaseService purchaseService;

  @RequestMapping(
      method = RequestMethod.POST,
      produces = {MediaType.APPLICATION_JSON_VALUE},
      consumes = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(code = HttpStatus.OK)
  @Secured({MvpRoles.Code.BUYER, MvpRoles.Code.SELLER})
  public PurchaseResult buy(@RequestBody List<Purchase> purchaseList, Authentication authentication)
      throws NotEnoughDepositException,
          ProductTemporarilyNotAvailable,
          UserCredentialException,
          ProductNotExistsException,
          TooManyProductPurchaseException {
    return purchaseService.buy(authentication.getName(), purchaseList);
  }
}
