package unit.controller;

import com.nordclan.employees.controller.RequestHistoryController;
import com.nordclan.employees.dto.entity.RequestHistoryDto;
import com.nordclan.employees.service.RequestHistoryService;
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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

class RequestHistoryControllerTest {
    @Mock
    private RequestHistoryService requestHistoryService;

    @InjectMocks
    private RequestHistoryController requestHistoryController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(requestHistoryController).build();
        objectMapper = new ObjectMapper();

    }


    @Test
    void getAllRequestHist() throws Exception {
        RequestHistoryDto requestHistoryDto =
                new RequestHistoryDto(1L, 2L, LocalDateTime.now(),
                        UUID.fromString("f000aa01-0451-4000-b000-000000000000"),
                        "Чета", "2", "3", "4", "5");

        RequestHistoryDto requestHistoryDto2 =
                new RequestHistoryDto(1L, 2L, LocalDateTime.now(),
                        UUID.fromString("f000aa01-0451-4000-b000-000000000000"),
                        "Чета", "2", "3", "4", "5");

        List<RequestHistoryDto> test_list = new ArrayList<>();
        test_list.add(requestHistoryDto);
        test_list.add(requestHistoryDto2);

        Pageable pageable = PageRequest.of(0, 10);
        Page<RequestHistoryDto> page = new PageImpl<>(test_list, pageable, test_list.size());
        when(requestHistoryService.findAll(any(Pageable.class))).thenReturn(page);

        mockMVC = MockMvcBuilders.standaloneSetup(requestHistoryController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        mockMVC.perform(get("/request_hist/getAllRequestHist")
                               // .contentType(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(groupDto))
                )
                .andExpect(status().isOk())


        ;
    }

    @Test
    void getRequestHistByRequestId() throws Exception {

        RequestHistoryDto requestHistoryDto =
                new RequestHistoryDto(1L, 1L, LocalDateTime.now(),
                        UUID.fromString("f000aa01-0451-4000-b000-000000000000"),
                        "Чета", "2", "3", "4", "5");

        List<RequestHistoryDto> test_list = new ArrayList<>();
        test_list.add(requestHistoryDto);

        when(requestHistoryService.getRequestHistByRequestId(eq(1L))).thenReturn(test_list);

        mockMVC.perform(get("/request_hist/getRequestHistByRequestId/{requestId}", 1)
                //.contentType(MediaType.APPLICATION_JSON)
                //.content(objectMapper.writeValueAsString(page))
        ).andExpect(status().isOk());
    }
}