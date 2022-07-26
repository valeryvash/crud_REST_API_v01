package org.valeryvash.servlet;

import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;
import org.valeryvash.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserViewServlet", value = "/user-view-servlet")
public class UserViewServlet extends HttpServlet {

    private static UserService userService;

    static {
        userService = new UserService(new HibernateUserRepositoryImpl());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Long id = Long.valueOf("id");
        String name = request.getParameter("name");

        User user = null;
        List<User> userList = null;

        switch (action) {
            case "add" -> {
                user = new User();
                user.setName(name);
                userService.add(user);
            }
            case "get" -> {
                user = userService.get(id);
            }
            case "update" -> {
                user = userService.get(id);
                user.setName(name);
                user = userService.update(user);
            }
            case "remove" -> {
                userService.remove(id);
            }
            case "getall" -> {
                userList = userService.getAll();
            }
            default -> {
                throw new IllegalArgumentException("Servlet has no such action");
            }
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if (user != null) {
            start(writer);
            body(writer, user);
            end(writer);
        } else if (userList != null && !userList.isEmpty()) {
            start(writer);
            body(writer, userList);
            end(writer);
        } else {
            throw new ServletException("Unexpected servlet condition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
    private void start(PrintWriter writer) throws IOException {
        writer.println("""
                <!DOCTYPE html>
                <html>
                <head><title> User-view servlet response </head></title> 
                <body>
                <body><h1> User-view servlet response </h1>
                """);
    }

    private void body(PrintWriter writer, User user) {
        writer.println("<h3>User</h3>");
        writer.println("ID : " + user.getId());
        writer.println("<br/>");
        writer.println("Name : " + user.getName());
        writer.println("<br/>");
        writer.println("<br/>");
    }

    private void body(PrintWriter writer, List<User> userList) {
        userList.forEach(user -> body(writer,user));
    }

    private void end(PrintWriter writer) {
        writer.println("</body></html>");
    }

}
