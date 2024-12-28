package com.ssafy12.tripchoice.auth.security;

import com.ssafy12.tripchoice.auth.exception.UnauthenticatedException;
import com.ssafy12.tripchoice.user.User;
import com.ssafy12.tripchoice.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthUserService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthUser loadUserByEmail(String email){
        return (AuthUser) loadUserByUsername(email);
    }

    public AuthUser loadUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UnauthenticatedException("User not found with id: " + id));
        return new AuthUser(user.getId(), user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthenticatedException("User not found with email: " + email));

        return new AuthUser(user.getId(), user.getEmail(), user.getPassword(), getAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return List.of(new SimpleGrantedAuthority(user.getRole().role()));
    }
}
