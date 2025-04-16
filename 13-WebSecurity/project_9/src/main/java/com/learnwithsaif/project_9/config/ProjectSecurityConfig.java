package com.learnwithsaif.project_9.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()) // CSRF protection disabled for now
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/courses").permitAll()
                .requestMatchers("/about").permitAll()
                .requestMatchers("/assets/**").permitAll()
            )
            .formLogin(loginConfigurer -> loginConfigurer
            .loginPage("/login")                    // our custom login page
            .defaultSuccessUrl("/dashboard")       // where to go after successful login
            .failureUrl("/login?error=true")       // where to go on failed login
            .permitAll()
        )
        .logout(logoutConfigurer -> logoutConfigurer
            .logoutSuccessUrl("/login?logout=true")    // redirect after logout
            .invalidateHttpSession(true)              // clears session data
            .permitAll()
        )
            .httpBasic(Customizer.withDefaults());
    
        return http.build();
    }
   
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("12345"))
                .roles("USER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("54321"))
                .roles("USER", "ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}