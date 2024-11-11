package unit.service;

import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.dto.response.RolePermissionsResponseDto;
import com.nordclan.employees.entity.RoleAction;
import com.nordclan.employees.repository.RoleActionRepository;
import com.nordclan.employees.service.SecurityService;
import com.nordclan.employees.service.UserRolePermissionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRolePermissionServiceTest {
    @Mock
    private SecurityService securityService;
    @Mock
    private RoleActionRepository roleActionRepository;
    @InjectMocks
    private UserRolePermissionService userRolePermissionService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(userRolePermissionService);
    }

    @Test
    void getRolePermissionsByUserLogin() {
        UserDto userDto = new UserDto();
        Set<RoleDto> roles = new HashSet<>(Set.of(
                new RoleDto(1L, "ага", "ого"),
                new RoleDto(2L, "dsa", "дса")
        ));
        userDto.setRoles(roles);

        when(securityService.getAuthorizedUser()).thenReturn(userDto);
        when(roleActionRepository.findByRoleId(any())).thenReturn(new ArrayList<RoleAction>());
        RolePermissionsResponseDto result = userRolePermissionService.getRolePermissionsByUserLogin();

        assertNotNull(result);
        assertEquals("dsa", result.getRoles().get(0));
        assertEquals("ага", result.getRoles().get(1));

    }

    @Test
    void getRolePermissionsByUserLogin_not() {
        UserDto userDto = new UserDto();
        Set<RoleDto> roles = new HashSet<>(Set.of(
                new RoleDto(1L, "ага", "ого"),
                new RoleDto(2L, "dsa", "дса")
        ));
        userDto.setRoles(roles);

        when(securityService.getAuthorizedUser()).thenReturn(userDto);
        when(roleActionRepository.findByRoleId(any())).thenReturn(new ArrayList<RoleAction>());
        RolePermissionsResponseDto result = userRolePermissionService.getRolePermissionsByUserLogin();

        assertNotNull(result);
        assertNotEquals("dsa", result.getRoles().get(1));
        assertNotEquals("ага", result.getRoles().get(0));

    }



    @Test
    void getRolePermissionsByUserLogin_notArg() {
        UserDto userDto = new UserDto();
        Set<RoleDto> roles = new HashSet<>(Set.of(

        ));
        userDto.setRoles(roles);

        when(securityService.getAuthorizedUser()).thenReturn(userDto);
        //when(roleActionRepository.findByRoleId(any())).thenReturn(new ArrayList<RoleAction>());
        RolePermissionsResponseDto result = userRolePermissionService.getRolePermissionsByUserLogin();

        assertNotNull(result);
        assertEquals(0
                , result.getRoles().size());
        assertEquals(0, result.getPermissions().size());
}
}