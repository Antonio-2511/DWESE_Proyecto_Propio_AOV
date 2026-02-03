package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // Activa @PreAuthorize
public class SecurityConfig {

    // ======================================================
    // FILTRO PRINCIPAL DE SEGURIDAD
    // ======================================================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // ----------------------------
                // AUTORIZACIÓN DE PETICIONES
                // ----------------------------
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/error",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()
                        .requestMatchers("/advertencias/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // ----------------------------
                // LOGIN PERSONALIZADO
                // ----------------------------
                .formLogin(form -> form
                        .loginPage("/login")          // Página de login propia
                        .defaultSuccessUrl("/", true) // Redirección tras login
                        .permitAll()
                )

                // ----------------------------
                // LOGOUT
                // ----------------------------
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    // ======================================================
    // ENCODER DE CONTRASEÑAS (BCrypt)
    // ======================================================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
