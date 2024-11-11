package com.nordclan.employees.dto.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO пользователя")
public class UserDto {

    @ApiModelProperty(required = true, value = "id пользователя")
    private UUID id;

    @ApiModelProperty(required = true, value = "Имя пользователя")
    private String firstName;

    @ApiModelProperty(required = true, value = "Фамилия пользователя")
    private String lastName;

    @ApiModelProperty(value = "Должность пользователя")
    private String post;

    @ApiModelProperty(value = "Отчество пользователя")
    private String surname;

    @ApiModelProperty(required = true, value = "Дата создания пользователя")
    private LocalDate creationDate;

    @ApiModelProperty(value = "Дата обновления пользователя")
    private LocalDate updateDate;

    @ApiModelProperty(required = true, value = "Признак удаления пользователя")
    private Boolean isDeleted;

    @ApiModelProperty(required = true, value = "Признак обладания правами администратора")
    private Boolean isRoot;

    @ApiModelProperty(value = "Login shell пользователя")
    private String loginShell;

    @ApiModelProperty(required = true, value = "Признак активности пользователя")
    private Boolean isActive;

    @ApiModelProperty(required = true, value = "Почта пользователя")
    private String mail;

    @ApiModelProperty(required = true, value = "Телеграмм пользователя")
    private String telegram;

    @ApiModelProperty(required = true, value = "Дата рождения пользователя")
    private LocalDate birthday;

    @ApiModelProperty(required = true, value = "Первый рабочий день пользователя")
    private LocalDate firstDay;

    @ApiModelProperty(value = "Последний рабочий день пользователя")
    private LocalDate lastDay;

    @ApiModelProperty(required = true, value = "Город проживания пользователя")
    private String city;

    @ApiModelProperty(value = "Аватар пользователя")
    private String photo;

    private Set<RoleDto> roles;

    private Set<Long> roleIds;



    public UserDto(UUID id, String firstName, String lastName, String post, String surname, LocalDate creationDate, LocalDate updateDate, Boolean isDeleted, Boolean isRoot, String loginShell, Boolean isActive, String mail, String telegram, LocalDate birthday, LocalDate firstDay, LocalDate lastDay, String city, String photo) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.post = post;
        this.surname = surname;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.isDeleted = isDeleted;
        this.isRoot = isRoot;
        this.loginShell = loginShell;
        this.isActive = isActive;
        this.mail = mail;
        this.telegram = telegram;
        this.birthday = birthday;
        this.firstDay = firstDay;
        this.lastDay = lastDay;
        this.city = city;
        this.photo = photo;

        this.roles = null;
        this.roleIds = null;

    }
}