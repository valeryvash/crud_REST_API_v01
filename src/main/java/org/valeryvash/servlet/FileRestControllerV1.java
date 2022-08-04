package org.valeryvash.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.valeryvash.dto.FileDto;
import org.valeryvash.model.File;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "FileRestControllerV1", value = "/api/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {

    private static final String URI = "/api/v1/files";
    private final FileService fileService;

    private final Gson gson = new Gson().newBuilder().create();

    public FileRestControllerV1() {
        this.fileService = new FileService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StringBuffer sb = new StringBuffer();
        String line = null;

        try {
            BufferedReader br = request.getReader();
            while ((line = br.readLine()) != null) {
                sb.append(line);
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FileDto fileDto = gson.fromJson(sb.toString(), FileDto.class);

        System.out.println(fileDto);

        fileDto = fileService.add(fileDto);

        String responseJson = gson.toJson(fileDto);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(responseJson);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();

        if (uri.length() == URI.length() ){

        }

        Long id = Long.valueOf(uri.substring(URI.length()+1));

        FileDto fileDto = fileService.get(id);

        String json = new GsonBuilder().create().toJson(fileDto);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(json);
    }



    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        String name = request.getParameter("name");
        String filePath = request.getParameter("filePath");

        FileDto fileDto = fileService.get(id);


        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
//        body(writer,file);
        end(writer);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));

//        File file = fileService.remove(id);

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        start(writer);
//        body(writer,file);
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
