package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.UserMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;
    }

    @Override
    public Optional<UserDto> getByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user.map(mapper::domainToDto);
    }

    @Override
    public Optional<UserDto> getById(String id) {
        var user = userRepository.findById(id);
        return user.map(mapper::domainToDto);
    }

    @Override
    public UserEntity save(UserDto registerDto) {
        var user = mapper.dtoToDomain(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
