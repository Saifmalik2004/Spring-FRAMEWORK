# 📖 Welcome to Your First Thymeleaf Project – **Education Hub**  

## 🌟 Introduction  
Welcome to our first **Spring Boot + Thymeleaf** project! 🎉  

In this repository, we are building a simple **Education Hub** website using Thymeleaf as our template engine. This is just the beginning! As we move forward, we will **gradually enhance this project**, adding more features and making it **production-ready** step by step. 🚀  

---

## 🎯 What You Will Learn  
 
✅ How to **pass dynamic data** from a Java controller to an HTML page.  
✅ What the **`Model` interface** is and how it helps in passing data.  
✅ How to create a **Thymeleaf-powered HTML page**.  

---
## 📂 Project Structure  

Inside this repository, we have a folder named **`project-1`** that contains all the necessary files for running our Spring Boot application.  

```
📦 repository-root
 ┣ 📂 project-1
 ┃ ┣ 📂 src
 ┃ ┃ ┣ 📂 main
 ┃ ┃ ┃ ┣ 📂 java
 ┃ ┃ ┃ ┃ ┗ 📂 com.learnwithsaif.project_1.controller
 ┃ ┃ ┃ ┃ ┃ ┗ 📜 HomeController.java
 ┃ ┃ ┃ ┣ 📂 resources
 ┃ ┃ ┃ ┃ ┗ 📂 templates
 ┃ ┃ ┃ ┃ ┃ ┗ 📜 home.html
 ┃ ┣ 📜 pom.xml
 ┃ ┣ 📜 README.md
```

---


## 🚀 Step 1: Setting Up Thymeleaf in Spring Boot  
Spring Boot comes with Thymeleaf **pre-configured**. However, if you don’t have it, you can add the dependency in `pom.xml`:  

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

---

## 🏗️ Step 2: Creating Our First Thymeleaf Page  

### **1️⃣ Creating an HTML File (`home.html`)**
Inside `src/main/resources/templates/`, create a file named `home.html`:

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Education Hub</title>
</head>
<body>
    <h1 th:text="'Hey, ' + ${username} + ' !!! Welcome to Education Hub.'"></h1>
</body>
</html>
```

🔹 **`th:text`**: This is a Thymeleaf expression used to **dynamically replace** the content inside the `<h1>` tag.  

Example: If `username = "John Doe"`, the rendered HTML will be:  
```html
<h1>Hey, John Doe !!! Welcome to Education Hub.</h1>
```

---

## 🎯 Step 3: Creating a Spring Boot Controller  

The **Controller** is responsible for handling user requests and passing data to the **Thymeleaf template**.  

### **2️⃣ Creating a Controller (`HomeController.java`)**
Inside `src/main/java/com/learnwithsaif/project_1/controller/`, create a file named `HomeController.java`:

```java
package com.learnwithsaif.project_1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping(value={"", "/", "home"})
    public String displayHomePage(Model model) {
        model.addAttribute("username", "John Doe");
        return "home.html";
    }

}
```

### 🔍 **Understanding the Code**  
✅ **`@Controller`** → Marks this class as a **Spring MVC controller**.  
✅ **`@RequestMapping(value={"", "/", "home"})`** → Maps multiple URLs (`/`, `/home`) to this controller method.  
✅ **`Model model`** → Used to **pass data** from the controller to the Thymeleaf template.  
✅ **`model.addAttribute("username", "John Doe")`** → Adds `"John Doe"` to the model, which Thymeleaf can use.  
✅ **Returns `"home.html"`** → This tells Spring Boot to **render the `home.html` file** inside `templates/`.  

---

## 🚀 How to Run the Project  

### **Step 1: Navigate to the `project-1` Folder**  
Open your terminal and navigate to the `project-1` directory:  

```sh
cd project-1
```

### **Step 2: Run the Project Using Maven**  
Use the following command to build and run the Spring Boot application:  

```sh
mvn spring-boot:run
```

### **Step 3: Open in Browser**  
Once the application starts, open your browser and visit:  

🔗 [http://localhost:8080/](http://localhost:8080/)  

You should see the welcome message displayed on the webpage.

---

## 📌 How Does This Work?  

1️⃣ **User visits** `http://localhost:8080/` or `http://localhost:8080/home`.  
2️⃣ The `HomeController` handles the request and **passes "John Doe" as the username** to the Thymeleaf template.  
3️⃣ Thymeleaf **renders the dynamic HTML**, displaying:  

```html
<h1>Hey, John Doe !!! Welcome to Education Hub.</h1>
```

---



## 📚 Additional Resources  
🔗 [Thymeleaf Official Documentation](https://www.thymeleaf.org/documentation.html)  
🔗 [Spring Boot + Thymeleaf Guide](https://spring.io/guides/gs/serving-web-content/)  

---

## 📂 Hands-on Practice Files  
Check out the hands-on code files in this repository: [Education Hub Project](./project_1/)  

---

Happy Coding! 🎉🚀