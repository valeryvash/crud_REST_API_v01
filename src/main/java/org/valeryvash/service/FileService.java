package org.valeryvash.service;

import org.valeryvash.dto.FileDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;
public class FileService {

    private final FileRepository fileRepository;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
    }

    private FileDto convertToDto(File file) {
        FileDto fileDto = new FileDto();
        fileDto.setId(file.getId());
        fileDto.setName(file.getName());
        fileDto.setFilePath(file.getFilePath());

        fileDto.setEventId(file.getEvent().getId());
        fileDto.setEventEventType(file.getEvent().getEventType());
        fileDto.setTimestamp(file.getEvent().getTimestamp());

        fileDto.setEventUserId(file.getEvent().getUser().getId());
        fileDto.setEventUserName(file.getEvent().getUser().getName());

        return fileDto;
    }

    private File convertToEntity(FileDto fileDto) {
        /**
         * FIXME Здесь отстутствует связь юзера со списком событий
         */
        User user = new User();
        user.setId(fileDto.getEventUserId());
        user.setName(fileDto.getEventUserName());

        Event event = new Event();
        event.setId(fileDto.getEventId());
        event.setEventType(fileDto.getEventEventType());
        event.setTimestamp(fileDto.getTimestamp());

        File file = new File();
        file.setId(fileDto.getId());
        file.setName(fileDto.getName());
        file.setFilePath(fileDto.getFilePath());

        file.setEvent(event);
        event.setFile(file);
        event.setUser(user);

        return file;
    }

    public FileDto add(FileDto dtoEntity) {
        throwIfNull(dtoEntity);

        File file = convertToEntity(dtoEntity);
        file = fileRepository.add(file);

        return convertToDto(file);
    }

    public FileDto get(Long entityId) {
        throwIfNull(entityId);

        File file = fileRepository.get(entityId);

        return convertToDto(file);
    }

    public FileDto update(FileDto dtoEntity) {
        throwIfNull(dtoEntity);

        File file = convertToEntity(dtoEntity);

        file = fileRepository.update(file);

        return convertToDto(file);
    }

    public FileDto remove(Long entityId) {
        throwIfNull(entityId);

        File file = fileRepository.remove(entityId);

        return convertToDto(file);
    }

    public List<FileDto> getAll() {
        return fileRepository.getAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

}
