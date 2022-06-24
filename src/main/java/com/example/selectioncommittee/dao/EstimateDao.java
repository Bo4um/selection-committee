package com.example.selectioncommittee.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.selectioncommittee.beans.Applicant;
import com.example.selectioncommittee.beans.Estimate;
import com.example.selectioncommittee.beans.Subject;

public class EstimateDao implements Dao<Estimate> {

    @SuppressWarnings("unused")
    private static Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    private static Map<Long, Estimate> estimates = new HashMap<>();

    @Override
    public void update(Estimate estimate) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE estimates SET subject_id = ?, estimate = ? WHERE applicant_id = ?");
            statement.setObject(1, estimate.getSubject());
            statement.setInt(2, estimate.getEstimate());
            statement.setLong(3, estimate.getApplicant().getId());
            statement.executeUpdate();
            estimates.put(estimate.getId(), estimate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM estimates WHERE applicant_id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            estimates.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Long create(Estimate estimate) throws DaoException {
        // TODO Auto-generated method stub
        return createEstimate(estimate);
    }

    @Override
    public List<Estimate> getAll() throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from estimates");
            List<Estimate> estimateList = new ArrayList<>();
            while(rs.next()){
                ApplicantDao applicantDao = new ApplicantDao();
                SubjectDao subjectDao = new SubjectDao();
                Estimate estimate = new Estimate(applicantDao.get(rs.getLong("applicant_id")), subjectDao.get(rs.getLong("subject_id")), rs.getInt("estimate"));
                Long id = rs.getLong("applicant_id");
                estimates.putIfAbsent(id, estimate);
                estimateList.add(estimate);
            }
            return estimateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Estimate get(Long id) throws DaoException {
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from estimates");
            while(rs.next()){
                Long foundId = rs.getLong("applicants_id");
                if(id == foundId) {
                    ApplicantDao applicantDao = new ApplicantDao();
                    SubjectDao subjectDao = new SubjectDao();
                    return new Estimate(applicantDao.get(rs.getLong("applicant_id")), subjectDao.get(rs.getLong("subject_id")), rs.getInt("estimate"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Estimate build(Applicant applicant, Subject subject, int estimate) {
        return new Estimate(applicant, subject, estimate);
    }

    private static Long createEstimate(Estimate estimate) {
        Long maxId = 0L;
        if(!estimates.isEmpty()) {
            maxId = Collections.max(estimates.keySet());
        }
        maxId++;
        estimate.setId(maxId);
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (applicant_id, subject_id, estimate) values ( ?, ?, ? )");
            statement.setObject(1, estimate.getApplicant());
            statement.setObject(2, estimate.getSubject());
            statement.setInt(3, estimate.getEstimate());
            statement.executeUpdate();
            estimate.setId(maxId);
            estimates.put(maxId, estimate);
            return maxId;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
