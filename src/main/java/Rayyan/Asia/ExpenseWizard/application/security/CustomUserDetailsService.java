package Rayyan.Asia.ExpenseWizard.application.security;

import Rayyan.Asia.ExpenseWizard.application.dto.models.user.CustomUserDetails;
import Rayyan.Asia.ExpenseWizard.domain.interfaces.UserRepository;
import Rayyan.Asia.ExpenseWizard.domain.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return new CustomUserDetails(user.getId(), user.getPassword());
    }
}
