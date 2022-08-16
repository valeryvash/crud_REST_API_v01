package org.valeryvash.service;

import org.valeryvash.dto.EventDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.EventType;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.UserRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;

/**
 *  Сервис принимает на вход дто объект, конвертирует его в энтити,
 *  совершает требуемый тип операции с репозиторием и возвращает
 *  преобразованный вновь дто объект
 */
public class EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    public EventService() {
        eventRepository = new HibernateEventRepositoryImpl();
        this.userRepository = new HibernateUserRepositoryImpl();
        this.fileRepository = new HibernateFileRepositoryImpl();
    }

    private EventDto convertToDto(Event event) {
        EventDto eventDto = new EventDto(
                event.getId(),
                event.getTimestamp(),
                event.getEventType(),
                event.getFile().getId(),
                event.getFile().getName(),
                event.getFile().getFilePath(),
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
 *        в противном случае есть щанс получить эксепшн на этапе апдейта
 *        Решения:
 *        1. Доставать и пришивать пользователя из базы в даннном методе (что не есть хорошо с точки зрения Solid - этот код отвечает за конвертацию)
 *        2. Доставать пользователя и пришивать в каждом методе.
 *        3. Сделать отдельный метод для получения и пришивания пользователя. Вызываем когда нам это необходимо.
 *        Примечание. Коллекция пользователя должна быть ленивой. Это уменьшит количество обращений к базе.
 */
        boolean fileIsNew = eventDto.getFileId() == 0L;
        boolean userIsNew = eventDto.getUserId() == 0L;
        boolean eventIsNew = eventDto.getId() == 0L;

        File file = null;
        if (fileIsNew) {
            file = new File();
        } else {
            file = fileRepository.get(eventDto.getFileId());
        }
        file.setName(eventDto.getFileName());
        file.setFilePath(eventDto.getFileFilePath());

        User user = null;
        if (userIsNew) {
            user = new User();
            user.setEvents(new ArrayList<>());
        } else {
            user = userRepository.get(eventDto.getUserId());
        }
        user.setName(eventDto.getUserName());

        Event event = null;
        if (eventIsNew) {
            event = new Event();
        } else {
            event = eventRepository.get(eventDto.getId());
        }
        event.setEventType(eventDto.getEventType());
        event.setTimestamp(eventDto.getTimestamp());
        event.setFile(file);
        event.setUser(user);

        file.setEvent(event);
        user.getEvents().add(event);

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
