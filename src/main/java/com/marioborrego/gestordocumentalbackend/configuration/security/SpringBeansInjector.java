package com.marioborrego.gestordocumentalbackend.configuration.security;

import com.marioborrego.gestordocumentalbackend.domain.repositories.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringBeansInjector {
    private final UsuarioRepository userRepository;

    public SpringBeansInjector(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationStrategy = new DaoAuthenticationProvider();
        daoAuthenticationStrategy.setPasswordEncoder(this.passwordEncoder());
        daoAuthenticationStrategy.setUserDetailsService(this.userDetailsService());
        return daoAuthenticationStrategy;
    }
    @Bean
    public UserDetailsService userDetailsService() {
        return userRepository::findByNombre;
    }
}
