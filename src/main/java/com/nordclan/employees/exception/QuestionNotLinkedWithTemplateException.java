package com.nordclan.employees.exception;

public class QuestionNotLinkedWithTemplateException extends IllegalArgumentException {

    public static final String message = "Вопрос с ID [%s] не связан с шаблоном с ID [%s]";

    public QuestionNotLinkedWithTemplateException(Long questionId, Long templateId) {
        super(message.formatted(questionId, templateId));
    }

}