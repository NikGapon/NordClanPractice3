package integration;

import com.nordclan.employees.controller.UserInfoController;
import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.User;
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
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserInfoTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private TrainingHistoryService trainingHistoryService;

    @Mock
    private UserService userService ;

    @InjectMocks
    private UserInfoController userInfoController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
         mockMvc = MockMvcBuilders.standaloneSetup(userInfoController).build();
         objectMapper = new ObjectMapper();

    }


    @Test
    void controllerServiceTest() throws Exception{
        if (false){
        UUID userId = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
        User user = new User();
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        user.setId(userId);
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

        mockMvc = MockMvcBuilders.standaloneSetup(userInfoController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        mockMvc.perform(
                get("/userInfo/check/{userId}", "f000aa01-0451-4000-b000-000000000000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.first").value(userDto));




    }}
}
