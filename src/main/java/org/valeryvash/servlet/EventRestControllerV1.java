package org.valeryvash.servlet;

import com.google.gson.Gson;
import org.valeryvash.dto.EventDto;
import org.valeryvash.service.EventService;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "EventRestControllerV1", value = "/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {

    private final String URI = "/api/v1/events";
    private final EventService eventService;

    private final Gson gson;

    public EventRestControllerV1() {
        this.eventService = new EventService();
        this.gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = stringRequestReader(request);

        EventDto eventDto = gson.fromJson(jsonString,EventDto.class);

        eventDto = eventService.add(eventDto);

        responsePrinter(response,eventDto);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String value = request.getRequestURI().substring(URI.length()+1);

        EventDto eventDto = null;
        List<EventDto> eventDtos = null;

        if (!value.equals("")){
            Long id = Long.valueOf(value);
            eventDto = eventService.get(id);
            responsePrinter(response,eventDto);
        } else {
            eventDtos = eventService.getAll();
            responsePrinter(response,eventDtos);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jsonString = stringRequestReader(request);

        EventDto eventDto = gson.fromJson(jsonString, EventDto.class);

        eventDto = eventService.update(eventDto);

        responsePrinter(response,eventDto);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();

        Long id = Long.valueOf(uri.substring(URI.length()+1));

        EventDto eventDto = eventService.remove(id);

        responsePrinter(response,eventDto);
    }

    private void responsePrinter(HttpServletResponse response, EventDto eventDto) throws IOException {
        String json = gson.toJson(eventDto);

        response.setStatus(200);
        response.setContentType("application/json");
        response.getOutputStream().println(json);
    }

    private void responsePrinter(HttpServletResponse response, List<EventDto> events) throws IOException {
        response.setStatus(200);
        response.setContentType("application/json");

        ServletOutputStream responseOutputStream = response.getOutputStream();

        for (EventDto event : events) {
            String s = gson.toJson(event);
            responseOutputStream.println(s);
        }
    }

    private String stringRequestReader(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader br = request.getReader()) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }

        return sb.toString();
    }

}
