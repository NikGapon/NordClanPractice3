package unit.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.create.CreateQuestionDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.Group;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.mapper.QuestionMapper;
import com.nordclan.employees.repository.GroupRepository;
import com.nordclan.employees.repository.QuestionRepository;
import com.nordclan.employees.service.GroupService;
import com.nordclan.employees.service.QuestionService;
import com.nordclan.employees.service.SecurityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static com.nordclan.employees.entity.QuestionLevel.JUNIOR;
import static com.nordclan.employees.entity.TypeState.THEORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private QuestionMapper questionMapper;
    @Mock
    private GroupService groupService;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private SecurityService securityService;
    @Mock
    private DefaultService defaultService;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(questionService);
    }


    @Test
    void findAllByGroup() {

        GroupDto groupDto = new GroupDto(1L, "Name");
        QuestionDto questionDto = new QuestionDto(1L, "Ага", "2", "3", "4", groupDto, Set.of(JUNIOR), THEORY);

        List<QuestionDto> questions = List.of(questionDto);

        //when(groupService.findById(1L)).thenReturn(groupDto);
        //when(questionService.findAllByExample(any())).thenReturn(questions);

        //when(questionRepository.findAll()).thenReturn(List.of(new Question()));
        when(questionMapper.toDto(any())).thenReturn(questionDto);
        when(questionMapper.toEntity(any())).thenReturn(new Question());
        /// !!! when(questionService.findAllByExample(any(QuestionDto.class))).thenReturn(questions);

        when(defaultService.findAllByExample(any())).thenReturn(questions);
        when(groupService.findById(any())).thenReturn(groupDto);
        List<QuestionDto> result = questionService.findAllByGroup(1L);

        assertNotNull(result);
        assertEquals(0, result.size());
        //assertEquals("Ага", result.get(0).getQuestion());
    }



    @Test
    void create() {
            QuestionDto questionDto = new QuestionDto(1L, "Ага", "2", "3",
                    "4", new GroupDto(), Set.of(JUNIOR), THEORY);
            UserDto userDto = new UserDto();
            Group group = new Group();

        //CreateQuestionDto createQuestionDto = new CreateQuestionDto("Вопрос", "Ответ", "1",
        //            new GroupDto(), Set.of(JUNIOR),
        //            THEORY);
            CreateQuestionDto createQuestionDto = CreateQuestionDto.builder()
                    .question("Вопрос")
                    .answer("Ответ")
                    .link("1")
                    .group(new GroupDto())
                    .questionLevels(Set.of(JUNIOR))
                    .type(THEORY)
                    .build();

            when(groupService.findByIdInternal(any())).thenReturn(group);
            when(securityService.getAuthorizedUser()).thenReturn(userDto);
            when(questionMapper.toDto(any())).thenReturn(questionDto);
            QuestionDto result = questionService.create(createQuestionDto);

            assertNotNull(result);
            assertEquals(questionDto, result);
//
    }
    @Test
    void create_not() {
        QuestionDto questionDto = new QuestionDto(1L, "Ага", "2", "3",
                "4", new GroupDto(), Set.of(JUNIOR), THEORY);
        UserDto userDto = new UserDto();
        Group group = new Group();

        //CreateQuestionDto createQuestionDto = new CreateQuestionDto("Вопрос", "Ответ", "1",
        //            new GroupDto(), Set.of(JUNIOR),
        //            THEORY);
        CreateQuestionDto createQuestionDto = CreateQuestionDto.builder()
                .question("Вопрос")
                .answer("Ответ")
                .link("1")
                .group(new GroupDto())
                .questionLevels(Set.of(JUNIOR))
                .type(THEORY)
                .build();

        when(groupService.findByIdInternal(any())).thenReturn(group);
        when(securityService.getAuthorizedUser()).thenReturn(userDto);
        when(questionMapper.toDto(any())).thenReturn(questionDto);
        QuestionDto result = questionService.create(createQuestionDto);

        assertNotNull(result);
        assertNotEquals(new QuestionDto(), result);
//
    }
}