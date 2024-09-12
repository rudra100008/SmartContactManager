package com.smartcontactmanager.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Config {

    // Bean for UserDetailsService implementation
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl(); // Your custom UserDetailsService implementation
    }

    // Bean for BCryptPasswordEncoder used for password hashing
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // BCryptPasswordEncoder for password encoding
    }

    // Bean for DaoAuthenticationProvider that uses UserDetailsService and PasswordEncoder
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService()); // Sets the UserDetailsService
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder()); // Sets the PasswordEncoder
        return daoAuthenticationProvider;
    }

    // Bean for configuring security filters and rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider()) // Uses the custom authentication provider
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN") // Requires ADMIN role for URLs matching /admin/**
                .requestMatchers("/user/**").hasRole("USER") // Requires USER role for URLs matching /user/**
                .requestMatchers("/**").permitAll() // Allows access to all other URLs without authentication
            )
            .formLogin(form -> form
                .loginPage("/home/login") 
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/user/viewContact/0", true)
                .failureUrl("/home/login?error")
                .permitAll() // Allows access to the login page for all users (including unauthenticated)
            )
            
            .logout(logout->logout
            .logoutUrl("/logout") //url to logout
            .logoutSuccessUrl("/home/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            )
            .csrf(csrf -> csrf.disable()); // Disables CSRF protection (useful for development; enable in production)

        return http.build(); // Builds the SecurityFilterChain
    }
}
