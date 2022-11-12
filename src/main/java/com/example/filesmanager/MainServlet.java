package com.example.filesmanager;

import com.example.filesmanager.models.User;
import com.example.filesmanager.models.UserRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.util.Date;



@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = UserRepository.USER_REPOSITORY.getUserByCookies(req.getCookies());
        if(user == null) {
            resp.sendRedirect("/files-manager/login");
        }
        else {
            if (user != null){
                String currentPath = req.getParameter("path");
                if (currentPath == null || !currentPath.startsWith("D:/Work/java/files-manager/users/" + user.getLogin())) {
                    currentPath = "D:/Work/java/files-manager/users/"  + user.getLogin();
                    File file = new File(currentPath);
                    if (!file.exists()) {
                        file.mkdir();
                    }
                }
                File file = new File(currentPath);

                if (file.isFile()) {

                    resp.setContentType("text/plain");
                    resp.setHeader("Content-disposition", "attachment; filename=" + file.getName());

                    try (InputStream in = Files.newInputStream(file.toPath()); OutputStream out = resp.getOutputStream()) {
                        byte[] buffer = new byte[4096];

                        int numBytesRead;
                        while ((numBytesRead = in.read(buffer)) > 0) {
                            out.write(buffer, 0, numBytesRead);
                        }
                    }

                }
                showFiles(req, new File(currentPath).listFiles(), currentPath);
                req.setAttribute("currentPath", currentPath);
                req.getRequestDispatcher("mypage.jsp").forward(req, resp);
            }
            else {
                resp.sendRedirect( "/auth");
            }
        }
    }

    private void showFiles(HttpServletRequest req, File[] files, String currentPath) {
        String currentDate = new Date().toString();
        StringBuilder attrFolders = new StringBuilder();
        StringBuilder attrFiles = new StringBuilder();
        for (File file : files) {
            if (file.isDirectory()) {
                attrFolders.append("<li><a href=\"?path=").append(currentPath).append("/").append(file.getName())
                        .append("\">")
                        .append(file.getName())
                        .append("</a></li>");
            } else {
                attrFiles.append("<li><a href=\"?path=").append(currentPath).append("/").append(file.getName())
                        .append("\">")
                        .append(file.getName())
                        .append("</a></li>");
            }
        }
        req.setAttribute("currentTime", currentDate);
        req.setAttribute("folders", attrFolders);
        req.setAttribute("files", attrFiles);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("exitBtn") != null) {
            UserRepository.USER_REPOSITORY.removeUserBySession(MyCookie.getValue(req.getCookies(), "JSESSIONID"));
            MyCookie.addCookie(resp, "JSESSIONID", null);
            resp.sendRedirect("/files-manager/");
        }
    }
}