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

        return fileDto;
    }

    private File convertToEntity(FileDto fileDto) {
        /**
         * FIXME Здесь отстутствует связь юзера со списком событий
         */
        File file = new File();
        file.setId(fileDto.getId());
        file.setName(fileDto.getName());
        file.setFilePath(fileDto.getFilePath());

        return file;
    }

    public FileDto get(Long entityId) {
        throwIfNull(entityId);

        File file = fileRepository.get(entityId);

        return convertToDto(file);
    }

    public FileDto update(FileDto dtoEntity) {
        throwIfNull(dtoEntity);

        File file = convertToEntity(dtoEntity);

        File toBeUpdatedFile = fileRepository.get(file.getId());
        toBeUpdatedFile.setName(file.getName());
        toBeUpdatedFile.setFilePath(file.getFilePath());

        toBeUpdatedFile = fileRepository.update(toBeUpdatedFile);

        return convertToDto(toBeUpdatedFile);
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
