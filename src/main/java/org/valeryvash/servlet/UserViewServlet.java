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
import java.util.Map;

@WebServlet(name = "UserViewServlet", value = "/user-view-servlet")
public class UserViewServlet extends HttpServlet {

    private static UserService userService;

    static {
        userService = new UserService(new HibernateUserRepositoryImpl());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long id = null;

        User user = null;
        List<User> userList = null;

        try {
            id = Long.valueOf("id");
            user = userService.get(id);
        } catch (NumberFormatException ignored) {
        }

        if (user == null) {
            userList = userService.getAll();
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if (user != null) {
            start(writer);
            body(writer, user);
            end(writer);
        } else if (userList != null) {
            start(writer);
            if (userList.isEmpty()) {
                writer.println("<h3>There is no users</h3>");
            } else {
                body(writer, userList);
            }
            end(writer);
        } else {
            throw new ServletException("Unexpected servlet condition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        User user = new User();
        user.setName(name);

        user = userService.add(user);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,user);
        end(writer);
    }


    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");

        User user = userService.get(id);
        user.setName(name);

        user = userService.update(user);

        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,user);
        end(writer);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));

        User user = userService.remove(id);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,user);
        end(writer);
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
