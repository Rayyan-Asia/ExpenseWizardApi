package Rayyan.Asia.ExpenseWizard.application.security;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.Role;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Username Not Found"));
        return new CustomUserDetails(user.getEmail(), user.getPassword());
    }
}
