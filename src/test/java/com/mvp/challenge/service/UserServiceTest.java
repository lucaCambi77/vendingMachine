package com.mvp.challenge.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mvp.challenge.domain.AcceptedCoins;
import com.mvp.challenge.domain.Deposit;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import com.mvp.challenge.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private UserService userService;

  @Mock private UserRepository userRepository;

  @Mock private BCryptPasswordEncoder bCryptPasswordEncoder;

  @BeforeEach
  public void setUp() {
    userService = new UserService(userRepository, bCryptPasswordEncoder);
  }

  @Test
  public void shouldAddUser() throws UserCredentialException {
    User user = new User();
    user.setUsername("User");
    user.setPassword("password");

    userService.save(user);

    verify(userRepository).mergeUser(user);
  }

  @Test
  public void shouldThrowWhenWrongUserCredential() throws UserCredentialException {
    User user = new User();

    when(userRepository.mergeUser(user))
        .thenThrow(new UserCredentialException("missing login info"));

    assertThrows(UserCredentialException.class, () -> userService.save(user));

    verify(userRepository).mergeUser(user);
  }

  @Test
  public void shouldGetUser() throws UsernameNotFoundException {
    String userName = "userName";

    User user = new User();
    user.setUsername(userName);
    user.setPassword("password");

    when(userRepository.get(userName)).thenReturn(user);

    assertEquals(user, userService.findByUsername(userName));

    verify(userRepository).get(userName);
  }

  @Test
  public void shouldThrowWhenUserNotFound() {
    String userName = "userName";

    when(userRepository.get(userName)).thenThrow(new UsernameNotFoundException("user not found"));

    assertThrows(UsernameNotFoundException.class, () -> userService.findByUsername(userName));

    verify(userRepository).get(userName);
  }

  @Test
  public void shouldDeposit() throws CoinInputException {
    String userName = "userName";
    AcceptedCoins deposit = AcceptedCoins.FIVE;

    User user = new User();
    user.setUsername(userName);
    user.setDeposit(deposit.getValue());

    when(userRepository.deposit(userName, deposit)).thenReturn(user);

    assertEquals(user, userService.deposit(userName, new Deposit(deposit.getValue())));
    verify(userRepository).deposit(userName, deposit);
  }

  @Test
  public void shouldNotDeposit() throws CoinInputException {
    String userName = "userName";
    AcceptedCoins deposit = AcceptedCoins.FIVE;

    User user = new User();
    user.setUsername(userName);
    user.setDeposit(deposit.getValue());

    when(userRepository.deposit(userName, deposit)).thenThrow(new CoinInputException());

    assertThrows(
        CoinInputException.class,
        () -> userService.deposit(userName, new Deposit(deposit.getValue())));

    verify(userRepository).deposit(userName, deposit);
  }

  @Test
  public void shouldResetDeposit() {
    String userName = "userName";

    User user = new User();
    user.setUsername(userName);
    user.setDeposit(0);

    when(userRepository.resetDeposit(userName)).thenReturn(user);

    assertEquals(user, userService.resetDeposit(userName));
    verify(userRepository).resetDeposit(userName);
  }
}
