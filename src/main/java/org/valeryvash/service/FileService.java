package org.valeryvash.service;

import org.valeryvash.model.File;
import org.valeryvash.repository.FileRepository;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;

//todo entity id checks
public class FileService {

    private FileRepository fileRepository;

    private FileService() {
    }

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public File add(File entity) {
        throwIfNull(entity);

        return fileRepository.add(entity);
    }

    public File get(Long entityId) {
        throwIfNull(entityId);

        return fileRepository.get(entityId);
    }

    public File update(File entity) {
        throwIfNull(entity);

        return fileRepository.update(entity);
    }

    public File remove(Long entityId) {
        throwIfNull(entityId);

        return fileRepository.remove(entityId);
    }

    public List<File> getAll() {
        return fileRepository.getAll();
    }
}
