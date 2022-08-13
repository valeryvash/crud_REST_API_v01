package org.valeryvash.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.valeryvash.dto.FileDto;
import org.valeryvash.dto.UserDto;
import org.valeryvash.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserRestControllerV1", value = "/api/v1/users/*")
public class UserRestControllerV1 extends HttpServlet {

    private final String URI = "/api/v1/users";
    private final UserService userService;

    private final Gson gson;

    public UserRestControllerV1() {
        this.userService = new UserService();
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

        UserDto userDto = null;
        List<UserDto> userDtos = null;

        if (uri.length() != URI.length() ){
            Long id = Long.valueOf(uri.substring(URI.length()+1));
            userDto = userService.get(id);
            responsePrinter(response,userDto);
        } else {
            userDtos = userService.getAll();
            responsePrinter(response,userDtos);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fixme add work method body
        doGet(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //fixme add work method body
        doGet(request, response);
    }

    private void responsePrinter(HttpServletResponse response, UserDto userDto) throws IOException {
        String json = gson.toJson(userDto);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(json);
    }

    private void responsePrinter(HttpServletResponse response, List<UserDto> users) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");

        ServletOutputStream responseOutputStream = response.getOutputStream();

        for (UserDto user : users) {
            String s = gson.toJson(user);
            responseOutputStream.println(s);
        }
    }

}
