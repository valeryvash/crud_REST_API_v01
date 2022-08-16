package org.valeryvash.service;

import org.valeryvash.dto.UserDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.EventRepository;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.UserRepository;
import org.valeryvash.repository.impl.HibernateEventRepositoryImpl;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;
public class UserService {
    private final UserRepository userRepository;

    private final EventRepository eventRepository;

    private final FileRepository fileRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
        eventRepository = new HibernateEventRepositoryImpl();
        fileRepository = new HibernateFileRepositoryImpl();
    }

    private UserDto convertToDto(User user) {
        List<Event> events = user.getEvents();

        List<UserDto.EventDto> eventDtos = events
                .stream()
                .map(event -> {
                    UserDto.EventDto userEventDto = new UserDto.EventDto();
                    userEventDto.setId(event.getId());
                    userEventDto.setTimestamp(event.getTimestamp());
                    userEventDto.setEventType(event.getEventType());

                    userEventDto.setFileId(event.getFile().getId());
                    userEventDto.setFileName(event.getFile().getName());
                    userEventDto.setFileFilePath(event.getFile().getFilePath());

                    return userEventDto;
                }).toList();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEvents(eventDtos);

        return userDto;
    }

    private User convertToEntity(UserDto userDto) {
        boolean userIsNew = userDto.getId() == 0L;
        User user = null;

        if (userIsNew) {
            user = new User();
        } else {
            user = userRepository.get(userDto.getId());
        }

        user.setName(userDto.getName());

        List<UserDto.EventDto> eventDtos = userDto.getEvents();

        List<Event> events = eventDtos
                .stream()
                .map(eventDto -> {
                    boolean eventIsNew = eventDto.getId() == 0L;
                    boolean fileIsNew = eventDto.getFileId() == 0L;

                    Event event = null;
                    if (eventIsNew) {
                        event = new Event();
                    } else {
                        event = eventRepository.get(eventDto.getId());
                    }
                    event.setEventType(eventDto.getEventType());
                    event.setTimestamp(eventDto.getTimestamp());

                    File file = null;
                    if (fileIsNew) {
                        file = new File();
                    } else {
                        file = fileRepository.get(eventDto.getFileId());
                    }
                    file.setName(eventDto.getFileName());
                    file.setFilePath(eventDto.getFileFilePath());

                    file.setEvent(event);
                    event.setFile(file);

                    return event;
                })
                .toList();

        user.setEvents(events);

        return user;
    }

    public UserDto add(UserDto userDto) {
        throwIfNull(userDto);

        User user = convertToEntity(userDto);

        user = userRepository.add(user);

        return convertToDto(user);
    }

    public UserDto get(Long entityId) {
        throwIfNull(entityId);

        User user = userRepository.get(entityId);

        return convertToDto(user);
    }

    public UserDto update(UserDto userDto) {
        throwIfNull(userDto);

        User user = convertToEntity(userDto);

        user = userRepository.update(user);

        return convertToDto(user);
    }

    public UserDto remove(Long entityId) {
        throwIfNull(entityId);

        User user = userRepository.remove(entityId);

        return convertToDto(user);
    }

    public List<UserDto> getAll() {
        return userRepository.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }
}
