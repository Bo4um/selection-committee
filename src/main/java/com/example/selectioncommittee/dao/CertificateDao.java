package com.example.selectioncommittee.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.selectioncommittee.beans.Applicant;
import com.example.selectioncommittee.beans.Certificate;

public class CertificateDao implements Dao<Certificate> {

    @SuppressWarnings("unused")
    private static Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    private static Map<Long, Certificate> certificates = new HashMap<>();

    @Override
    public void update(Certificate certificate) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE certificates SET average_score = ? WHERE id = ?");
            statement.setDouble(1, certificate.getAverageScore());
            statement.setLong(2, certificate.getApplicant().getId());
            statement.executeUpdate();
            certificates.put(certificate.getApplicant().getId(), certificate);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM certificates WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            certificates.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long create(Certificate certificate) throws DaoException {
        // TODO Auto-generated method stub
        return createCertificate(certificate);
    }

    @Override
    public List<Certificate> getAll() throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from certificates");
            List<Certificate> certificateList = new ArrayList<>();
            ApplicantDao applicantDao = new ApplicantDao();
            while(rs.next()){
                certificateList.add(new Certificate(applicantDao.get(rs.getLong("id")), rs.getDouble("average_score")));
            }
            return certificateList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Certificate get(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from certificates");
            ApplicantDao applicantDao = new ApplicantDao();
            while(rs.next()){
                Long foundId = rs.getLong("id");
                if(id == foundId){
                    return new Certificate(applicantDao.get(foundId), rs.getDouble("average_score"));
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    } 

    public static Certificate build(Applicant applicant, double averageScore) {
        return new Certificate(applicant, averageScore);
    }

    private static Long createCertificate(Certificate certificate) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO certificates (id, average_score) values (?, ?)");
            Long id = certificate.getApplicant().getId();
            statement.setLong(1, id);
            statement.setDouble(2, certificate.getAverageScore());
            statement.executeUpdate();
            certificates.put(id, certificate);
            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
