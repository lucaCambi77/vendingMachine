package com.mvp.challenge.repository;

import com.mvp.challenge.domain.AcceptedCoins;
import com.mvp.challenge.domain.user.User;
import com.mvp.challenge.exception.CoinInputException;
import com.mvp.challenge.exception.UserCredentialException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @InjectMocks
    private UserRepository userRepository;

    private static final String USER_NAME = "user";

    @Test
    public void shouldAddUser() throws UserCredentialException {
        User user = createUser();
        userRepository.mergeUser(user);
    }

    @Test
    public void shouldNotAddUser() {
        User user = new User();
        user.setUserName("newUser");

        assertThrows(UserCredentialException.class, () -> userRepository.mergeUser(user));

        user.setUserName(null);
        user.setPassword("password");

        assertThrows(UserCredentialException.class, () -> userRepository.mergeUser(user));

    }

    @Test
    void shouldDepositCoins() throws CoinInputException, UserCredentialException {
        User user = createUser();
        userRepository.mergeUser(user);

        assertEquals(0, userRepository.get(USER_NAME).getDeposit());

        userRepository.deposit(USER_NAME, AcceptedCoins.TEN);

        assertEquals(AcceptedCoins.TEN.getValue(), userRepository.get(USER_NAME).getDeposit());
    }

    @Test
    void shouldNotDepositCoins() {
        assertThrows(CoinInputException.class, () -> userRepository.deposit(USER_NAME, null));
    }

    @Test
    public void shouldResetDeposit() throws CoinInputException, UserCredentialException {
        User user = createUser();
        userRepository.mergeUser(user);

        userRepository.deposit(USER_NAME, AcceptedCoins.TEN);

        assertEquals(AcceptedCoins.TEN.getValue(), userRepository.get(USER_NAME).getDeposit());

        userRepository.resetDeposit(USER_NAME);

        assertEquals(0, userRepository.get(USER_NAME).getDeposit());
    }

    private User createUser() {
        User user = new User();
        user.setUserName(USER_NAME);
        user.setPassword("password");
        return user;
    }

}
