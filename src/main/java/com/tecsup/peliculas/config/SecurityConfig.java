package com.tecsup.peliculas.config;

import com.tecsup.peliculas.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. ACTIVAR LA CONFIGURACIÓN DE CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 2. Deshabilitar CSRF (común para APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // 3. Configurar la gestión de sesión como STATELESS (sin estado)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 4. Definir las reglas de autorización para las rutas
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll() // Permitir a todos acceder al login
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Permitir acceso a la documentación
                        .requestMatchers(HttpMethod.GET, "/api/peliculas/**", "/api/generos/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/peliculas", "/api/generos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/peliculas/**", "/api/generos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/peliculas/**", "/api/generos/**").hasRole("ADMIN")
                        .anyRequest().authenticated() // Cualquier otra petición requiere autenticación
                );

        // 5. Añadir el filtro de JWT antes del filtro de autenticación estándar
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Bean que define la configuración de CORS para toda la aplicación.
     * Esto es lo que permite que tu frontend (ej. en localhost:5173)
     * pueda hacer peticiones a tu backend (en localhost:8080).
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Orígenes permitidos (la URL de tu frontend)
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));
        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        // Cabeceras permitidas
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
        // Permitir credenciales (cookies, etc.)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Aplicar esta configuración a todas las rutas de la API
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}