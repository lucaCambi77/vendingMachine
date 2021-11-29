package com.mvp.challenge.controller;

import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import static com.mvp.challenge.domain.MvpRoles.BUYER;

@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    public User addUser(@RequestBody User user) throws UserCredentialException {
        return userService.save(user);
    }

    @RequestMapping(value = "/deposit", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @RolesAllowed({BUYER})
    public User deposit(@RequestBody Deposit deposit, HttpServletRequest httpServletRequest) throws CoinInputException {
        return userService.deposit(httpServletRequest.getUserPrincipal().getName(), deposit);
    }

    @RequestMapping(value = "/reset", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @RolesAllowed({BUYER})
    public User reset(HttpServletRequest httpServletRequest) {
        return userService.resetDeposit(httpServletRequest.getUserPrincipal().getName());
    }
}
