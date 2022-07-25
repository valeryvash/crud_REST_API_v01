package org.valeryvash.servlet;

import org.valeryvash.model.File;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.service.FileService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
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

        if (file != null) {
            // here we print object in response
        } else if (fileList != null && !fileList.isEmpty()) {
            // or here we print list of objects in response
        } else {
            throw new ServletException("Unexpected action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
