package com.mvp.challenge.repository;

import com.mvp.challenge.domain.AcceptedCoins;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepository {

    private Map<String, User> userMap = new HashMap<>();

    public User get(String user) throws UsernameNotFoundException {
        return Optional.of(userMap.get(user)).orElseThrow(() -> new UsernameNotFoundException("User " + user + "do not exists"));
    }

    public User mergeUser(User user) throws UserCredentialException {
        if (Optional.ofNullable(user.getPassword()).isEmpty() || Optional.ofNullable(user.getUsername()).isEmpty())
            throw new UserCredentialException("User name and password are required to create a user");

        userMap.put(user.getUsername(), user);

        return user;
    }

    public User deposit(String userName, AcceptedCoins input) throws CoinInputException {
        int deposit = Optional.ofNullable(input).map(AcceptedCoins::getValue).orElseThrow(CoinInputException::new);

        User existingUser = get(userName);
        existingUser.setDeposit(existingUser.getDeposit() + deposit);
        return userMap.put(userName, existingUser);
    }

    public User resetDeposit(String userName) {
        User existingUser = get(userName);
        existingUser.setDeposit(0);
        return userMap.put(userName, existingUser);
    }
}
