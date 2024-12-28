package com.ssafy12.tripchoice.user;

import com.ssafy12.tripchoice.common.security.PasswordEncoder;
import com.ssafy12.tripchoice.user.dto.SignupDto;
import com.ssafy12.tripchoice.user.dto.UserDto;
import com.ssafy12.tripchoice.user.dto.UserUpdateDto;
import com.ssafy12.tripchoice.user.exception.UserAlreadyExistsException;
import com.ssafy12.tripchoice.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ssafy12.tripchoice.auth.UserRole;

import java.util.Arrays;
import java.util.Base64;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signup(SignupDto dto) {
        userRepository.findByEmail(dto.email()).ifPresent(u -> {
            throw new UserAlreadyExistsException();
        });
        String base64String = !dto.image().isEmpty() ? dto.image().split(",")[1] : "";
        byte[] decodedBytes = !base64String.isEmpty() ? Base64.getDecoder().decode(base64String) : null;

        userRepository.save(User.builder()
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .name(dto.name())
                .image(decodedBytes)
                .build());
    }

    public UserDto getUser(Long userId) {
        return userRepository.findById(userId)
                .map(this::toDto)
                .orElseThrow(UserNotFoundException::new);
    }

    private UserDto toDto(User user) {
        String retrievedBase64 = user.getImage() != null ? "data:image/png;base64," + Base64.getEncoder().encodeToString(user.getImage()) : "";
        return UserDto.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().role())
                .image(retrievedBase64)
                .build();
    }

    @Transactional
    public void update(UserUpdateDto dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(UserNotFoundException::new);

        String base64String = !dto.image().isEmpty() ? dto.image().split(",")[1]:"";
        byte[] decodedBytes = !base64String.isEmpty() ? Base64.getDecoder().decode(base64String) : null;
        if (isNotEmpty(dto.password())) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        if(isNotEmpty(dto.name())) {
            user.setName(dto.name());
        }
        if(isNotEmpty(dto.role())) {
            user.changeRole(UserRole.roleOf(dto.role()));
        }
        if(isNotEmpty(dto.image())) {
            user.setImage(decodedBytes);
        }
    }

    @Transactional
    public void remove(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }
}
