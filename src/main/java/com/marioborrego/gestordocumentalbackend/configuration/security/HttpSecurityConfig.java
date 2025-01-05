package com.marioborrego.gestordocumentalbackend.configuration.security;

import com.marioborrego.gestordocumentalbackend.configuration.security.filter.JwtAuthenticationFilter;
import com.marioborrego.gestordocumentalbackend.domain.models.enums.TipoRol;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;


    public HttpSecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(ssmg -> ssmg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authz -> authz.requestMatchers("/auth/authenticate").permitAll()
                        .requestMatchers("/auth/validate-token", "/api/proyectos/listarproyectos", "/api/ncs/responder", "/api/ncs/proyecto/**").authenticated()
                        .requestMatchers("/api/proyectos/proyecto/").authenticated()
                        .requestMatchers("/api/ncs/cerrarPuntoNc/**", "/api/ncs/crearPuntoNoConformidad/").hasRole(TipoRol.EMPLEADO.toString())
                        .requestMatchers(HttpMethod.POST,"/api/ncs/crearNoConformidad/").hasRole(TipoRol.EMPLEADO.name())
                        .requestMatchers("/api/usuarios/**", "/api/roles/**").hasRole(TipoRol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.POST, "/api/proyectos/").hasRole(TipoRol.ADMINISTRADOR.toString())
                        .requestMatchers(HttpMethod.PUT, "/api/proyectos/actualizarProyecto/").hasRole(TipoRol.ADMINISTRADOR.toString())
                        .anyRequest().authenticated()).build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
