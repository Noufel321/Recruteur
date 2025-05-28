package com.example.master_app.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/recruteur/login", "/css/**", "/js/**").permitAll() // Permet l'accès à /recruteur/login sans authentification
                        .requestMatchers("/utilisateur/candidats").hasRole("RECRUTEUR")
                        .requestMatchers("/recruteur/**").hasRole("RECRUTEUR")
                        .requestMatchers("/recruteur/**").hasRole("RH")// Accès aux routes Recruteur seulement avec le rôle "RECRUTEUR"
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/recruteur/login")
                        .loginProcessingUrl("/process-login")
                        .defaultSuccessUrl("/recruteur/home", true)
                        .permitAll()
                )
                .logout()
                .and()
                .csrf().disable();
        return http.build();
    }

    // Configuration du password encoder (bcrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuration de l'AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }


}
