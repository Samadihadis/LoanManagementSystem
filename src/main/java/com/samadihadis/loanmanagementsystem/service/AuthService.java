package com.samadihadis.loanmanagementsystem.service;

import com.samadihadis.loanmanagementsystem.dto.RegisterRequest;
import com.samadihadis.loanmanagementsystem.entity.User;
import com.samadihadis.loanmanagementsystem.enums.Role;
import com.samadihadis.loanmanagementsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setRole(Role.USER);
        userRepository.save(user);
    }
}
