package org.valeryvash.servlet;

import org.valeryvash.model.File;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.service.FileService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "FileViewServlet", value = "/file-view-servlet")
public class FileViewServlet extends HttpServlet {

    private static FileService fileService;

    static {
        fileService = new FileService(new HibernateFileRepositoryImpl());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action").toLowerCase();

        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String filePath = request.getParameter("filePath");

        File file = null;
        List<File> fileList = null;

        switch (action) {
            case "add" -> {
                file = new File();
                file.setFilePath(filePath);
                file.setName(name);
                file = fileService.add(file);
            }
            case "get" -> {
                file = fileService.get(id);
            }
            case "update" -> {
                //TODO check this method, may be we need to get(entityId) instead of new File()...set...
                file = new File();
                file.setId(id);
                file.setFilePath(filePath);
                file.setName(name);
                file = fileService.update(file);
            }
            case "remove" -> {
                file = fileService.remove(id);
            }
            case "getall" -> {
                fileList = fileService.getAll();
            }
            default -> {
                throw new IllegalArgumentException("Servlet has no such action");
            }
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if (file != null) {
            start(writer);
            body(writer,file);
            end(writer);
        } else if (fileList != null && !fileList.isEmpty()) {
            start(writer);
            body(writer,fileList);
            end(writer);
        } else {
            throw new ServletException("Unexpected action");
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
                <head><title> File-view servlet response </head></title> 
                <body>
                <body><h1> File-view servlet response </h1>
                """);
    }

    private void body(PrintWriter writer, File file) {
        writer.println("<h3>File</h3>");
        writer.println("ID : " + file.getId());
        writer.println("<br/>");
        writer.println("Name : " + file.getName());
        writer.println("<br/>");
        writer.println("Path to file: " + file.getFilePath());
        writer.println("<br/>");
        writer.println("<br/>");
    }

    private void body(PrintWriter writer, List<File> fileList) {
        fileList.forEach(file -> body(writer,file));
    }

    private void end(PrintWriter writer) {
        writer.println("</body></html>");
    }
}
