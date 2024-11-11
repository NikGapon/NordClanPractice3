package com.nordclan.employees.repository;

import com.nordclan.employees.api.DefaultRepository;
import com.nordclan.employees.entity.QuestionTemplate;
import com.nordclan.employees.entity.QuestionTemplateId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionTemplateRepository extends DefaultRepository<QuestionTemplate, QuestionTemplateId> {

    @Query(value = "SELECT * FROM question_template " + "WHERE question_id = :questionId AND template_id = :templateId", nativeQuery = true)
    Optional<QuestionTemplate> findQuestionTemplateRelation(Long questionId, Long templateId);


    @Query(value = "SELECT question_id FROM question_template WHERE template_id = :templateId", nativeQuery = true)
    List<Long> findQuestionIdsByTemplateId(Long templateId);


    @Modifying
    @Query(value = "DELETE FROM question_template WHERE template_id = :templateId AND question_id NOT IN (:questionIds)", nativeQuery = true)
    void deleteTemplateQuestionsExcept(Long templateId, List<Long> questionIds);

}