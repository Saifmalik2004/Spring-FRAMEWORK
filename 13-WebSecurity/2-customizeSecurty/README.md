# Spring Framework Guide: Customizing Spring Security Configuration

## 🧱 Understanding Spring's Default Security Behavior
When you add `spring-boot-starter-security` to your project, Spring automatically applies a default security setup. This includes:

### 🔐 Default Beans and Filters
- **SecurityFilterChain**: A core component that defines the filter chain used to secure your web application. It intercepts requests and enforces security rules.
- **DefaultSecurityFilterChain**: The default implementation of the `SecurityFilterChain`, registered automatically by Spring Boot.

By default:
- All endpoints are **secured**.
- Spring presents a **login page**.
- Authentication is required for every route.

This is great for getting started, but in most real-world scenarios, you need more control—for example, some pages should be public, others should be restricted.

---

## 🛠️ Why We Customize Spring Security
We don’t always want to use the default behavior because:
- Some pages (like home or contact) should be accessible by everyone.
- Others (like dashboard or admin) should require authentication.

To manage that, we **override the default filter chain** by creating a custom `SecurityFilterChain` bean.

---

## 🔓 Scenario 1: Allow All Pages to Be Accessed
This is useful during development or for public-facing pages.

### ✅ Step-by-Step: Permit All Requests
1. Create a class in the `config` folder named `ProjectSecurityConfig.java`.
2. Paste the following code:

```java
package com.learnwithsaif.project_7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // Permit All Requests inside the Web Application
        http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

### 🔍 What This Code Does:
- `authorizeHttpRequests()`: Allows you to define authorization rules.
- `anyRequest().permitAll()`: Permits access to **every endpoint**.
- `formLogin()` and `httpBasic()`: Enables default form-based login and basic authentication.

Now, your app won’t block any routes. You can access all pages without logging in.

---

## 🔒 Scenario 2: Deny All Access by Default
This is useful when your app should be fully protected, like in early production lockdown or testing.

### 🔐 Step-by-Step: Deny All Requests
Replace the configuration in `ProjectSecurityConfig.java` with:

```java
package com.learnwithsaif.project_7.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        // Deny All Requests inside the Web Application
        http.authorizeHttpRequests(requests -> requests.anyRequest().denyAll())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
```

### 🔍 What This Code Does:
- `anyRequest().denyAll()`: Blocks all access to every route, even static pages.
- This is a good baseline when you're setting up access rules from scratch or testing protection boundaries.

---

## 📁 Where to Add This Code
Place the file in:
```
src/main/java/com/learnwithsaif/project_7/config/ProjectSecurityConfig.java
```

---

## 🧪 Project Reference
This configuration is implemented in **Project 8**. You can download the source code from the link below:

🔗 [Download Project 8 Source Code](/13-WebSecurity/project_8/)

---

## ⏭️ What’s Next?
In the next scenario, we’ll learn how to restrict access to specific pages while leaving others open—laying the foundation for role-based and route-based security.

