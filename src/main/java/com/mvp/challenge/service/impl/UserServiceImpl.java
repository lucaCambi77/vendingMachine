/**
 *
 */
package com.mvp.challenge.service.impl;

import com.mvp.challenge.domain.AcceptedCoins;
import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.UserRepository;
import com.mvp.challenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) throws UserCredentialException {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.mergeUser(user);
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        return userRepository.get(username);
    }

    @Override
    public User deposit(String userName, Deposit deposit) throws CoinInputException {

        return userRepository.deposit(userName, getDepositValue(deposit));
    }

    @Override
    public User resetDeposit(String userName) {
        return userRepository.resetDeposit(userName);
    }

    private AcceptedCoins getDepositValue(Deposit deposit) {
        return AcceptedCoins.getByValue(deposit.getValue());
    }
}