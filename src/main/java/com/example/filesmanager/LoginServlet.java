package com.example.filesmanager;

import com.example.filesmanager.models.User;
import com.example.filesmanager.models.UserRepository;
import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UserDataSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        if (user != null) {
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        DBService dbService = new DBService();
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if (login == null || password == null) {
            return;
        }

//        User user = UserRepository.USER_REPOSITORY.getUserByLogin(login);
        UserDataSet user = null;
        try {
            user = dbService.getUser(login);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        if (user == null || !user.getPassword().equals(password)) {
            resp.sendRedirect("/files-manager/login");
            return;
        }
        User user2 = new User(login, password, user.getEmail());
        UserRepository.USER_REPOSITORY.addUserBySession(MyCookie.getValue(req.getCookies(), "JSESSIONID"), user2);
        resp.sendRedirect("/files-manager/");
    }
}
