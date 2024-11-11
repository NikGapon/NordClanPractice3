package unit.controller;

import com.nordclan.employees.controller.UserRolePermissionController;
import com.nordclan.employees.dto.response.RolePermissionsResponseDto;
import com.nordclan.employees.service.UserRolePermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserRolePermissionControllerTest {

        @Mock
        private  UserRolePermissionService userRolePermissionService;

        @InjectMocks
        private UserRolePermissionController userRolePermissionController;

        private MockMvc mockMVC;

        private ObjectMapper objectMapper;

        @BeforeEach
        void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(userRolePermissionController).build();
            objectMapper = new ObjectMapper();
        }

        @Test
        void assess() throws Exception {
                RolePermissionsResponseDto rolePermissionsResponseDto = new RolePermissionsResponseDto(List.of(new String[]{"1"}), List.of(new String[]{"2"}));
                when(userRolePermissionService.getRolePermissionsByUserLogin()).thenReturn(rolePermissionsResponseDto);
                mockMVC.perform(get("/configuration/role")
                                //.contentType(MediaType.APPLICATION_JSON)
                                //.content(objectMapper.writeValueAsString(rolePermissionsResponseDto)))
                ).andExpect(status().isOk());
        }
}
