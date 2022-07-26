package org.valeryvash.servlet;

import org.valeryvash.model.Event;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;
import org.valeryvash.service.EventService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(name = "EventViewServlet", value = "/event-view-servlet")
public class EventViewServlet extends HttpServlet {

    private static EventService eventService;

    static {
        eventService = new EventService(new HibernateEventRepositoryImpl());
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        Long id = Long.valueOf(request.getParameter("id"));
        Long fileId = Long.valueOf(request.getParameter("file_id"));
        Date date = new Date();
        EventType eventType = EventType.valueOf(request.getParameter("eventType"));
        Long userId = Long.valueOf(request.getParameter("userId"));

        Event event = null;
        List<Event> eventList = null;

        switch (action) {
            case "add" -> {
                
            }
            case "get" -> {
                event = eventService.get(id);
            }
            case "update" -> {
                event = eventService.get(id);
                event.setEventType(eventType);
                event.setTimestamp(date);
                eventService.update(event);
            }
            case "remove" -> {
                event = eventService.remove(id);
            }
            case "getAll" -> {
                eventList = eventService.getAll();
            }
            default -> {
                throw new IllegalArgumentException("Servlet has no such action");
            }
        }

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
        doGet(request, response);
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
