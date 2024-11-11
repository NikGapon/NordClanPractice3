package unit.controller;

import com.nordclan.employees.controller.UserInfoController;
import com.nordclan.employees.dto.entity.TrainingHistoryDto;
import com.nordclan.employees.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserInfoControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserInfoController userInfoController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(userInfoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getUserInfo() throws Exception {

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

//        //when(userService.getUserInfo(eq(UUID.fromString("f000aa01-0451-4000-b000-000000000000")),any(Pageable.class))).thenReturn((Pair<UserDto, Page<TrainingHistoryDto>>) Pair.of(new UserDto(), page));
//
//        mockMVC = MockMvcBuilders.standaloneSetup(userInfoController)
//                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
//                .build();
//        mockMVC.perform(get("/userInfo/check/{userId}", "f000aa01-0451-4000-b000-000000000000"))
//
//
//                .andExpect(status().isOk())
//                .andExpect(
//                        jsonPath("$.first").value(new UserDto()))
//                ;
//
//
//
//
//

    }
}