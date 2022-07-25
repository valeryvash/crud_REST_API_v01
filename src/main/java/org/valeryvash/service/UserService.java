package org.valeryvash.service;

import org.valeryvash.model.User;
import org.valeryvash.repository.UserRepository;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;

// todo entity id checks
public class UserService {

    private UserRepository userRepository;

    private UserService() {}

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(User entity) {
        throwIfNull(entity);

        return userRepository.add(entity);
    }

    public User get(Long entityId) {
        throwIfNull(entityId);

        return userRepository.get(entityId);
    }

    public User update(User entity) {
        throwIfNull(entity);

        return userRepository.update(entity);
    }

    public User remove(Long entityId) {
        throwIfNull(entityId);

        return userRepository.remove(entityId);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }
}
