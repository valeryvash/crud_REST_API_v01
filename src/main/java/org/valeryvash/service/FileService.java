package org.valeryvash.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.valeryvash.dto.FileDto;
import org.valeryvash.model.Event;
import org.valeryvash.model.File;
import org.valeryvash.model.User;
import org.valeryvash.repository.FileRepository;
import org.valeryvash.repository.impl.HibernateFileRepositoryImpl;
import org.valeryvash.util.EntityConsolePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.valeryvash.util.ServiceChecker.throwIfNull;
public class FileService {
    private final ModelMapper modelMapper;

    private final FileRepository fileRepository;

    public FileService() {
        fileRepository = new HibernateFileRepositoryImpl();
        modelMapper = new ModelMapper();
    }

    private FileDto convertToDto(File file) {
        return modelMapper.map(file, FileDto.class);
    }

    private File convertToEntity(FileDto fileDto) {

        File file =
                File.builder()
                        .id(fileDto.getId())
                        .filePath(fileDto.getFilePath())
                        .name(fileDto.getName())
                        .event(
                                Event.builder()
                                        .id(fileDto.getEventId())
                                        .eventType(fileDto.getEventEventType())
//                                        .timestamp()
                                        .user(
                                                User.builder()
                                                        .id(fileDto.getEventUserId())
                                                        .name(fileDto.getEventUserName())
                                                        .events(new ArrayList<>())
                                                        .build())
                                .build())
                .build();

        Event event = file.getEvent();
        User user = event.getUser();
        user.getEvents().add(event);
        event.setFile(file);

        return file;
    }

    public FileDto add(FileDto dtoEntity) {
        throwIfNull(dtoEntity);
        System.out.println(dtoEntity);
        File file = convertToEntity(dtoEntity);
        EntityConsolePrinter.print(file);
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
