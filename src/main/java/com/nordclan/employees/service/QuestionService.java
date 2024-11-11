package com.nordclan.employees.service;

import com.nordclan.employees.api.DefaultService;
import com.nordclan.employees.dto.create.CreateQuestionDto;
import com.nordclan.employees.dto.entity.GroupDto;
import com.nordclan.employees.dto.entity.QuestionDto;
import com.nordclan.employees.dto.entity.UserDto;
import com.nordclan.employees.entity.Group;
import com.nordclan.employees.entity.Question;
import com.nordclan.employees.exception.GroupNotFoundException;
import com.nordclan.employees.exception.QuestionNotFoundException;
import com.nordclan.employees.mapper.QuestionMapper;
import com.nordclan.employees.repository.GroupRepository;
import com.nordclan.employees.repository.QuestionRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nordclan.employees.utils.CommonUtils.setIfNotBlank;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
@Transactional
public class QuestionService extends DefaultService<Long, Question, QuestionDto> {
    QuestionRepository questionRepository;
    QuestionMapper questionMapper;
    GroupService groupService;
    GroupRepository groupRepository;
    SecurityService securityService;

    public QuestionService(
        QuestionMapper questionMapper,
        QuestionRepository questionRepository,
        GroupService groupService,
        GroupRepository groupRepository,
        SecurityService securityService
    ) {
        super(questionRepository, questionMapper, QuestionNotFoundException::new);
        this.questionMapper = questionMapper;
        this.questionRepository = questionRepository;
        this.groupService = groupService;
        this.groupRepository = groupRepository;
        this.securityService = securityService;
    }

    public List<QuestionDto> findAllByGroup(Long groupId) throws GroupNotFoundException {
        return this.findAllByExample(QuestionDto.builder()
                .group(GroupDto.builder()
                    .id(groupService.findById(groupId).getId())
                    .build())
                .build());
    }

    public QuestionDto create(CreateQuestionDto request) {
        final UserDto currentUser = securityService.getAuthorizedUser();

        final Group group = groupService.findByIdInternal(request.getGroup().getId());

        final Question question = Question.builder()
            .question(request.getQuestion())
            .answer(request.getAnswer())
            .group(group)
            .questionLevels(request.getQuestionLevels())
            .type(request.getType())
            .author(String.format("%s %s",
                currentUser.getFirstName(),
                currentUser.getLastName()))
            .link(request.getLink())
            .build();

        return questionMapper.toDto(questionRepository.save(question));
    }

    @Override
    protected Question updateInternal(Question question, QuestionDto questionDto) {
        setIfNotBlank(questionDto.getQuestion(), question::setQuestion);
        setIfNotBlank(questionDto.getAuthor(), question::setAuthor);
        setIfNotBlank(questionDto.getLink(), question::setLink);
        setIfNotBlank(questionDto.getAnswer(), question::setAnswer);
        setIfNotBlank(questionDto.getQuestionLevels(), question::setQuestionLevels);
        setIfNotBlank(questionDto.getType(), question::setType);
        if (questionDto.getGroup() != null && questionDto.getGroup().getId() != null) {
            setIfNotBlank(
                groupService.findByIdInternal(questionDto.getGroup().getId()),
                question::setGroup);
        }
        return question;
    }
}