package com.example.selectioncommittee.beans;

public class Estimate {
    private Long id;
    private Applicant applicant;
    private Subject subject;
    private int estimate;

    public Estimate(Applicant appl, Subject subj, int est) {
        applicant = appl;
        subject = subj;
        estimate = est;
    }

    public void setId(Long newId) {
        id = newId;
    }

    public Long getId() {
        return id;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getEstimate() {
        return estimate;
    }
}
