package com.nordclan.employees.entity;

import lombok.Getter;

@Getter
public enum RoleNames {

    USER("Пользователь"),

    MENTOR("Наставник"),

    ADMIN("Администратор"),

    HIRING_HEAD("Руководитель найма"),

    TECHNICAL_EXPERT("Технический эксперт"),

    HR_SPECIALIST("Специалист по HR"),

    TRAINING_HEAD("Руководитель обучения"),

    SUPER_ADMIN("Супер администратор"),

    GROUP_HEAD("Руководитель группы");

    private final String name;

    RoleNames(String name) {
        this.name = name;
    }

    public static String getRussianNameByRole(String role) {
        for (RoleNames roleName : values()) {
            if (roleName.name().equalsIgnoreCase(role)) {
                return roleName.getName();
            }
        }
        return null;
    }

}