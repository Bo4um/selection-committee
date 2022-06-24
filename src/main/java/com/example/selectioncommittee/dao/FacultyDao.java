package com.example.selectioncommittee.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.selectioncommittee.beans.Faculty;
import com.example.selectioncommittee.beans.Subject;

public class FacultyDao implements Dao<Faculty>{

    private static Map<Long, Faculty> faculties = new HashMap<>();

    @SuppressWarnings("unused")
    private static Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void update(Faculty faculty) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE faculties SET places = ? WHERE id = ?");
            statement.setInt(1, faculty.getPlaces());
            statement.setLong(2, faculty.getId());
            statement.executeUpdate();
            faculties.put(faculty.getId(), faculty);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM faculties WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
            faculties.remove(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long create(Faculty faculty) throws DaoException {
        // TODO Auto-generated method stub
        return createFaculty(faculty);
    }

    @Override
    public List<Faculty> getAll() throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from faculties");
            List<Faculty> facultyList = new ArrayList<>();
            while(rs.next()){
                Faculty faculty = new Faculty();
                Long id = rs.getLong("id");
                faculty.setId(id);
                faculty.setName(rs.getString("name"));
                faculty.setPlaces(rs.getInt("places"));
                faculties.putIfAbsent(id, faculty);
                facultyList.add(faculty);
            }
            return facultyList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Faculty get(Long id) throws DaoException {
        // TODO Auto-generated method stub
        try {
            Statement statement = connection.createStatement();
            statement.execute("set names utf8");
            ResultSet rs = statement.executeQuery("SELECT * from faculties");
            while(rs.next()){
                Long foundId = rs.getLong("id");
                if(id == foundId){
                    Faculty faculty = new Faculty();
                    faculty.setId(foundId);
                    faculty.setName(rs.getString("name"));
                    faculty.setPlaces(rs.getInt("places"));
                    return faculty;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Faculty build(String name, int places, List<Subject> subjects) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setPlaces(places);
        faculty.setSubjects(subjects);
        return faculty;
    }

    private static Long createFaculty(Faculty faculty) {
        Long maxId = 0L;
        if(!faculties.isEmpty()) {
            maxId = Collections.max(faculties.keySet());
        }
        maxId++;
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO faculties (name, places) values (?, ?)");
            statement.setString(1, faculty.getName());
            statement.setInt(2, faculty.getPlaces());
            statement.executeUpdate();
            faculty.setId(maxId);
            faculties.put(maxId, faculty);
            return maxId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
}
