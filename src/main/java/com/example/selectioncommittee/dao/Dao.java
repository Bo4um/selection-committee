package com.example.selectioncommittee.dao;

import java.util.List;

public interface Dao<T> {
    void update(T t) throws DaoException;
    void delete(Long id) throws DaoException;
    Long create(T t) throws DaoException;
    List<T> getAll() throws DaoException;
    T get(Long id) throws DaoException;
}