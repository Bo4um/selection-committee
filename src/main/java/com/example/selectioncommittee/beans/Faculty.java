package com.example.selectioncommittee.beans;

import java.util.List;

public class Faculty {
    private Long id;
    private String name;
    private int places;
    private List<Subject> subjects;

    public Long getId() {
        return id;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public void setName(String str) {
        name = str;
    }

    public String getName() {
        return name;
    }

    public void setPlaces(int count) {
        places = count;
    }

    public int getPlaces() {
        return places;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> newSubjects) {
        subjects = newSubjects;
    }
}