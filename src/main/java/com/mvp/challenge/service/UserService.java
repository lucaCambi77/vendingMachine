/**
 *
 */
package com.mvp.challenge.service;

import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User save(User user) throws UserCredentialException;

    User findByUsername(String username) throws UsernameNotFoundException;

    User deposit(String userName, Deposit deposit) throws CoinInputException;

    User resetDeposit(String userName);
}
