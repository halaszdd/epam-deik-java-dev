import com.epam.training.ticketservice.commands.ApplicationCommands;
import com.epam.training.ticketservice.domain.Role;
import com.epam.training.ticketservice.domain.User;
import com.epam.training.ticketservice.services.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationCommandsTest {

    private ApplicationCommands underTest;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        underTest = new ApplicationCommands(authenticationService);
    }


    @Test
    void signInShouldReturnCorrectly() {
        //given
        String username = "admin";
        String password = "admin";
        //when
        underTest.signIn(username,password);
        //then
        verify(authenticationService).authenticate(username,password);
    }

    @Test
    void signOut() {
        //given
        //when
        underTest.signOut();
        //then
        verify(authenticationService).logOut();
    }

    @Test
    void describeAccountWhenSignedInPrivileged() {
        //given
        User user = new User("admin","admin", Role.ADMIN);
        when(authenticationService.getLoggedInUser()).thenReturn(user);
        //when
        underTest.describeAccount();
        //then
        verify(authenticationService).getLoggedInUser();
    }

    @Test
    void describeAccountWhenNotSignedInPrivileged() {
        //given
        //when
        underTest.describeAccount();
        //then
        verify(authenticationService).getLoggedInUser();
    }
}