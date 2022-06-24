package com.example.selectioncommittee.servlets;

import com.example.selectioncommittee.beans.Applicant;
import com.example.selectioncommittee.dao.ApplicantDao;
import com.example.selectioncommittee.dao.DaoException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ApplicantsServlet", value = "/ApplicantsServlet")
public class ApplicantsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/selection_committee_main" +
                            "?useUnicode=true&characterEncoding=utf8",
                    "root", "dimas");
            ApplicantDao applicantDao = new ApplicantDao();
            applicantDao.setConnection(conn);
            List<Applicant> applicants = applicantDao.getAll();
            request.setAttribute("applicants", applicants);
            getServletContext().getRequestDispatcher("/applicants.jsp").forward(request, response);
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
