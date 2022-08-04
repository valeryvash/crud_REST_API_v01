package org.valeryvash.service;

import org.modelmapper.ModelMapper;
import org.valeryvash.dto.UserDto;
import org.valeryvash.model.User;
import org.valeryvash.repository.UserRepository;
import org.valeryvash.repository.impl.HibernateUserRepositoryImpl;

import java.util.List;

import static org.valeryvash.util.ServiceChecker.throwIfNull;
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService() {
        userRepository = new HibernateUserRepositoryImpl();
        modelMapper = new ModelMapper();
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
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

        User user = modelMapper.map(userDto, User.class);

        user = userRepository.update(user);

        return modelMapper.map(user, UserDto.class);
    }

    public UserDto remove(Long entityId) {
        throwIfNull(entityId);

        User user = userRepository.remove(entityId);

        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> getAll() {
        return userRepository.getAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList();
    }
}
