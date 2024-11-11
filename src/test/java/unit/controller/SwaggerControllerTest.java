package unit.controller;


import com.nordclan.employees.controller.SwaggerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@ExtendWith(MockitoExtension.class)

public class SwaggerControllerTest {


    @InjectMocks
    private SwaggerController swaggerController;

    private MockMvc mockMVC;


    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(swaggerController).build();
    }

    @Test
    void home() throws Exception{
        mockMVC.perform(get("/")).andExpect(redirectedUrl("/swagger"));
    }
}
