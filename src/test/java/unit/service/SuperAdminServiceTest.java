package unit.service;

import com.nordclan.employees.dto.entity.RoleDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.dto.request.SetRolesRequestDto;
import com.nordclan.employees.entity.Role;
import com.nordclan.employees.entity.RoleAction;
import com.nordclan.employees.entity.RoleNames;
import com.nordclan.employees.entity.User;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.RoleMapper;
import com.nordclan.employees.mapper.UserMapper;
import com.nordclan.employees.repository.RoleRepository;
import com.nordclan.employees.repository.UserRepository;
import com.nordclan.employees.service.SuperAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Collectors;

import static com.nordclan.employees.entity.RoleNames.MENTOR;
import static com.nordclan.employees.entity.RoleNames.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SuperAdminServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SuperAdminService superAdminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(superAdminService);

    }

    @Test
    void assignRoleToUser()  {
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        user.setId(userId);
        UserDto userDto = new UserDto();

        SetRolesRequestDto setRolesRequestDto = new SetRolesRequestDto(List.of(new RoleNames[]{USER, MENTOR}));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(any())).thenReturn(Optional.of(new Role(1L, "тестовая роль")));
        when(userMapper.toDto(any())).thenReturn(userDto);

        UserDto result = superAdminService.assignRoleToUser(userId, setRolesRequestDto);

        assertNotNull(result);
        assertEquals(new UserDto(), result);

        User result1 = user.setRoles(
                setRolesRequestDto.getRoles()
                        .stream()
                        .map(RoleNames::toString)
                        .map(role -> roleRepository.findByRole(role)
                                .orElseThrow(() -> new NoSuchElementException("Не найдена роль " + role)))
                        .collect(Collectors.toSet()));


        assertNotNull(result1);
        assertEquals(Set.of(new Role(1L, "тестовая роль")), result1.getRoles());
        assertEquals(userId, result1.getId());
    }
    @Test
    void assignRoleToUser_UserNotFoundException() {
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        SetRolesRequestDto setRolesRequestDto = new SetRolesRequestDto(List.of(RoleNames.USER, RoleNames.MENTOR));

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                superAdminService.assignRoleToUser(userId, setRolesRequestDto)
        );
    }

    @Test
    void assignRoleToUser_NoSuchElementException() {
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        user.setId(userId);
        SetRolesRequestDto setRolesRequestDto = new SetRolesRequestDto(List.of(RoleNames.USER, RoleNames.MENTOR));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(anyString())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                superAdminService.assignRoleToUser(userId, setRolesRequestDto)
        );
    }
    @Test
    void getAvailableRoles() {
        RoleDto roleDto = new RoleDto(1L, "тестовая роль", "тестовая роль");

        when(roleRepository.findAll()).thenReturn(List.of(new Role(1L, "тестовая роль")));
        when(roleMapper.toDto(any())).thenReturn(roleDto);
        List<RoleDto> result1 = superAdminService.getAvailableRoles();

        assertNotNull(result1);
        assertEquals(roleDto, result1.get(0));
    }

    @Test
    void getAvailableRoles_not() {
        RoleDto roleDto = new RoleDto(1L, "тестовая роль", "тестовая роль");

        when(roleRepository.findAll()).thenReturn(List.of(new Role(1L, "тестовая роль")));
        when(roleMapper.toDto(any())).thenReturn(roleDto);
        List<RoleDto> result1 = superAdminService.getAvailableRoles();

        assertNotNull(result1);
        assertNotEquals(new RoleDto(), result1.get(0));
    }
}