package unit.service;

import com.nordclan.employees.dto.entity.SimpleUserDto;
import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.User;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.UserMapper;
import com.nordclan.employees.repository.UserRepository;
import com.nordclan.employees.service.TrainingHistoryService;
import com.nordclan.employees.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TrainingHistoryService trainingHistoryService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(userService);
    }



    @Test
    void delete() throws Exception{
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User().setIsDeleted(false).setId(userId);
        UserDto userDto = new UserDto();
        userDto.setIsDeleted(true);

        when(userMapper.toDto(user)).thenReturn(userDto);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertEquals(false, user.getIsDeleted());

        //UserDto result = userMapper.toDto(userService.findByIdInternal(userId).setIsDeleted(true));
        UserDto result = userService.delete(userId);
        assertEquals(true, user.getIsDeleted());

        assertNotNull(result);
        assertEquals(userId, user.getId());

        assertEquals(userDto, result);
        assertEquals(true, result.getIsDeleted());

    }
    @Test
    void delete_UserNotFoundException(){
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");

        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () ->
            userService.delete(userId)
        );
    }

    @Test
    void getSimpleUserDtoById() {
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        user.setFirstName("Имя");
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        SimpleUserDto result = userService.getSimpleUserDtoById(userId);


        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals("Имя", result.getFirstName());
        assertNull(result.getSurName());


    }
    @Test
    void getSimpleUserDtoById_UserNotFoundException() {
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        user.setFirstName("Имя");
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //SimpleUserDto result = userService.getSimpleUserDtoById(userId);


        assertThrows(UserNotFoundException.class, () ->
                userService.getSimpleUserDtoById(userId)
        );


    }


    @Test
    void getUserInfo() throws Exception{
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        UserDto userDto = new UserDto();
        TrainingHistoryDto trainingHistoryDto =
                new TrainingHistoryDto(1L, 1L, LocalDateTime.now(), UUID.fromString("f000aa01-0451-4000-b000-000000000000")
                        , "1",
                        "2",
                        "3",
                        "4", "5"
                );
        List<TrainingHistoryDto> test_list = new ArrayList<>();
        test_list.add(trainingHistoryDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<TrainingHistoryDto> page = new PageImpl<>(test_list, pageable, test_list.size());


        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);
        when(trainingHistoryService.findAllByExample(any(TrainingHistoryDto.class), eq(pageable)))
                .thenReturn(page);

        //Pair<UserDto, Page<TrainingHistoryDto>> result = userService.getUserInfo(userId);

        //assertNotNull(result);
        //assertEquals(userDto, result.getFirst());
        //assertEquals(page, result.getSecond());


    }


}