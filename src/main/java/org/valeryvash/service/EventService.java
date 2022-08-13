package org.valeryvash.service;

import org.valeryvash.dto.EventDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;

/**
 *  Сервис принимает на вход дто объект, конвертирует его в энтити,
 *  совершает требуемый тип операции с репозиторием и возвращает
 *  преобразованный вновь дто объект
 */
public class EventService {

    private final EventRepository eventRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
    }

    private EventDto convertToDto(Event event) {
        EventDto eventDto = new EventDto(
                event.getId(),
                event.getFile().getId(),
                event.getFile().getName(),
                event.getFile().getFilePath(),
                event.getTimestamp(),
                event.getEventType(),
                event.getUser().getId(),
                event.getUser().getName()
        );

        return eventDto;
    }

    private Event convertToEntity(EventDto eventDto) {
/**
 *        FIXME Отсутствует фиксация эвента на список юзера
 *        т.к. не известно сколько эвентов у данного юзера
 *        возможные решения : уменьшить уровень вложенности
 *        или начать доставать юзеров и файлы прямо в данном методе и связывать их
 *
 */

        User eventUser = new User();
        eventUser.setId(eventDto.getId());
        eventUser.setName(eventDto.getFileName());

        File file = new File();
        file.setId(eventDto.getFileId());
        file.setName(eventDto.getFileName());
        file.setFilePath(eventDto.getFileFilePath());

        Event event = new Event(
                eventDto.getId(),
                file,
                eventDto.getTimestamp(),
                eventDto.getEventType(),
                eventUser
        );

        return event;
    }


    public EventDto add(EventDto dtoEntity) {
        throwIfNull(dtoEntity);

        Event event = convertToEntity(dtoEntity);

        event = eventRepository.add(event);

        return convertToDto(event);
    }

    public EventDto get(Long entityId) {
        throwIfNull(entityId);

        return convertToDto(eventRepository.get(entityId));
    }

    public EventDto update(EventDto entityDto) {
        throwIfNull(entityDto);

        Event event = convertToEntity(entityDto);

        event = eventRepository.update(event);

        return convertToDto(event);
    }

    public EventDto remove(Long entityId) {
        throwIfNull(entityId);

        return convertToDto(eventRepository.remove(entityId));
    }

    public List<EventDto> getAll() {
        return eventRepository.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }
}
