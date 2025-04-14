Here's your next structured README covering **custom Spring Security configuration** using `requestMatchers`, along with a beginner-friendly explanation of why it's necessary, how it works, and an introduction to CSRF protection.

---

# 🔐 Custom Spring Security Configuration – Part 2

In this guide, we move beyond permitting all requests and set up **custom security rules** using `requestMatchers`. This gives us more control by allowing only specific pages to be public, while preparing others to be secured later.

---

## 🧠 Why Move to Custom Configuration?

Previously, we used:

```java
http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
```

That made **every page accessible** to anyone, which is okay temporarily—but not secure in the long run.

Now, we want to:
- Allow public access to selected pages (like home, contact, etc.)
- Keep other pages ready for restriction (we’ll secure them with `authenticated()` later)

This is where **custom request matchers** come in.

---

## 🧪 Code: Custom Security with `requestMatchers`

```java
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
```

---

## 🧾 Breakdown of the Configuration

### 🔹 `requestMatchers(...)`
Used to define which routes are affected by a specific security rule.

- `"/"` and `"/home"`: Homepage
- `"/holidays/**"`: All URLs starting with `/holidays/`, e.g. `/holidays/winter`, `/holidays/summer`
- `"/assets/**"`: Grants access to static assets like CSS, JS, images. If not allowed, your frontend might break (no styles, scripts, etc.)
- `"/contact"`, `"/about"`, `"/saveMsg"`, etc.: Typical public-facing pages

### 🔹 `/**` Meaning
The `**` wildcard means: "any path that comes after".  
So `/holidays/**` matches:
- `/holidays`
- `/holidays/winter`
- `/holidays/summer/special`

If you leave it out, only the exact path `/holidays` would be accessible.

---

## ⚠️ Why Permit Static Assets?

Without permitting `/assets/**`, your CSS, JavaScript, and images won’t load properly, and the UI will appear broken. Static resources **must be explicitly permitted** for public access.

---

## 🧯 CSRF (Cross-Site Request Forgery) – Basic Intro

Spring Security enables **CSRF protection by default** to prevent attackers from submitting malicious POST requests on behalf of logged-in users.

In our case, we have a public **contact form** (`/saveMsg`) that uses a `POST` request.

### ✅ When you use Thymeleaf (Spring form)
Thymeleaf auto-inserts a hidden CSRF token, so everything works.

### ❌ When you use plain HTML forms (`<form action="/saveMsg" method="post">`)
You get a **403 Forbidden** error unless you manually include the CSRF token.

---

## 🛑 Temporarily Disabling CSRF

To avoid issues during learning and development, we’re disabling CSRF for now:

```java
http.csrf(csrf -> csrf.disable());
```

> **Note:** This is fine for now, but not safe for production.  
We’ll handle CSRF properly in a later chapter.

---

## 📌 Summary

- We're now using `requestMatchers()` to **customize access per route**.
- All specified routes are **public**, the rest are prepared for future restrictions.
- `/**` lets you cover full URL patterns.
- Static files (`/assets/**`) need to be explicitly permitted.
- CSRF protection is **disabled for now** so forms work without tokens.

---

## 📁 Where to Place the Code

Create or update the following file:

```
src/main/java/com/yourapp/config/ProjectSecurityConfig.java
```

Adjust package name as per your project.

---


