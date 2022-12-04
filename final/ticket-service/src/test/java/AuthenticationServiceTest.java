import com.epam.training.ticketservice.domain.Role;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.repositories.UserRepository;
import com.epam.training.ticketservice.services.AuthenticationFailedException;
import com.epam.training.ticketservice.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private AuthenticationService underTest;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        underTest = new AuthenticationService(userRepository);
    }

    @Test
    void authenticateShouldReturnSuccess() {
        //given
        User user = new User("admin", "admin", Role.ADMIN);
        String expectedPassword = "admin";
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));
        //when
        underTest.authenticate(user.getUsername(), user.getPassword());
        //then
        assertEquals(expectedPassword, user.getPassword());
    }

    @Test
    void authenticateShouldThrowWhenNoUserFound() {
        //given
        User user = new User("admin", "admin", Role.ADMIN);
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.empty());
        //when
        //then
        assertThrows(NoSuchElementException.class, () -> {
            underTest.authenticate(user.getUsername(), user.getPassword());
        });
    }

    @Test
    void authenticateShouldThrowWhenFailed() {
        //given
        User user = new User("admin", "admin", Role.ADMIN);
        String expectedPassword = "notadmin";
        when(userRepository.findById(user.getUsername())).thenReturn(Optional.of(user));
        //when
        //then
        assertThrows(AuthenticationFailedException.class, () -> {
            underTest.authenticate(user.getUsername(), expectedPassword);
        });
    }

    @Test
    void logOut() {
    }

    @Test
    void getLoggedInUser() {
    }
}