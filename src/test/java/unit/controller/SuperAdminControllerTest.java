package unit.controller;

import com.nordclan.employees.controller.SuperAdminController;
import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.dto.request.SetRolesRequestDto;
import com.nordclan.employees.entity.RoleNames;
import com.nordclan.employees.service.SuperAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SuperAdminControllerTest {

    @Mock
    private SuperAdminService superAdminService;
    @InjectMocks
    private SuperAdminController superAdminController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(superAdminController).build();
        objectMapper = new ObjectMapper();

    }

    @Test
    void assignRoleToUser() throws Exception {
        List<RoleNames> roleNamesList = new ArrayList<>(Arrays.asList(RoleNames.USER, RoleNames.MENTOR));
        SetRolesRequestDto setRolesRequestDto = new SetRolesRequestDto(roleNamesList);

        when(superAdminService.assignRoleToUser(
                UUID.fromString("f000aa01-0451-4000-b000-000000000000"), setRolesRequestDto))
                .thenReturn(new UserDto());

        mockMVC.perform(put("/superAdmin/assignRole/{userId}", "f000aa01-0451-4000-b000-000000000000")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setRolesRequestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void getAvailableRoles() throws Exception {
        List<RoleDto> roleNamesList1 = new ArrayList<>(Arrays.asList(new RoleDto(1L, "2", "2")));
        when(superAdminService.getAvailableRoles()).thenReturn(roleNamesList1);
        mockMVC.perform(get("/superAdmin/availableRoles")).andExpect(status().isOk());
    }
}