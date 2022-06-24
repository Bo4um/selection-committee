package com.example.selectioncommittee.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.selectioncommittee.beans.Subject;

public class SubjectDao implements Dao<Subject> {

    @SuppressWarnings("unused")
    private static Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    private static Map<Long, Subject> subjects = new HashMap<>();

    @Override
    public void update(Subject subject) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE subjects SET name = ? WHERE id = ?");
            statement.setString(1, subject.getName());
            statement.setLong(2, subject.getId());
            statement.executeUpdate();
            subjects.put(subject.getId(), subject);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM subjects WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            subjects.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long create(Subject subject) throws DaoException {
        // TODO Auto-generated method stub
        return createSubject(subject);
    }

    @Override
    public List<Subject> getAll() throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from subjects");
            List<Subject> subjectList = new ArrayList<>();
            while(rs.next()){
                Subject subj = new Subject();
                Long id = rs.getLong("id");
                subj.setId(id);
                subj.setName(rs.getString("name"));
                subjects.putIfAbsent(id, subj);
                subjectList.add(subj);
            }
            return subjectList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Subject get(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from subjects");
            List<Subject> subjectList = new ArrayList<>();
            while(rs.next()){
                Long foundId = rs.getLong("id");
                if(id == foundId){
                    Subject subj = new Subject();
                    subj.setId(id);
                    subj.setName(rs.getString("name"));
                    return subj;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Subject build(String name) {
        Subject subject = new Subject();
        subject.setName(name);
        return subject;
    }

    private static Long createSubject(Subject subject) {
        Long maxId = 0L;
        if(!subjects.isEmpty()) {
            maxId = Collections.max(subjects.keySet());
        }
        maxId++;
        subject.setId(maxId);
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO subjects (name) values ( ? )");
            statement.setString(1, subject.getName());
            statement.executeUpdate();
            subject.setId(maxId);
            subjects.put(maxId, subject);
            return maxId;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        try {
            SubjectDao subjectDao = new SubjectDao();
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/selection_committee_main" +
                            "?useUnicode=true&characterEncoding=utf8",
                    "root", "dimas");
            subjectDao.setConnection(conn);
            List<Subject> subjectList = subjectDao.getAll();
            for(Subject subject: subjectList) {
                System.out.println("Id: " + subject.getId() + " name: " + subject.getName());
            }
            System.out.println();
            Subject newSubject = new Subject();
            newSubject.setId(11L);
            newSubject.setName("Математика");
//            subjectDao.update(newSubject);
            subjectList = subjectDao.getAll();
            for(Subject subject: subjectList) {
                System.out.println("Id: " + subject.getId() + " name: " + subject.getName());
            }
            System.out.println();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
    
}
