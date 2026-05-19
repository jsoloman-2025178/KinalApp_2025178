package com.jeffersonsoloman.kinalapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth

                        // Rutas públicas
                        .requestMatchers(
                                "/login",
                                "/css/**",
                                "/js/**",
                                "/logo.png/**",
                                "/img/**"
                        ).permitAll()

                        // USER y ADMIN
                        .requestMatchers(
                                "/productos/**",
                                "/ventas/**",
                                "/clientes/**"
                        ).hasAnyRole("USER", "ADMIN")

                        // Solo ADMIN
                        .requestMatchers(
                                "/usuarios/**",
                                "/proveedores/**",
                                "/compras/**"
                        ).hasRole("ADMIN")

                        // Cualquier otra petición requiere login
                        .anyRequest().authenticated()
                )

                // Configuración del login
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/productos", true)
                        .permitAll()
                )

                // Configuración del logout
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // Desactivar CSRF
                .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        // ADMIN
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("1234")
                .roles("ADMIN")
                .build();

        // USER
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("usuario")
                .password("1234")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}