package org.valeryvash.servlet;

import org.valeryvash.model.Event;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.service.EventService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "EventRestControllerV1", value = "/api/v1/events/*")
public class EventRestControllerV1 extends HttpServlet {

    private final String URI = "/api/v1/events";
    private final EventService eventService;

    public EventRestControllerV1() {
        this.eventService = new EventService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long fileId = Long.valueOf(request.getParameter("file_id"));
        EventType eventType = EventType.valueOf(request.getParameter("eventType"));
        Long userId = Long.valueOf(request.getParameter("userId"));

        Event event = null;
        List<Event> eventList = null;


        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();

        if (event != null) {
            start(writer);
            body(writer, event);
            end(writer);
        } else if (eventList != null && !eventList.isEmpty()) {
            start(writer);
            body(writer, eventList);
            end(writer);
        } else {
            throw new ServletException("Unexpected servlet condition");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long fileId = Long.valueOf(request.getParameter("file_id"));
        EventType eventType = EventType.valueOf(request.getParameter("eventType"));
        Long userId = Long.valueOf(request.getParameter("userId"));

        Event event = new Event();
        File file = null;
        User user = null;

        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();


        start(writer);
        body(writer, event);
        end(writer);

    }

    private void start(PrintWriter writer) throws IOException {
        writer.println("""
                <!DOCTYPE html>
                <html>
                <head><title> Event-view servlet response </head></title> 
                <body>
                <body><h1> Event-view servlet response </h1>
                """);
    }

    private void body(PrintWriter writer, Event event) {
        writer.println("<h3>Event</h3>");
        writer.println("ID : " + event.getId());
        writer.println("<br/>");
        writer.println("Event type : " + event.getEventType());
        writer.println("<br/>");
        writer.println("Date : " + event.getTimestamp().toString());
        writer.println("FileId: " + event.getFile().getId());
        writer.println("UserId: " + event.getUser().getId());
        writer.println("<br/>");
        writer.println("<br/>");
    }

    private void body(PrintWriter writer, List<Event> eventList) {
        eventList.forEach(event -> body(writer,event));
    }

    private void end(PrintWriter writer) {
        writer.println("</body></html>");
    }



}
