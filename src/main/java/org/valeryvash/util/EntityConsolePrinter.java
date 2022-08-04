package org.valeryvash.util;

import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;

import java.util.List;

public class EntityConsolePrinter {


    public static void print(User user) {
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

    public static void print(File file) {
        System.out.printf(
                "File: [ id: %0,10d | name: %-25s | file path: %-25s ]\n",
                file.getId(),
                file.getName(),
                file.getFilePath()
        );

        Event event = file.getEvent();

        if (event != null) {
            System.out.printf(
                    "Event: [ id: %0,10d | event type: %-25s | time stamp: -25d ]\n",
                    event.getId(),
                    event.getEventType().toString()
//                    ,event.getTimestamp().toString()
            );

            User user = event.getUser();

            if (user != null) {
                System.out.printf(
                        "User: [ id: %0,10d | name: %-25s ]\n",
                        user.getId(),
                        user.getName()
                );
            }
        }
    }
}
