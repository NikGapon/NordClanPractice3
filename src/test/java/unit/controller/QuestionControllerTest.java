package unit.controller;

import com.nordclan.employees.controller.QuestionController;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.entity.QuestionLevel;
import com.nordclan.employees.service.QuestionService;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nordclan.employees.entity.QuestionLevel.JUNIOR;
import static com.nordclan.employees.entity.TypeState.THEORY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)

class QuestionControllerTest {

    @Mock
    private QuestionService questionService;
    @InjectMocks
    private QuestionController questionController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(questionController).build();
        objectMapper = new ObjectMapper();

    }


    @Test
    void getQuestion() throws Exception {
        Set<QuestionLevel> questionLevels = new HashSet<>(JUNIOR.ordinal());
        GroupDto groupDto = new GroupDto(1L, "Чета");

        when(questionService.findById(1L))
                .thenReturn(new QuestionDto(1L, "Чета", "1", "2", "3", groupDto, questionLevels, THEORY));

        mockMVC.perform(get("/question/getQuestion/{questionId}", 1L)
                        )
                .andExpect(status().isOk());
    }

    @Test
    void allQuestion() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        List<QuestionDto> test_list = new ArrayList<>();
        test_list.add(questionDto);

        Pageable pageable = PageRequest.of(0, 10);
        Page<QuestionDto> page = new PageImpl<>(test_list, pageable, test_list.size());

        when(questionService.findAll(any(Pageable.class))).thenReturn(page);
        mockMVC = MockMvcBuilders.standaloneSetup(questionController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        mockMVC.perform(get("/question/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void createQuestion() throws Exception {
        Set<QuestionLevel> questionLevels = new HashSet<>(JUNIOR.ordinal());
        GroupDto groupDto = new GroupDto(1L, "Чета");
        QuestionDto questionDto = new QuestionDto(1L, "Чета", "1", "2", "3", groupDto, questionLevels, THEORY);


        mockMVC.perform(post("/question/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto))).andExpect(status().isOk());

    }

    @Test
    void deleteQuestion() throws Exception {
        mockMVC.perform(delete("/question/delete/{questionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateQuestion() throws Exception {
        Set<QuestionLevel> questionLevels = new HashSet<>(JUNIOR.ordinal());
        GroupDto groupDto = new GroupDto(1L, "Чета");
        QuestionDto questionDto = new QuestionDto(1L, "Чета", "1", "2", "3", groupDto, questionLevels, THEORY);


        QuestionDto questionDto1 = new QuestionDto(1L, "Чета новое", "1", "2", "3", groupDto, questionLevels, THEORY);
        when(questionService.update(1L, questionDto))
                .thenReturn(questionDto1);

        mockMVC.perform(put("/question/update/{questionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(questionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.question").value("Чета новое"));
    }
}