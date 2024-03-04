package com.example.jvaccommodationbookingservice.service.userservice;

import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateProfileInformationDto;
import com.example.jvaccommodationbookingservice.dto.userDto.UserUpdateRoleDto;
import com.example.jvaccommodationbookingservice.dto.userDto.*;
import com.example.jvaccommodationbookingservice.exception.DataProcessingException;
import com.example.jvaccommodationbookingservice.exception.EntityNotFoundException;
import com.example.jvaccommodationbookingservice.exception.RegistrationException;
import com.example.jvaccommodationbookingservice.mapper.UserMapper;
import com.example.jvaccommodationbookingservice.model.User;
import com.example.jvaccommodationbookingservice.repository.UserRepository;
import com.example.jvaccommodationbookingservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RegistrationException("Can't complete registration");
        }
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        final Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(requestDto.getEmail(),
                requestDto.getPassword()));
        String token = jwtUtil.generateToken(authentication.getName());
        return new UserLoginResponseDto(token);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by id: " + id));
    }

    @Override
    public User getAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new DataProcessingException("Unable to find authenticated user");
        }
        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("Can't find user by user email: "
                        + authentication.getName()));
    }

    @Override
    public boolean existsById(final Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by email: " + email)
        );
    }

    @Override
    public void updateUserRole(final Long id, final UserUpdateRoleDto updateRoleDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id: " + id)
        );
        final User.Role role = checkValidUserRole(updateRoleDto);
        user.setRole(role);
        userRepository.save(user);
    }
    @Override
    public UserResponseDto getUserProfile() {
        User user = getAuthenticated();
        return userMapper.toDto(user);
    }


    @Override
    public UserResponseDto updateUserProfile(final UserUpdateProfileInformationDto request) {
        User user = getAuthenticated();

        if (request.email() != null && !request.email().isEmpty()
                && !request.email().equals(user.getEmail())) {
            user.setEmail(request.email());
        }

        if (request.password() != null && !request.password().isEmpty()
                && !request.password().equals(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(request.password()));
        }

        if (request.firstName() != null && !request.firstName().isEmpty()
                && !request.firstName().equals(user.getFirstName())) {
            user.setFirstName(request.firstName());
        }

        if (request.lastName() != null && !request.lastName().isEmpty()
                && !request.lastName().equals(user.getLastName())) {
            user.setLastName(request.lastName());
        }

        return userMapper.toDto(userRepository.save(user));
    }

    private User.Role checkValidUserRole(UserUpdateRoleDto updateRoleDto) {
        User.Role role = null;
        String requestRole = updateRoleDto.role().trim().toUpperCase();
        StringBuilder roleMessage = new StringBuilder();

        for (User.Role value : User.Role.values()) {
            roleMessage.append(",").append(" ").append(value.name());

            if (value.name().equals(requestRole)) {
                role = value;
            }
        }

        if (role == null) {
            throw new DataProcessingException("Incorrect role name entered, "
                    + "please enter correct data" + roleMessage.toString());
        }
        return role;
    }
}
