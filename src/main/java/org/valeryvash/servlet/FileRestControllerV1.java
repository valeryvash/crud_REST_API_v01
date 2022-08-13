package org.valeryvash.servlet;

import com.google.gson.Gson;
import org.valeryvash.dto.FileDto;
import org.valeryvash.service.FileService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FileRestControllerV1", value = "/api/v1/files/*")
public class FileRestControllerV1 extends HttpServlet {

    private final String URI = "/api/v1/files";
    private final FileService fileService;

    private final Gson gson;

    public FileRestControllerV1() {
        this.fileService = new FileService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fixme add method body
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();

        FileDto fileDto = null;
        List<FileDto> fileDtos = null;

        if (uri.length() != URI.length() ){
            Long id = Long.valueOf(uri.substring(URI.length()+1));
            fileDto = fileService.get(id);
            responsePrinter(response,fileDto);
        } else {
            fileDtos = fileService.getAll();
            responsePrinter(response,fileDtos);
        }

    }

    private void responsePrinter(HttpServletResponse response, FileDto fileDto) throws IOException {
        String json = gson.toJson(fileDto);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(json);
    }

    private void responsePrinter(HttpServletResponse response, List<FileDto> files) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");

        ServletOutputStream responseOutputStream = response.getOutputStream();

        for (FileDto file : files) {
            String s = gson.toJson(file);
            responseOutputStream.println(s);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fixme add method body
        doGet(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fixme add method body
        doGet(request, response);
    }

}
