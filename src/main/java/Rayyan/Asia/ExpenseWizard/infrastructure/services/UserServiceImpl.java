package Rayyan.Asia.ExpenseWizard.infrastructure.services;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserDto;
import Rayyan.Asia.ExpenseWizard.application.dto.models.user.UserUpsertDto;
import Rayyan.Asia.ExpenseWizard.application.mappers.UserMapper;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserRepository;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    @Override
    @Transactional
    public Optional<UserDto> getByEmail(String email) {
        var user = userRepository.findByEmail(email);
        return user.map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public Optional<UserDto> getById(String id) {
        var user = userRepository.findById(id);
        return user.map(mapper::domainToDto);
    }

    @Override
    @Transactional
    public UserDto save(UserUpsertDto registerDto) {
        var user = mapper.dtoToDomain(registerDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return mapper.domainToDto(user);
    }
}
