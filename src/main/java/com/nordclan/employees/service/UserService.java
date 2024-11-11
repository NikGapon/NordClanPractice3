package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.entity.*;
import com.nordclan.employees.dto.response.QuestionResultDtoFull;
import com.nordclan.employees.entity.User;
import com.nordclan.employees.exception.RoleNotFoundException;
import com.nordclan.employees.exception.UserNotFoundException;
import com.nordclan.employees.mapper.TrainingHistoryMapper;
import com.nordclan.employees.mapper.UserMapper;
import com.nordclan.employees.repository.RoleRepository;
import com.nordclan.employees.repository.TrainingHistoryRepository;
import com.nordclan.employees.repository.UserRepository;
import io.micrometer.common.util.StringUtils;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.antlr.v4.runtime.misc.Triple;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.nordclan.employees.utils.CommonUtils.setIfNotBlank;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class UserService extends DefaultService<UUID, User, UserDto> {
    UserMapper userMapper;
    UserRepository userRepository;
    RoleRepository roleRepository;

    TrainingHistoryRepository trainingHistoryRepository;
    TrainingHistoryMapper trainingHistoryMapper;
    TrainingHistoryService trainingHistoryService;

    TemplateService templateService;
    TrainingService trainingService;
    AssessmentService assessmentService;
    QuestionService questionService;

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            RoleRepository roleRepository,
            TrainingHistoryRepository trainingHistoryRepository,
            TrainingHistoryMapper trainingHistoryMapper, TrainingHistoryService trainingHistoryService, TemplateService templateService, TrainingService trainingService, AssessmentService assessmentService, QuestionService questionService
    ) {
        super(userRepository, userMapper, UserNotFoundException::new);
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.trainingHistoryRepository = trainingHistoryRepository;
        this.trainingHistoryMapper = trainingHistoryMapper;

        this.trainingHistoryService = trainingHistoryService;
        this.templateService = templateService;
        this.trainingService = trainingService;
        this.assessmentService = assessmentService;
        this.questionService = questionService;
    }



    @Override
    public UserDto delete(UUID id) throws UserNotFoundException {
        return userMapper.toDto(this.findByIdInternal(id).setIsDeleted(true));
    }

    @Override
    protected User updateInternal(User user, UserDto userDto) {
        setIfNotBlank(userDto.getFirstName(), user::setFirstName);
        setIfNotBlank(userDto.getLastName(), user::setLastName);
        setIfNotBlank(userDto.getSurname(), user::setSurname);
        setIfNotBlank(userDto.getIsDeleted(), user::setIsDeleted);
        return user;
    }

    public List<UserDto> findUser(String userLastName, String userRole) {
        if (StringUtils.isNotBlank(userRole)) {
            return userRepository.findByLastNameAndRoles(
                    userLastName,
                    Set.of(roleRepository
                        .findByRole(userRole)
                        .orElseThrow(() -> new RoleNotFoundException("role", userRole))))
                .stream()
                .map(userMapper::toDto)
                .toList();
        }

        return this.findAllByExample(UserDto.builder().lastName(userLastName).build());
    }

    public SimpleUserDto getSimpleUserDtoById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return SimpleUserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .surName(user.getSurname())
                .roles(user.getRoles())
                .photo(user.getPhoto())
                .build();
    }

    public Triple<SimpleUserDto, Map<Long, TemplateDto>, Integer> getUserInfo(UUID userId){
        SimpleUserDto userDto = this.getSimpleUserDtoById(userId);

        List<TrainingDto> trainingDtos = trainingService.findAllByExample(TrainingDto.builder().student(this.getSimpleUserDtoById(userId)).build());
        Integer allResult = trainingDtos.stream()
                .mapToInt(TrainingDto::getExamResult)
                .sum();

        Map<Long, TemplateDto> trainingMap = trainingDtos.stream()
                .collect(Collectors.toMap(
                        trainingDto -> trainingDto.getId(),
                        trainingDto -> templateService.findById(trainingDto.getTemplateId())
                ));
        return new Triple(userDto,
                trainingMap, allResult);
    }

    public Pair<UserHonestCompareDto, Map<String, String>> honestCompare2UsersInfo(UUID user1Id, UUID user2Id) {
        SimpleUserDto user1SimpleDto = this.getSimpleUserDtoById(user1Id);
        SimpleUserDto user2SimpleDto = this.getSimpleUserDtoById(user2Id);
        UserHonestCompareDto userHonestCompareDto = new UserHonestCompareDto();
        HashMap<String, String> questionAllcompare = new HashMap<>();
        userHonestCompareDto.setFighter1(Pair.of(user1SimpleDto.getFirstName(), user1Id));
        userHonestCompareDto.setFighter2(Pair.of(user2SimpleDto.getFirstName(), user2Id));
        List<TrainingDto> trainingDtos1 = trainingService.findAllByExample(TrainingDto.builder().student(user1SimpleDto).build());
        List<TrainingDto> trainingDtos2 = trainingService.findAllByExample(TrainingDto.builder().student(user2SimpleDto).build());

        Integer user1Score = 0;
        Integer user2Score = 0;
        Integer howManyTemplate = 0;
        for(TrainingDto dto1 : trainingDtos1){
            for(TrainingDto dto2 : trainingDtos2){
                if(Objects.equals(dto1.getTemplateId(), dto2.getTemplateId())){
                    howManyTemplate++;
                    user1Score += dto1.getExamResult();
                    user2Score += dto2.getExamResult();
                    List<QuestionResultDtoFull> allResultByTrainingUser1 = assessmentService.getAllResultByTraining(dto1.getId());
                    List<QuestionResultDtoFull> allResultByTrainingUser2 = assessmentService.getAllResultByTraining(dto2.getId());

                    for(QuestionDto questionDto : templateService.findById(dto1.getTemplateId()).getQuestions()){

                         questionAllcompare.put(questionDto.getQuestion(),
                                user1SimpleDto.getFirstName() + " набрал по этому вопросу - "
                                        + allResultByTrainingUser1.stream().filter(x -> x.getQuestionId() == questionDto.getId()).findFirst().get().getPoint()
                                        + ".    " +
                        user2SimpleDto.getFirstName() + " набрал по этому вопросу - "
                                        + allResultByTrainingUser2.stream().filter(x -> x.getQuestionId() == questionDto.getId()).findFirst().get().getPoint()
                         );

                    }


                }
            }
        }

        userHonestCompareDto.setFighter1Score(user1Score);
        userHonestCompareDto.setFighter2Score(user2Score);
        userHonestCompareDto.setHowManyTemplate(howManyTemplate);
        if(user1Score > user2Score) {

            userHonestCompareDto.setWinner("В равном сравнении " + userHonestCompareDto.getFighter1().getFirst() + " набрал больше");
            return Pair.of(userHonestCompareDto, questionAllcompare);
        }else if(user1Score < user2Score) {
            userHonestCompareDto.setWinner("В равном сравнении " + userHonestCompareDto.getFighter2().getFirst() + " набрал больше");
            return Pair.of(userHonestCompareDto, questionAllcompare);
        }
        else {
            userHonestCompareDto.setWinner("В равном сравнении, оба набрали одинаковое кол во балов");
            return Pair.of(userHonestCompareDto, questionAllcompare);
        }
    }

    public Pair<String, Map<Triple<String, UUID, Integer>, String>> topUsersoftheTemplate(Long templateId) {
        List<String> NeutralResponse = List.of("Не достиг высот но был душой компании", "Опоздал на вечеринку", "Хороший человек", "Хорош во всем", "Молодец");
        List<String> GoodResponse = List.of("Легенда по", "Эксперт в вопросе", "Великолепен по", "Даже не почувствовал");
        HashMap<Triple<String, UUID, Integer>, String> topList = new HashMap<>();
        List<TrainingDto> trainingDtos = trainingService.findAllByExample(TrainingDto.builder().templateId(templateId).build());
        List<QuestionResultDtoFull> topofQuestion = new ArrayList<>();

        trainingDtos = trainingDtos.stream()
                .sorted(Comparator.comparing(TrainingDto::getExamResult))
                .limit(10).toList();
        trainingDtos
                .forEach(x -> topofQuestion.addAll(assessmentService.
                        getAllResultByTraining(x.getId())));


        Map<Long, List<QuestionResultDtoFull>> groupedByQuestionId = topofQuestion.stream()
                .collect(Collectors.groupingBy(QuestionResultDtoFull::getQuestionId));
        List<QuestionResultDtoFull> resultTopQuestion = new ArrayList<>();
        for (Map.Entry<Long, List<QuestionResultDtoFull>> entry : groupedByQuestionId.entrySet()) {
            List<QuestionResultDtoFull> records = entry.getValue();
            int maxPoint = Integer.MIN_VALUE;
            List<QuestionResultDtoFull> maxPointRecords = new ArrayList<>();

            for (QuestionResultDtoFull record : records) {
                if (record.getPoint() > maxPoint) {
                    maxPoint = record.getPoint();
                    maxPointRecords.clear();
                    maxPointRecords.add(record);
                } else if (record.getPoint() == maxPoint) {
                    maxPointRecords.add(record);
                }
            }
            if (maxPointRecords.size() == 1) {
                resultTopQuestion.add(maxPointRecords.get(0));
            }
        }

        List<Long> userTopAnswer = new ArrayList<>();
        String aboutUserAnswer = "";
        for(TrainingDto dto : trainingDtos){
            {

                resultTopQuestion.stream().filter(x -> x.getTrainingId() == dto.getId()).forEach(j -> userTopAnswer.add(j.getQuestionId()));
                if(userTopAnswer.isEmpty()){
                    aboutUserAnswer = NeutralResponse.get(new Random().nextInt(NeutralResponse.size()));
                } else {
                    for(Long id : userTopAnswer){
                        aboutUserAnswer += GoodResponse.get(new Random().nextInt(GoodResponse.size())) + " " + questionService.findById(id).getQuestion() + ".  ";
                    }
                }
                topList.put(new Triple<>(dto.getStudent().getFirstName() + " " + dto.getStudent().getLastName(), dto.getStudent().getId(), dto.getExamResult()), aboutUserAnswer);
                userTopAnswer.clear();
                aboutUserAnswer = "";
            }}
        return Pair.of(templateService.findById(templateId).getName(), topList);
    }
}