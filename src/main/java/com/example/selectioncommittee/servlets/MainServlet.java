package com.example.selectioncommittee.servlets;

import com.example.selectioncommittee.beans.Applicant;
import com.example.selectioncommittee.beans.Faculty;
import com.example.selectioncommittee.dao.ApplicantDao;
import com.example.selectioncommittee.dao.CertificateDao;
import com.example.selectioncommittee.dao.DaoException;
import com.example.selectioncommittee.dao.FacultyDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(name = "MainServlet", value = "/MainServlet")
public class MainServlet extends HttpServlet {
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
            getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/selection_committee_main" +
                            "?useUnicode=true&characterEncoding=utf8",
                    "root", "dimas");
            ApplicantDao applicantDao = new ApplicantDao();
//            CertificateDao certificateDao = new CertificateDao();
            applicantDao.setConnection(conn);
            String first_name = request.getParameter("first_name");
            String second_name = request.getParameter("second_name");
            String patronymic = request.getParameter("patronymic");
            LocalDate date = LocalDate.parse(request.getParameter("birthday"));
            Date birthday = java.sql.Date.valueOf(date);
//            double average_score = Double.parseDouble(request.getParameter("average_score"));
            if(first_name != null && second_name != null && patronymic != null && birthday != null){
                Applicant applicant = ApplicantDao.build(birthday, first_name, second_name, patronymic);
                applicantDao.create(applicant);
//                certificateDao.setConnection(conn);
//                certificateDao.create(CertificateDao.build(applicant, average_score));
                request.setAttribute("first_name", first_name);
                request.setAttribute("second_name", second_name);
                request.setAttribute("patronymic", patronymic);
                getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }

    }
}
