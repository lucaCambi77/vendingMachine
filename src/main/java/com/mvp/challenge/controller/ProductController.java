package com.mvp.challenge.controller;


import com.mvp.challenge.domain.MvpRoles;
import com.mvp.challenge.domain.Product;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.ProductAlreadyExistsException;
import com.mvp.challenge.exception.ProductNotExistsException;
import com.mvp.challenge.exception.UserNotAuthorizedException;
import com.mvp.challenge.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController()
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    @RolesAllowed({MvpRoles.Code.SELLER})
    public Product addProduct(@RequestBody Product product, Authentication authentication) throws CoinInputException, ProductAlreadyExistsException, UserNotAuthorizedException {
        if (!hasUserPrivilegeOnProduct(authentication.getPrincipal(), product.getSellerId())) {
            throw new UserNotAuthorizedException("User not authorized to create product");
        }

        return productService.add(product);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @RolesAllowed({MvpRoles.Code.SELLER})
    public Product update(@RequestBody Product product, Authentication authentication) throws ProductNotExistsException, UserNotAuthorizedException {
        if (!hasUserPrivilegeOnProduct(authentication.getPrincipal(), product.getSellerId())) {
            throw new UserNotAuthorizedException("User not authorized to update product");
        }

        return productService.updateProduct(product);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    @RolesAllowed({MvpRoles.Code.SELLER})
    public Product delete(@RequestBody Product product, Authentication authentication) throws UserNotAuthorizedException {
        if (!hasUserPrivilegeOnProduct(authentication.getPrincipal(), product.getSellerId())) {
            throw new UserNotAuthorizedException("User not authorized to delete product");
        }

        return productService.delete(product);
    }

    private boolean hasUserPrivilegeOnProduct(Object user, int product) {
        UserDetails userdetails = (UserDetails) user;
        return userdetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Integer.toString(product)));
    }
}
