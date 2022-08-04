package org.valeryvash.service;

import org.valeryvash.model.Event;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;

//todo add entity id checks
public class EventService {

    private final EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    public Event add(Event entity) {
        throwIfNull(entity);

        return eventRepository.add(entity) ;
    }

    public Event get(Long entityId) {
        throwIfNull(entityId);

        return eventRepository.get(entityId);
    }

    public Event update(Event entity) {
        throwIfNull(entity);

        return eventRepository.update(entity);
    }

    public Event remove(Long entityId) {
        throwIfNull(entityId);

        return eventRepository.remove(entityId);
    }

    public List<Event> getAll() {
        return eventRepository.getAll();
    }
}
