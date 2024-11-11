package unit.controller;

import com.nordclan.employees.controller.AssessmentController;
import com.nordclan.employees.dto.entity.QuestionResultDto;
import com.nordclan.employees.dto.request.AssessTrainingRequestDto;
import com.nordclan.employees.dto.response.QuestionResultDtoFull;
import com.nordclan.employees.service.AssessmentService;
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

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AssessmentControllerTest {
    @Mock
    private AssessmentService assessmentService;

    @InjectMocks
    private AssessmentController assessmentController;

    private MockMvc mockMVC;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() { mockMVC = MockMvcBuilders.standaloneSetup(assessmentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void assess() throws Exception {
        QuestionResultDto questionResultDto = new QuestionResultDto(1);
        mockMVC.perform(post("/assessment/assess/{trainingId}/{questionId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionResultDto)
                )).andExpect(status().isOk());
    }

    @Test
    void getAllResultByTraining() throws Exception{
        //QuestionResultDto questionResultDto = new QuestionResultDto(1);
        QuestionResultDtoFull questionResultDtoFull =
                new QuestionResultDtoFull(1L, 1L, 1);
        when(assessmentService.getAllResultByTraining(1L))
                .thenReturn(List.of(questionResultDtoFull));
        mockMVC.perform(get("/assessment/getAssessments/{trainingId}", 1)).andExpect(status().isOk());
    }

    @Test
    void assessQuestions() throws Exception{
        AssessTrainingRequestDto.Assessment assessment1 = new AssessTrainingRequestDto.Assessment(
                1L,
                2);

        AssessTrainingRequestDto.Assessment assessment2 = new AssessTrainingRequestDto.Assessment(
                2L,
                3);

        AssessTrainingRequestDto assessTrainingRequestDto = new AssessTrainingRequestDto(
               1L,
                List.of(assessment1, assessment2));
        mockMVC.perform(post("/assessment/assess")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assessTrainingRequestDto)))
                .andExpect(status().isOk())
                ;
    }
}
