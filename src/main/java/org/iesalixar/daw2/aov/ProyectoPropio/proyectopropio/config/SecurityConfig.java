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

    @Autowired
    private CustomOAuth2SuccessHandler customOAuth2SuccessHandler;


    @Autowired
    private CustomOAuth2FailureHandler customOAuth2FailureHandler;

    private static final Logger logger =
            LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/login",
                                "/error",
                                "/oauth2/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/auth/**"
                        ).permitAll()

                        .requestMatchers("/profile/**")
                        .authenticated()

                        .requestMatchers("/advertencias/**")
                        .hasAnyRole("USER", "MANAGER", "ADMIN")

                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        .anyRequest()
                        .authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customOAuth2SuccessHandler) // Usa el Success Handler personalizado
                        .failureHandler(customOAuth2FailureHandler) // Handler para fallo en autenticación
                )

                .oauth2Login(oauth2 -> {
                    logger.debug("Configurando login con OAuth2");
                    oauth2
                            .loginPage("/login")          // reutiliza tu login
                            .defaultSuccessUrl("/", true) // redirige al inicio
                            .permitAll();
                })

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
