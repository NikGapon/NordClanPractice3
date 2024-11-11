package unit.controller;

import com.nordclan.employees.controller.SecurityController;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)

class SecurityControllerTest {

    @Mock
    private SecurityService securityService;
    @InjectMocks
    private SecurityController securityController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(securityController).build();
        objectMapper = new ObjectMapper();

    }

    @Test
    void getAuthorizedUser() throws Exception {
        when(securityService.getAuthorizedUser()).thenReturn(new UserDto());
        mockMVC.perform(get("/security/getAuthorizedUser")).andExpect(status().isOk());
    }
}