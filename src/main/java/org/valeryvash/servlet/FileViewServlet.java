package org.valeryvash.servlet;

import org.valeryvash.model.File;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        Long id = null;

        File file = null;
        List<File> fileList = null;

        try {
            id = Long.valueOf(request.getParameter("id"));
            file = fileService.get(id);
        } catch (NumberFormatException ignored) {
        }

        if (file == null) {
            fileList = fileService.getAll();
        }

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if (file != null) {
            start(writer);
            body(writer,file);
            end(writer);
        } else if (fileList != null) {
            start(writer);
            if (fileList.isEmpty()) {
                writer.println("<h3>There is no files</h3>");
            } else {
                body(writer,fileList);
            }
            end(writer);
        } else {
            throw new ServletException("Unexpected servlet condition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String filePath = request.getParameter("filePath");

        File file = new File();

        file.setName(name);
        file.setFilePath(filePath);

        file = fileService.add(file);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,file);
        end(writer);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String filePath = request.getParameter("filePath");

        File file = fileService.get(id);

        file.setName(name);
        file.setFilePath(filePath);

        file = fileService.update(file);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,file);
        end(writer);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));

        File file = fileService.remove(id);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
        body(writer,file);
        end(writer);
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
