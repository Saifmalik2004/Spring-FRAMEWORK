# Spring Framework Guide: Introduction to Spring Security

## 🔐 What is Spring Security?
Spring Security is a powerful and highly customizable authentication and access-control framework. It is the standard for securing Spring-based applications. With just a simple configuration, it helps protect your application from unauthorized access, making it one of the most essential components in a production-ready system.

Spring Security is not just about login forms. It provides robust protection against common vulnerabilities, such as:
- **Cross-Site Scripting (XSS)**
- **Cross-Site Request Forgery (CSRF)**
- **Session Fixation**
- **Clickjacking**
- **Man-in-the-middle (MITM) attacks** (when combined with HTTPS)

It also integrates smoothly with OAuth2, JWT, and LDAP, making it versatile for enterprise-grade security solutions.

### 🚨 Why Security Matters
By default, in most web applications, anyone can access any page unless explicitly restricted. This is risky—imagine a dashboard or admin panel being exposed to everyone. Spring Security fixes that with minimal setup, preventing unauthorized access and enforcing user roles and permissions.

Even a basic app handles sensitive user data, which must be protected. Without proper security:
- Malicious users can manipulate URLs to access protected pages.
- User sessions can be hijacked.
- Sensitive endpoints (like `/admin`) can be misused.

Spring Security helps you lock these down quickly and safely.

---

## 🧠 Core Concepts

### 🔑 Authentication
Authentication is about verifying **who you are**. When you log in with a username and password, the system checks if your credentials are correct. If they are, you’re authenticated.

- **Example:** You enter your email and password into a login form.
- **Spring Security Role:** It checks these credentials, typically against a user store (database, in-memory, etc.).
- **Output:** A valid session or token that confirms your identity.

### 🛡️ Authorization
Authorization is about determining **what you’re allowed to do**. Once you’re authenticated, the system decides what resources you can access.

- **Example:** You may be allowed to view your profile but not edit other users’ data.
- **Spring Security Role:** It ensures users can only access routes/resources permitted by their roles.
- **Output:** Access granted or denied.

Both processes work together. Authentication answers “Who are you?” and authorization answers “What can you do?”

---

## ⚙️ Let's Get Started: First Step Toward Securing the App
Without writing a single line of security configuration, Spring Security already protects your application. Here's how:

### ✅ Step 1: Add Spring Security Dependency
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### 🎉 What Happens Now?
As soon as you add this dependency and run your Spring Boot application, Spring Security kicks in.

- Every route is now protected.
- If you try to access any page, you’ll be redirected to a login screen.
- The default username is `user`.
- The password is auto-generated and printed in the console log like this:
  ```
  Using generated security password: 23f1c3d1-b5f7-4fbb-a1a1-abcdef123456
  ```

Use this password to log in.

Spring Security auto-generates this password to help you get started quickly and safely without needing custom login logic right away.

### 🧪 Try it Yourself
1. Run your Spring Boot app.
2. Open `http://localhost:8080` in your browser.
3. You’ll see a login form.
4. Enter:
   - **Username:** `user`
   - **Password:** *(copy from console)*

You’re now in. Just by adding a dependency, you’ve enabled basic security for the entire app.

---
## 🔧 How to Set a Custom Username and Password
You can easily replace the default login credentials by adding the following to your `application.properties` file:

```properties
spring.security.user.name=admin
spring.security.user.password=secret123
```

### 📌 How It Works:
- `spring.security.user.name`: Sets the username you want (e.g., `admin`).
- `spring.security.user.password`: Sets the plain text password (e.g., `secret123`).

Once you restart the application, you can log in using:
- **Username:** `admin`
- **Password:** `secret123`

### 🔒 Important Note:
For real-world apps, never store plain text passwords. This setup is just for learning and development. We’ll cover encrypted password storage and user roles in future chapters.

---
## 📌 What’s Next?
In the next chapter, we’ll learn how to:
- Customize the username and password.
- Create user roles.
- Define access rules for specific pages and routes.
- Build a login form that fits your app.

Stay tuned. We’ve just scratched the surface of securing Spring applications.

