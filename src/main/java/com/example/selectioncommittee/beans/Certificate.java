package com.example.selectioncommittee.beans;

public class Certificate {
    private Applicant applicant;
    private double averageScore;

    public Certificate(Applicant appl, double score) {
        applicant = appl;
        averageScore = score;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public double getAverageScore() {
        return averageScore;
    }
}
