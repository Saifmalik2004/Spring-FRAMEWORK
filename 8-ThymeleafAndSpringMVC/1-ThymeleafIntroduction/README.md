# Introduction to Thymeleaf

## What is Thymeleaf?
Thymeleaf is a modern server-side Java template engine used for web applications. It helps developers generate dynamic HTML, XML, or even JavaScript pages while keeping the templates highly readable and maintainable.

### Why is Thymeleaf Essential?
- **Seamless Integration with Spring Boot**: Works effortlessly with Spring Boot to generate dynamic web pages.
- **Readable and Maintainable HTML**: Unlike JSP, Thymeleaf allows templates to be viewed and edited as standard HTML files.
- **Powerful Templating Features**: Provides loops, conditionals, and fragment reuse to enhance page development.
- **Supports Both Server-Side and Client-Side Rendering**: Can be used for server-side templating and also in combination with JavaScript for dynamic UI updates.

## Key Concepts and Terminologies
Before diving into examples, let’s understand some essential Thymeleaf concepts:

1. **Template Engine**: Thymeleaf acts as a bridge between backend data and frontend UI, allowing dynamic rendering of HTML.
2. **Thymeleaf Expressions**:
   - **Variable Expression (`${}`)**: Used to display dynamic values from the model.
   - **Selection Expression (`*{}`)**: Used inside `<th:block>` to reference the current object.
   - **Message Expression (`#{}`)**: Used for localization and messages from `messages.properties`.
   - **Link Expression (`@{}`)**: Used to dynamically generate URLs.
3. **Attributes and Directives**:
   - `th:text`: Sets text content of an element.
   - `th:each`: Loops over a collection.
   - `th:if` / `th:unless`: Conditional rendering.
   - `th:fragment`: Defines reusable components.

## Real-World Analogy:
Think of Thymeleaf as a restaurant menu system:
- The **menu template (HTML file)** remains the same, just like the structure of a Thymeleaf template.
- The **chef (backend code)** prepares dishes based on available ingredients (data).
- The **waiter (Thymeleaf processor)** dynamically fills in the placeholders (variables in the template) to generate a complete menu (webpage) based on current availability.

---

## How to Use Thymeleaf in a Spring Boot Project
### 1️⃣ Setting Up Thymeleaf in a Spring Boot Project
Spring Boot automatically includes Thymeleaf support. If not included, add the dependency in `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### 2️⃣ Creating a Simple Thymeleaf Template
Create an HTML file inside the `src/main/resources/templates/` directory:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Welcome Page</title>
</head>
<body>
    <h1 th:text="${message}">Default Message</h1>
</body>
</html>
```

### 3️⃣ Creating a Controller to Pass Data
Create a Spring Boot controller to send data to the template:
```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "welcome";
    }
}
```
Now, when visiting `http://localhost:8080/`, you will see **Hello, Thymeleaf!** displayed dynamically in the template.

---

## 🌟 Project-Based Learning Approach!
In this repository, we are not just learning Thymeleaf as a theory-based topic; we are going to build a real-world project step by step! 🛠️

- We will start with **basic concepts** and gradually **implement them in a real application**.
- As we progress, we will **enhance our project**, improving its features and making it production-ready.
- By the end, you will have **a fully functional website** using Spring Boot and Thymeleaf.

So don’t worry about just reading—**you will get hands-on experience as we build together!** 🚀


## 🔗 Additional Resources
- [Thymeleaf Official Documentation](https://www.thymeleaf.org/documentation.html)
- [Spring Boot + Thymeleaf Guide](https://spring.io/guides/gs/serving-web-content/)

---

## 🛠 Hands-on Practice Files
Explore the practice files for this topic in this repository: [Thymeleaf Examples](./thymeleaf-examples/)

