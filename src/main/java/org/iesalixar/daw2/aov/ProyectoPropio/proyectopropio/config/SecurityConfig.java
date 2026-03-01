package org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.config;

import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.handlers.CustomOAuth2FailureHandler;
import org.iesalixar.daw2.aov.ProyectoPropio.proyectopropio.handlers.CustomOAuth2SuccessHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final Logger logger =
            LoggerFactory.getLogger(SecurityConfig.class);

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // =========================
                // AUTORIZACIÓN
                // =========================
                .authorizeHttpRequests(auth -> auth

                        // Recursos públicos
                        .requestMatchers(
                                "/login",
                                "/error",
                                "/oauth2/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/auth/**"
                        ).permitAll()

                        // Perfil autenticado
                        .requestMatchers("/profile/**")
                        .authenticated()

                        // Advertencias → USER, MANAGER o ADMIN
                        .requestMatchers("/advertencias/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        // Zona administración → solo ADMIN
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest()
                        .authenticated()
                )

                // =========================
                // LOGIN NORMAL (BASE DE DATOS)
                // =========================
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(customOAuth2FailureHandler)
                        .permitAll()
                )

                // =========================
                // LOGIN OAUTH2 (GITHUB)
                // =========================
                .oauth2Login(oauth2 -> {
                    logger.debug("Configurando login con OAuth2");

                    oauth2
                            .loginPage("/login")
                            .successHandler(customOAuth2SuccessHandler)
                            .failureHandler(customOAuth2FailureHandler);
                })

                // =========================
                // LOGOUT
                // =========================
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    // =========================
    // PASSWORD ENCODER
    // =========================
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}