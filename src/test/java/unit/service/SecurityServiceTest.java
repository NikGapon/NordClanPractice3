package unit.service;

import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SecurityServiceTest {


    @InjectMocks
    private SecurityService securityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(securityService);
    }

    @Test
    void getAuthorizedUser() {

        UserDto result = securityService.getAuthorizedUser();

        assertNotNull(result);
        assertEquals(new UserDto(), result);


    }

    @Test
    void getAuthorizedUser_not() {
        UserDto userDto_new = new UserDto();
        userDto_new.setFirstName("ds");
        UserDto result = securityService.getAuthorizedUser();

        assertNotNull(result);
        assertNotEquals(userDto_new, result);

    }
}