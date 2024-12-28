package com.ssafy12.tripchoice.user;

import com.ssafy12.tripchoice.auth.resolver.AuthenticatedUserId;
import com.ssafy12.tripchoice.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static java.util.Objects.isNull;
import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Valid SignupRequestDto request) {
        userService.signup(SignupDto.builder()
                .email(request.email())
                .password(request.password())
                .name(isNull(request.name()) ? "User" + UUID.randomUUID().toString().substring(0, 5) : request.name())
                .image(request.image())
                .build());

        return ResponseEntity.status(CREATED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(@AuthenticatedUserId Long userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userId));
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> update(@AuthenticatedUserId Long userId, @RequestBody @Valid UserUpdateRequestDto request) {
        userService.update(UserUpdateDto.builder()
                .userId(userId)
                .password(request.password())
                .name(request.name())
                .role(request.role())
                .image(request.image())
                .build());
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> signout(@AuthenticatedUserId Long userId) {
        userService.remove(userId);
        return ResponseEntity
                .noContent()
                .build();
    }


}
