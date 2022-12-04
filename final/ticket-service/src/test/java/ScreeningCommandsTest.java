import com.epam.training.ticketservice.commands.ScreeningCommands;
import com.epam.training.ticketservice.domain.RegisterScreeningModel;
import com.epam.training.ticketservice.domain.Screening;
import com.epam.training.ticketservice.services.AuthenticationService;
import com.epam.training.ticketservice.services.ScreeningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScreeningCommandsTest {

    private ScreeningCommands underTest;

    @Mock
    private ScreeningService screeningService;

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        underTest = new ScreeningCommands(authenticationService, screeningService);
    }


    @Test
    void createScreening() {
        //given
        RegisterScreeningModel registerScreeningModel = new RegisterScreeningModel();
        String time = "2222-11-11 11:11";
        //when
        underTest.createScreening(registerScreeningModel.getTitle(),registerScreeningModel.getName(),time);
        //then
        verify(screeningService).createScreening(any());
    }

    @Test
    void listScreenings() {
    }

    @Test
    void deleteScreening() {
    }
}