package com.example.selectioncommittee.dao;

import com.example.selectioncommittee.beans.Applicant;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApplicantDao implements Dao<Applicant>{
    private static Map<Long, Applicant> applicants = new HashMap<>();
    @SuppressWarnings("unused")
    private static Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

    @Override
    public void update(Applicant appl) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE applicants SET birthday_date = ?, first_name = ?, second_name = ?, patronymic = ? WHERE id = ?");
            statement.setDate(1, (java.sql.Date) appl.getBirthday());
            statement.setString(2, appl.getFirstName());
            statement.setString(3, appl.getSecondName());
            statement.setString(4, appl.getPatronymic());
            statement.setLong(5, appl.getId());
            statement.executeUpdate();
            applicants.put(appl.getId(), appl);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM applicants WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            applicants.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long create(Applicant appl) throws DaoException {
        // TODO Auto-generated method stub
        return createApplicant(appl);
    }

    @Override
    public List<Applicant> getAll() throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from applicants");
            List<Applicant> applicantList = new ArrayList<>();
            while(rs.next()){
                Applicant applicant = new Applicant();
                Long id = rs.getLong("id");
                applicant.setId(id);
                applicant.setBirthday(rs.getDate("birthday_date"));
                applicant.setFirstName(rs.getString("first_name"));
                applicant.setSecondName(rs.getString("second_name"));
                applicant.setPatronymic(rs.getString("patronymic"));
                applicants.putIfAbsent(id, applicant);
                applicantList.add(applicant);
            }
            return applicantList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Applicant get(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from applicants");
            while(rs.next()){
                Long foundId = rs.getLong("id");
                if(id == foundId) {
                    Applicant applicant = new Applicant();
                    applicant.setId(id);
                    applicant.setBirthday(rs.getDate("birthday_date"));
                    applicant.setFirstName(rs.getString("first_name"));
                    applicant.setSecondName(rs.getString("second_name"));
                    applicant.setPatronymic(rs.getString("patronymic"));
                    return applicant;
                }

            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Applicant build(Date birthday, String firstName, String secondName, String patronymic) {
        Applicant applicant = new Applicant();
        applicant.setBirthday(birthday);
        applicant.setFirstName(firstName);
        applicant.setSecondName(secondName);
        applicant.setPatronymic(patronymic);
        return applicant;
    }
    
    private static Long createApplicant(Applicant appl) {
        Long maxId = 0L;
        if(!applicants.isEmpty()) {
            maxId = Collections.max(applicants.keySet());
        }
        maxId++;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO applicants (birthday_date, first_name,  second_name, patronymic) values (?, ?, ?, ?)");
            statement.setDate(1, (java.sql.Date) appl.getBirthday());
            statement.setString(2, appl.getFirstName());
            statement.setString(3, appl.getSecondName());
            statement.setString(4, appl.getPatronymic());
            statement.executeUpdate();
            appl.setId(maxId);
            applicants.put(maxId, appl);
            return maxId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
