# 🚀 Setting Up a Spring Boot Project with a Simple HTML Page

## 1️⃣ Introduction
Spring Boot makes it easy to create stand-alone, production-ready Spring applications. In this guide, we'll set up a basic Spring Boot project using Spring Initializr and create a simple HTML page to display in the browser.

---

## 2️⃣ Creating a Spring Boot Project Using Spring Initializr
Spring Initializr is an online tool that generates a pre-configured Spring Boot project.

### 🔹 Steps to Create a Project:
1. Open [Spring Initializr](https://start.spring.io/).
2. **Project Type** → Choose **Maven** or **Gradle**.
3. **Language** → Select **Java**.
4. **Spring Boot Version** → Choose the latest stable version.
5. **Project Metadata**:
   - **Group**: `com.example`
   - **Artifact**: `demo`
   - **Name**: `SpringBootDemo`
   - **Package Name**: `com.example.demo`
   - **Packaging**: `Jar`
   - **Java Version**: `17` (or latest LTS version)
6. **Dependencies**:
   - Select **Spring Web** (for building web applications).
7. Click **Generate** to download the project as a ZIP file.
8. Extract the ZIP file and open it in your favorite IDE (IntelliJ IDEA, VS Code, Eclipse, etc.).



---

## 4️⃣ Creating a Simple HTML Page
### 📌 Step 1: Create `index.html`
Inside `src/main/resources/static/`, create a file named `index.html`:

```html
<!DOCTYPE html>
<html>
<head>
    <title>Welcome to Spring Boot</title>
</head>
<body>
    <h1>Hello, Spring Boot!</h1>
</body>
</html>
```

### 📌 Step 2: Create a Controller to Serve the HTML Page
Inside `src/main/java/com/example/demo/controller/`, create `HomeController.java`:

```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home() {
        return "index.html";
    }
}


```

---

## 5️⃣ Running the Spring Boot Project
### ▶️ Steps to Run the Application
1. Open a terminal or command prompt.
2. Navigate to the project directory.
3. Run the following command:
   ```sh
   mvn spring-boot:run  # For Maven users
   ```
   OR
   ```sh
   ./gradlew bootRun  # For Gradle users
   ```
4. Open a browser and visit: `http://localhost:8080/`
5. You should see the **"Hello, Spring Boot!"** message from `index.html`.

---

## 🔥 Troubleshooting  

### ❌ Issue: "Whitelabel Error Page" or 404 Not Found  

✅ **Solution 1:** Ensure you have either:  
- **Static `index.html` inside `src/main/resources/static/`**  
- OR a **`@Controller` returning file name `index.html`**.  


✅ **Solution 2:** Check if your Spring Boot application is running properly using:  
```sh
mvn spring-boot:run
```
---
# 📂 Understanding the Spring Boot Project Structure

When you generate a Spring Boot project using Spring Initializr, you get a structured setup that makes development organized and efficient. Below is a breakdown of the key directories and files in a Spring Boot project.

---

## 🏗️ Project Structure Overview
```
SpringBootDemo/
│── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── DemoApplication.java  <-- Main Spring Boot entry point
│   │   │   ├── controller/
│   │   │   │   ├── HomeController.java  <-- Handles web requests
│   │   ├── resources/
│   │   │   ├── static/  <-- Stores static files (CSS, HTML, JS, Images)
│   │   │   │   ├── index.html  <-- Simple HTML page
│   │   │   ├── templates/  
│   │   │   ├── application.properties  <-- Configuration file
│── pom.xml (For Maven dependencies)
```

---

## 📌 Detailed Explanation of Each Component

### 1️⃣ `src/main/java/com/example/demo/`
This is the main directory for your Java source code. It contains all the application logic, controllers, services, and configurations.

#### 🔹 `DemoApplication.java`
- This is the **main entry point** of the Spring Boot application.
- It contains the `@SpringBootApplication` annotation, which enables auto-configuration and component scanning.
- Runs the application using the `main` method.

```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### 2️⃣ `controller/`
- This folder contains controllers that handle HTTP requests.
- **`HomeController.java`** is an example controller that serves the `index.html` page.

```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
```
#### **Handling Multiple Paths in a Controller**
- You can define multiple URL paths for a single controller method using an array in the `@GetMapping` annotation.
- This is useful when you want different routes (e.g., `/` and `/home`) to serve the same page.
- Example use case:
  - Ensuring that both the root URL (`/`) and `/home` point to the same homepage.
  - Handling different URL variations without creating multiple controller methods.

---
```java
package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "index";
    }
}
```
### 3️⃣ `resources/`
This directory contains configuration files, templates, and static assets.

#### 🔹 `static/`
- Stores static resources like **CSS, HTML, JavaScript, and images**.
- Files placed here are directly accessible via URL (e.g., `/images/logo.png`).

#### 🔹 `templates/`
- Stores **HTML pages** rendered by Spring Boot using **Thymeleaf (default)**.


#### 🔹 `application.properties`
- The **configuration file** for defining application settings.
- Example settings:
  ```properties
  server.port=8080  # Changes the default port
  spring.application.name=SpringBootDemo
  ```

### 4️⃣ `pom.xml` (For Maven Projects)
- **Manages dependencies** required for the project.
- Example dependencies:
  ```xml
  <dependencies>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
  </dependencies>
  ```

---


## 🔧 5. Customizing Default Path & Port in Spring Boot  

By default, Spring Boot runs on **port 8080** and serves content from the **root ("/") context path**. However, you can customize these settings easily in `application.properties` or `application.yml`.  

### 🌐 5.1 Changing the Default Port  
To change the default port (e.g., to **9090**), update `application.properties`:  

```properties
server.port=9090
```

Or in `application.yml`:  

```yaml
server:
  port: 9090
```


## ❓ 5.2 What Happens If We Set `server.port=0`?
If you set the port to `0`, Spring Boot will automatically assign a **random available port** at runtime. This is useful when running multiple instances of an application without port conflicts.

### Example in `application.properties`:
```properties
server.port=0
```

### 📂 5.3 Changing the Context Path  
To modify the default context path (e.g., to `/api`), update `application.properties`:  

```properties
server.servlet.context-path=/api
```

Or in `application.yml`:  

```yaml
server:
  servlet:
    context-path: /api
```


### 🚀 5.4 Verifying Changes  
After applying these configurations, start your Spring Boot application and access it at:  
- **http://localhost:9090/api** (if you changed both port and context path).  

---



## 🎯 Conclusion
Congratulations! 🎉 You have successfully created a **Spring Boot project** with a simple **HTML page** and learned how to run it. This is just the beginning; you can now explore adding more features like REST APIs, database integration, and more!