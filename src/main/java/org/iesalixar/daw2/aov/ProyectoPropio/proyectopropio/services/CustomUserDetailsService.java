package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.services;

import java.util.stream.Collectors;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.Role;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.entities.User;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log =
            LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.debug("Intentando autenticar usuario con email={}", username);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> {
                    log.warn("No se encontró usuario con email={}", username);
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        UserDetails userDetails =
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getEmail())
                        .password(user.getPasswordHash())
                        .authorities(
                                user.getRoles().stream()
                                        .map(Role::getName) // ROLE_USER, ROLE_ADMIN…
                                        .collect(Collectors.toList())
                                        .toArray(new String[0])
                        )
                        .accountLocked(!user.isAccountNonLocked())
                        .disabled(!user.isActive())
                        .credentialsExpired(false)
                        .accountExpired(false)
                        .build();

        log.debug(
                "Usuario autenticado correctamente: {} con roles {}",
                user.getEmail(),
                userDetails.getAuthorities()
        );

        return userDetails;
    }
}
