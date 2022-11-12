package com.example.filesmanager;

import com.example.filesmanager.models.User;
import com.example.filesmanager.models.UserRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/registration")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        if (user != null) {
            resp.sendRedirect("/files-manager/");
            return;
        }

        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (login == null || UserRepository.USER_REPOSITORY.containsUserByLogin(login) || email == null || password == null) {
            return;
        }

        User user = new User(login, password, email);
        UserRepository.USER_REPOSITORY.addUser(user);
        UserRepository.USER_REPOSITORY.addUserBySession(MyCookie.getValue(req.getCookies(), "JSESSIONID"), user);
        resp.sendRedirect("/files-manager/");
    }
}
