package org.valeryvash.util;

import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.util.List;

public class EntityConsolePrinter {

    public static void print(Object entity) {
        if (entity == null) {
            throw new NullPointerException("Null in EntityConsolePrinter.print(Object object) method");
        } else if (entity instanceof User) {
            print((User) entity);
        } else if (entity instanceof Event) {
            print((Event) entity);
        } else if (entity instanceof File) {
            print((File) entity);
        } else {
            System.out.println("No such entity");
        }
    }

    private static void print(User user) {
        System.out.printf(
                "UserRepository: [ id: %0,10d | name: %-25s ]\n",
                user.getId(),
                user.getName()
        );

        List<Event> eventList = user.getEvents();

        if (eventList != null && !eventList.isEmpty()) {
            eventList.forEach(EntityConsolePrinter::print);
        } else {
            System.out.println("UserRepository has no events");
        }
    }

    private static void print(Event event) {
        System.out.printf(
                "Event: [ id: %0,10d | event type: %-25s | time stamp: %-25d ]\n",
                event.getId(),
                event.getEventType().toString(),
                event.getTimestamp().toString()
        );

        File eventFile = event.getFile();

        if (eventFile != null) {
            print(eventFile);
        } else {
            System.out.println("Event has no file");
            throw new NullPointerException("event.getFile() return null in EntityConsolePrinter class");
        }
    }

    private static void print(File file) {
        System.out.printf(
                "File: [ id: %0,10d | name: %-25s | file path: %-25s ]\n",
                file.getId(),
                file.getName(),
                file.getFilePath()
        );
    }
}
