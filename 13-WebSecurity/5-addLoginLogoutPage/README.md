

# 🧠 Spring Security — Custom Login Page & Logout Setup

## 💡 What’s the Goal?

Till now, Spring Security was using its own **default login page** (plain and boring).  
But in real-world applications, we want a login page that fits the website’s design and gives a better user experience.

So in this step:
- We’ll replace the default Spring login page with our own custom `login.html`.
- Handle login success, failure, and logout behavior.
- Add `Dashboard` as a protected page (visible only after login).

---

## ⚙️ Security Configuration Changes

### 💻 Updated Security Config:

```java
@Bean
SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    http.csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers("/dashboard").authenticated()
            .requestMatchers("/", "/home").permitAll()
            .requestMatchers("/holidays/**").permitAll()
            .requestMatchers("/contact").permitAll()
            .requestMatchers("/saveMsg").permitAll()
            .requestMatchers("/courses").permitAll()
            .requestMatchers("/about").permitAll()
            .requestMatchers("/login").permitAll()
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
```

---

### 💡 What Changed?

| Change | Purpose |
|--------|---------|
| `requestMatchers("/dashboard").authenticated()` | Secured `/dashboard` — login required to access. |
| `loginPage("/login")` | Tells Spring to load your `login.html` (not the default one). |
| `defaultSuccessUrl("/dashboard")` | After a successful login, user is redirected to `/dashboard`. |
| `failureUrl("/login?error=true")` | If login fails, Spring redirects here (so we can show an error). |
| `logoutSuccessUrl("/login?logout=true")` | After logout, user is redirected to `/login?logout=true`. |
| `invalidateHttpSession(true)` | Clears session data on logout for security. |

---

## 🖼️ Our Custom Login Page: `login.html`

We created a beautiful `login.html` page that matches the site design.

✅ **Key Points:**
- Uses the `POST` method to send username and password.
- Matches Spring Security’s expected action: `/login`.
- Shows error or logout messages based on URL (`error=true` or `logout=true`).

💡 **Example Form:**

```html
<form action="/login" method="post" class="signin-form">
    <input type="text" name="username" placeholder="Username" />
    <input type="password" name="password" placeholder="Password" />
    <button class="btn">Log In</button>
</form>
```

💡 If login fails, the controller will display:
```
"Username or Password is incorrect !!"
```
If logout is successful:
```
"You have been successfully logged out !!"
```

---

## 🧠 Login Controller Explained

This controller handles:
- Showing the login page.
- Handling error and logout messages.

### 💡 Code:

```java
@Slf4j
@Controller
public class LoginController {

    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public String displayLoginPage(@RequestParam(value = "error", required = false) String error,
                                   @RequestParam(value = "logout", required = false) String logout, Model model) {
        String errorMessge = null;
        if (error != null) {
            errorMessge = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessge = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessge", errorMessge);
        return "login.html";
    }
}
```

### 🧾 Breakdown:

| Line | Explanation |
|------|-------------|
| `@RequestMapping("/login")` | Maps both GET and POST requests for `/login`. |
| `@RequestParam("error")` | Checks if login failed. |
| `@RequestParam("logout")` | Checks if logout was successful. |
| `model.addAttribute("errorMessge", errorMessge);` | Sends the error message to the view (if any). |
| `return "login.html";` | Tells Spring to load `login.html`. |

---

## 💾 Where Is This Used?

You can check this setup in **Project 9**.

👉 [Get the code for Project 9](/13-WebSecurity/project_9/)*

---

## 🎯 Summary:

- Spring Security now uses your custom login form instead of the default one.
- The `/dashboard` page is protected and requires login.
- Logout clears the session and shows a friendly message.
- All routing and feedback are handled via the controller.

---



# 🧠 Adding a Protected Dashboard Page in Spring Security

Now that we’ve set up our **custom login page** and users, it's time to create a simple **Dashboard page**. This will only be accessible after login.

---

## 💡 Step 1: Create the Dashboard Controller

Inside your `controller` package, create a class called `DashboardController.java`.

```java
package com.eazybytes.eazyschool.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @RequestMapping("/dashboard")
    public String displayDashboard(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("roles", authentication.getAuthorities().toString());
        return "dashboard.html";
    }
}
```

---

### 🔍 Code Explanation:

| Line | Purpose |
|------|---------|
| `@Controller` | Tells Spring this is a controller class handling web requests. |
| `@RequestMapping("/dashboard")` | Maps requests to `/dashboard` URL. |
| `Authentication authentication` | Spring Security will inject the current user’s login details. |
| `authentication.getName()` | Fetches the logged-in username. |
| `authentication.getAuthorities()` | Fetches the user’s assigned roles (like `USER` or `ADMIN`). |
| `model.addAttribute(...)` | Sends the username and roles to `dashboard.html` for display. |
| `return "dashboard.html";` | Loads the `dashboard.html` view when someone opens `/dashboard`. |

---

## 💡 Step 2: Create `dashboard.html`

This is the page shown after a successful login. The username and roles will be displayed using Thymeleaf.

```html
<!-- login block -->
<section class="w3l-contact py-5" id="contact">
    <div class="container py-md-5 py-4">
        <div class="title-main text-center mx-auto mb-md-5 mb-4" style="max-width:500px;">
            <h3 class="title-style" th:text="${'Welcome - ' + username}"></h3>
            <p class="" th:text="${'You logged in as - ' + roles}"></p>
        </div>
    </div>
</section>
<!-- //login block -->
```

### 💡 What This Page Does:
- Shows a welcome message with the username.
- Displays the user’s assigned roles.
- Simple and clean — linked to the `DashboardController`.

---

## 💡 Step 3: Don’t Forget Your Custom Login CSS!

If you're designing your login page, add this CSS **after your contact section styles** in your CSS file:

```css
/* login section */
.login-block input,
.login-block textarea {
  width: 100%;
  color: var(--heading-color);
  background: var(--bg-grey);
  font-size: 16px;
  padding: 14px;
  border: 2px solid var(--border-color-light);
  outline: none;
  margin-bottom: 20px;
  border-radius: var(--border-radius);
}

.log-left.text-center i {
  color: var(--secondary-color);
  font-size: 26px;
}

.login-block textarea {
  height: 180px;
  margin: 0 0 20px 0;
}

.login-block input:focus,
.login-block textarea:focus {
  border: 2px solid var(--secondary-color);
  background: var(--bg-color);
}

.login-left {
  box-shadow: var(--box-shadow);
  padding: 40px;
}

.login-center {
    float: none;
    margin: auto;
}

.log-details p a:hover {
  color: var(--secondary-color);
}

.log-details h6 {
  font-size: 18px;
  font-weight: 600;
  line-height: 18px;
  margin-bottom: 8px;
}

.new-user {
    color: var(--secondary-color);
    margin-left: 40%;
}
/* //login section */
```

✅ This will style your `login.html` page to match your site’s design.

---



# 🧠 Updating Header Navigation for Login / Logout & Secured Pages  

Once you configure Spring Security and start using Thymeleaf for dynamic navigation (like showing **LogIn**, **Dashboard**, or **Logout** based on user status) — you need two important things:  

---

### ✅ 1️⃣ Make Sure You Have the Spring Security Dependency for Thymeleaf  

In your `pom.xml`, add this dependency to enable security expressions inside your Thymeleaf templates (`sec:authorize`):

```xml
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-springsecurity6</artifactId>
</dependency>
```

💡 Without this, the `sec:authorize` conditions in your `header.html` will not work, and the application might fail to load or ignore the conditions.

---

### ✅ 2️⃣ Add the Spring Security Namespace to Your HTML Files  

In every `.html` file where you want to use security expressions (like `sec:authorize`), add this in your `<html>` tag:

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org" 
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity6">
```

This allows Thymeleaf to understand and process the `sec:` attributes.

---

### 💡 3️⃣ Update the Header Navigation

Once the setup is correct, update your `header.html` navigation like this:

```html
<ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll">
    <!-- Visible only when user is not logged in -->
    <li class="nav-item" >
        <a th:href="@{/home}" class="nav-link" aria-current="page">Home</a>
    </li>
    <li class="nav-item" >
        <a th:href="@{/courses}" class="nav-link">Courses</a>
    </li>
    <li class="nav-item" >
        <a th:href="@{/contact}" class="nav-link">Contact</a>
    </li>
    <li class="nav-item" sec:authorize="isAnonymous()">
        <a th:href="@{/login}" class="nav-link">LogIn</a>
    </li>

    <!-- Visible only when user is logged in -->
    <li class="nav-item" sec:authorize="isAuthenticated()">
        <a th:href="@{/dashboard}" class="nav-link">Dashboard</a>
    </li>
    <li class="nav-item" sec:authorize="isAuthenticated()">
        <a th:href="@{/logout}" class="nav-link">Logout</a>
    </li>
</ul>
```

---

### ⚡ Why This Setup?

- 🔒 **Dynamic Navigation** — shows links based on the user’s login status.
- 🧠 **No Hardcoding or Redirection Issues**.
- 💡 Smooth integration with Spring Security once `thymeleaf-extras-springsecurity6` is added.


---

### 🔍 How This Works:

| Expression | Meaning |
|------------|---------|
| `sec:authorize="isAnonymous()"` | Visible **only if the user is NOT logged in**. |
| `sec:authorize="isAuthenticated()"` | Visible **only if the user IS logged in**. |

This way the navbar will:
- Show **LogIn, Home, Courses, Contact** if no one is logged in.
- Show **Dashboard and Logout** when the user is logged in.

---

### ⚡ Why This Is Important:

- 🔒 Avoids showing links to pages users can’t access.
- 🎯 Improves user experience by making the navigation dynamic.
- ✅ Keeps the flow clean for both guests and authenticated users.

---
## 💡 Summary:

- Created a controller for `/dashboard` which shows username and roles.
- Made a simple `dashboard.html` page that displays user info.
- Added login page CSS for a better user experience.

---

💾 **You can grab the full working code from Project 9:**  
👉 [View / Download Project 9](/13-WebSecurity/project_9/)*

---
