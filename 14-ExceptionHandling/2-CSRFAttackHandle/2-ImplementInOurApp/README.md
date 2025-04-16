

# 💡 Understanding & Implementing CSRF in Spring Security

---

## 🧠 What is CSRF?

**Cross-Site Request Forgery (CSRF)** is an attack where an authenticated user's session is misused to send unwanted actions to the server — without the user’s knowledge or intent.

**Example:**  
If you are logged into your bank account and a malicious website triggers a form submit or request, the action could get processed as if you performed it yourself — since your session is still active.

---

## ⚙️ Spring Security and CSRF — Default Behavior

Spring Security enables `CSRF` protection by default for:

| HTTP Method | CSRF Protection |
|-------------|-----------------|
| `GET`       | ❌ Not Required |
| `POST`      | ✅ Required |
| `PUT`       | ✅ Required |
| `DELETE`    | ✅ Required |

**GET requests are considered safe** since they don't modify data, but state-changing requests like `POST`, `PUT`, and `DELETE` require a valid CSRF token.

---

## 🚫 Why Did We Disable CSRF Initially?

In our project:

- Most of the requests were `GET`.
- Only one `POST` endpoint was used — `/saveMsg` (which was public).

So for convenience during early development, CSRF was globally disabled like this:

```java
http.csrf((csrf) -> csrf.disable())
```

---

## 💡 A Better Solution: Selectively Ignoring CSRF

Disabling CSRF globally is **not recommended**.  
A better and secure approach is to disable CSRF **only** for specific public endpoints.

```java
http.csrf(csrf -> csrf.ignoringRequestMatchers("/saveMsg"))
```

### 🔥 Explanation:

- `ignoringRequestMatchers("/saveMsg")`: CSRF will be ignored only for `/saveMsg`.
- All other routes will remain protected.
- This is ideal when a public form submits data without a logged-in session context.

---

## 🔐 Custom Logout Handling

When CSRF is enabled, Spring Security only allows `POST /logout`.  
If you trigger logout via `GET`, you will encounter a `403 Forbidden` error.

To handle this cleanly, we implemented a **custom logout controller**:

```java
@RequestMapping(value="/logout", method = RequestMethod.GET)
public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null){
        new SecurityContextLogoutHandler().logout(request, response, auth);
    }
    return "redirect:/login?logout=true";
}
```

### 👉 What this does:

- Fetches the currently authenticated user.
- Clears the session and authentication context.
- Redirects the user back to the login page with a logout confirmation.

---

## ⚙️ Final Security Configuration — After Changes

```java
@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/saveMsg"))  // CSRF disabled only for saveMsg
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
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .failureUrl("/login?error=true")
                .permitAll()
            )
            .logout(logoutConfigurer -> logoutConfigurer
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
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
```

---

## 💡 How to Handle CSRF in Forms

Once CSRF protection is enabled (default), all `POST`, `PUT`, and `DELETE` forms must include a valid CSRF token.

In Thymeleaf templates, Spring provides this automatically:

```html
<input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
```

### Example — Login Form:

```html
<form th:action="@{/login}" method="post" class="signin-form">
    <div class="col-md-8 login-center input-grids">
        <li class="alert alert-danger" role="alert" th:if="${!#strings.isEmpty(errorMessge)}"
            th:text="${errorMessge}" />
        <input type="text" name="username" id="username" placeholder="Username" class="login-input" />
        <input type="password" name="password" id="password" placeholder="Password" class="login-input" />
        <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
    </div>
    <div class="col-md-8 login-center text-start">
        <button class="btn btn-style btn-style-3 text-left">Log In</button>
        <a class="new-user text-right" href="">New User ?</a>
    </div>
</form>
```

✅ **Note:**  
If the CSRF token is missing or invalid, Spring will block the request with a `403 Forbidden` error.

---

## ✅ Final Summary:

| Scenario                          | Solution                                     |
|-----------------------------------|----------------------------------------------|
| Globally disabled CSRF            | ❌ Avoid it — not secure.                     |
| Public `POST` without CSRF token  | ✔️ Use `csrf.ignoringRequestMatchers("/saveMsg")` |
| Logout via `GET` request issue    | ✔️ Handled using a custom `logout` controller. |
| CSRF token in HTML forms          | ✔️ Add hidden CSRF field via Thymeleaf.       |

---

This way:

- Your application stays secure.
- Logout issues are handled properly.
- CSRF protection is active for sensitive operations.
- Public forms can bypass CSRF when needed — safely.

💾 **You can grab the full working code from Project 9:**  
👉 [View / Download Project 10](/14-ExceptionHandling/project_10/)*

---
