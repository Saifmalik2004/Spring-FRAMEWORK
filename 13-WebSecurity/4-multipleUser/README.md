# 👥 In-Memory User Setup with Spring Security (Using BCrypt)

## 📚 What Are We Doing Here?

In this chapter, we’re creating **multiple users** in memory without a database. This is useful when:
- You're just learning Spring Security.
- You want to test role-based access.
- You don’t have a database set up yet.

We’re also using **BCryptPasswordEncoder**, a real password encryption method, instead of default encoders (which are not secure).

---

## 💡 Why This Matters

Real apps need secure passwords. Even though we’re not using a database here, we’re still using proper password hashing (via `BCrypt`) to simulate production-like behavior.

This is **not for production**, but it’s the best way to simulate real-world authentication while learning.

---

## 🧪 The Code

```java
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
            .requestMatchers("/", "/home").permitAll()
            .requestMatchers("/holidays/**").permitAll()
            .requestMatchers("/contact").permitAll()
            .requestMatchers("/saveMsg").permitAll()
            .requestMatchers("/courses").permitAll()
            .requestMatchers("/about").permitAll()
            .requestMatchers("/assets/**").permitAll()
        )
        .formLogin(Customizer.withDefaults())
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
```

---

## 🔍 Code Breakdown

### 🔐 `InMemoryUserDetailsManager`
- Used to define multiple users inside your code.
- Holds all user info in memory.
- No need for a database.

### 👤 `User.builder()`
Used to define:
- `username` – login name.
- `password` – stored using encrypted format (BCrypt).
- `roles()` – assign roles like `USER` or `ADMIN`.

### 🔑 `passwordEncoder.encode("12345")`
We don’t store plain text passwords. `BCryptPasswordEncoder` hashes the password before storing it.

### 🔒 `BCryptPasswordEncoder`
A secure password hashing function. Even if someone sees the database (or memory), the passwords won’t be readable.

---

## 👎 Why Not Use This in Production?

- Data resets every time the server restarts.
- No database = no real persistence.
- Managing many users manually in code is painful.
- In-memory users can’t be edited dynamically.

> Use this **only for development, demos, or quick testing.**

---

## 🧪 How to Test

1. Run the app.
2. Open your browser and go to any protected page (later we’ll restrict some).
3. Spring will show the login form.
4. Try logging in with:
   - Username: `user` | Password: `12345`
   - Username: `admin` | Password: `54321`

You’ll be logged in as the respective user and will later get access based on their roles.

---

## 🗂 Where to Place This Code

File path:
```
src/main/java/com/learnwithsaif/project_9/config/ProjectSecurityConfig.java
```

---

## ✅ Summary

- You can define multiple users in memory using `InMemoryUserDetailsManager`.
- Passwords should be stored using a secure encoder like `BCrypt`.
- This setup is great for learning but **should not be used in production**.
- We’ll build on this to restrict routes based on roles in the next section.

---

