/** */
package com.mvp.challenge.service;

import com.mvp.challenge.domain.AcceptedCoins;
import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public User save(User user) throws UserCredentialException {
    user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    user.setActive(true);
    return userRepository.mergeUser(user);
  }

  public User findByUsername(String username) throws UsernameNotFoundException {
    return userRepository.get(username);
  }

  public User deposit(String userName, Deposit deposit) throws CoinInputException {
    return userRepository.deposit(userName, getDepositValue(deposit));
  }

  public User resetDeposit(String userName) {
    return userRepository.resetDeposit(userName);
  }

  private AcceptedCoins getDepositValue(Deposit deposit) {
    return AcceptedCoins.getByValue(deposit.getValue());
  }
}
