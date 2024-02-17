package com.example.jvaccommodationbookingservice.service;

import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserLoginResponseDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserRegistrationRequestDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserResponseDto;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;
import com.example.jvaccommodationbookingservice.mapper.UserMapper;
import com.example.jvaccommodationbookingservice.model.Role;
import com.example.jvaccommodationbookingservice.model.User;
import com.example.jvaccommodationbookingservice.repository.RoleRepository;
import com.example.jvaccommodationbookingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = new User();
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Role userRole = roleRepository.findRoleByName(Role.RoleName.CUSTOMER)
                .orElseThrow(() -> new RegistrationException("Can't find role by name"));
        Set<Role> defaultUserRoleSet = new HashSet<>();
        defaultUserRoleSet.add(userRole);
        user.setRoles(defaultUserRoleSet);
        User userSaved = userRepository.save(user);
        return userMapper.toUserResponse(userSaved);
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                requestDto.getPassword()));
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }
    public User getAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Can't found user with email"));
    }
}
