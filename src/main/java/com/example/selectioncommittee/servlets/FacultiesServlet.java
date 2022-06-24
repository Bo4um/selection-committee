package com.example.selectioncommittee.servlets;

import com.example.selectioncommittee.beans.Faculty;
import com.example.selectioncommittee.dao.DaoException;
import com.example.selectioncommittee.dao.FacultyDao;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;
@WebServlet(name = "FacultiesServlet", value = "/FacultiesServlet")
public class FacultiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/selection_committee_main" +
                            "?useUnicode=true&characterEncoding=utf8",
                    "root", "dimas");
            FacultyDao facultyDao = new FacultyDao();
            facultyDao.setConnection(conn);
            List<Faculty> faculties = facultyDao.getAll();
            request.setAttribute("faculties", faculties);
            getServletContext().getRequestDispatcher("/faculties.jsp").forward(request, response);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
