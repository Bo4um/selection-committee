package com.example.selectioncommittee.beans;

import java.util.Date;

public class Applicant {
    private Long id;
    private Date birthday;
    private String firstName;
    private String secondName;
    private String patronymic;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public void setBirthday(Date date) {
        birthday = date;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setFirstName(String str) {
        firstName = str;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setSecondName(String str) {
        secondName = str;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setPatronymic(String str) {
        patronymic = str;
    }

    public String getPatronymic() {
        return patronymic;
    }
}
