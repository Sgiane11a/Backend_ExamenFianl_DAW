package com.tecsup.peliculas.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final Map<String, UserDetails> users;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomUserDetailsService() {
        users = new HashMap<>();
        // Usuario Admin
        users.put("admin", new User("admin", passwordEncoder.encode("12345"),
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"))));
        // Usuario User
        users.put("gianella", new User("gianella", passwordEncoder.encode("56789"),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }
}