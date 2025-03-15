# Spring Learning: Creating Beans Using @Component

## 📌 Introduction
Welcome to the **Spring Learning Repository**! 🚀 In this section, we will explore one of the fundamental concepts of Spring Framework: **Creating Beans Using `@Component`**.

By the end of this guide, you will:
- Understand what a Spring **Bean** is.
- Learn how to create a Bean using `@Component`.
- Discover how Spring automatically manages Beans.
- Use `ApplicationContext` to retrieve and utilize Beans.
- Understand related annotations like `@ComponentScan`, `@Service`, and `@Repository`.

Let's dive in! 🏊‍♂️

---
## 🧐 What is a Spring Bean?
A **Spring Bean** is an object managed by the **Spring IoC (Inversion of Control) Container**. Beans are created, initialized, and managed automatically by Spring, reducing boilerplate code and improving scalability.

---
## 🎯 Creating a Bean Using `@Component`
### 🏗️ **Step 1: Set Up a Spring Project**
First, ensure you have a **Spring Boot** or **Spring Core** project. You can create a Maven project and add the required dependency:

**Add Spring Context Dependency in `pom.xml`**
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```

---
### 📝 **Step 2: Create a Simple Component Bean**
We use `@Component` to define a class as a **Spring Bean**. Let's create a simple `GreetingService` class.

#### 📂 **Project Structure**
```
SpringComponentDemo/
│── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   ├── AppConfig.java
│   │   │   ├── GreetingService.java
│   │   │   ├── App.java
│── pom.xml
```

#### 📌 **Define a Bean Using `@Component`**
```java
package com.example.demo;

import org.springframework.stereotype.Component;

@Component
public class GreetingService {
    public String getGreeting() {
        return "Hello from Spring Component!";
    }
}
```
✅ The `@Component` annotation marks `GreetingService` as a Spring-managed Bean.

---
### 🔍 **Step 3: Enable Component Scanning**
For Spring to detect and register `@Component` classes, we need to enable **component scanning** using `@ComponentScan`.

#### 📌 **Configuration Class (AppConfig.java)**
```java
package com.example.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppConfig {
}
```
✅ The `@ComponentScan` annotation tells Spring where to look for `@Component` classes.

---
### 🏗️ **Step 4: Use `ApplicationContext` to Retrieve the Bean**
Let's now create a main class to retrieve and use our `GreetingService` bean.

#### 📌 **Main Class (App.java)**
```java
package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        GreetingService greetingService = context.getBean(GreetingService.class);
        
        System.out.println(greetingService.getGreeting());
    }
}
```
✅ This will print:
```
Hello from Spring Component!
```
💡 `context.getBean(GreetingService.class);` retrieves the bean managed by Spring.

---
## 🔍 Understanding Related Annotations: `@Service` and `@Repository`
Spring provides specialized versions of `@Component` for better clarity:

| Annotation  | Purpose  |
|------------|----------|
| `@Component`  | Generic annotation to mark a class as a Bean |
| `@Service`    | Specifically for **business logic** classes (e.g., services) |
| `@Repository` | Specifically for **data access** classes (e.g., DAOs) |

These annotations behave the same way but help in better organizing the project structure.

---
## 🔥 Advanced Topic: Handling Multiple Beans
### ⚠️ Problem: `NoUniqueBeanDefinitionException`
If you have multiple Beans of the **same type**, Spring will throw an error when trying to retrieve a Bean without specifying its name.

Example:
```java
@Component("greetingService1")
public class GreetingService1 {
    public String getGreeting() {
        return "Hello from Service 1!";
    }
}

@Component("greetingService2")
public class GreetingService2 {
    public String getGreeting() {
        return "Hello from Service 2!";
    }
}
```
Trying to retrieve `GreetingService` will cause an error:
```
NoUniqueBeanDefinitionException: expected single matching bean but found 2
```

### ✅ Solution 1: Use Bean Name
```java
GreetingService service1 = (GreetingService) context.getBean("greetingService1");
```

### ✅ Solution 2: Use `@Primary`
Mark one bean as **default** using `@Primary`:
```java
@Primary
@Component
public class GreetingServicePrimary {
    public String getGreeting() {
        return "Hello from Primary Service!";
    }
}
```
This ensures that `GreetingServicePrimary` is used when no explicit name is given.

 

---

## **Stereotype Annotations in Spring**  
### **What are Stereotype Annotations?**  
Stereotype annotations in Spring are **specialized annotations** used to define and categorize Spring-managed components. They help Spring automatically detect and register beans without requiring explicit configuration in Java or XML.

---
### **Common Stereotype Annotations**  
Spring provides several stereotype annotations, each with a specific purpose:  

1. **`@Component`** – A generic annotation to mark any class as a Spring-managed component.  
2. **`@Service`** – Used for business logic components (like services).  
3. **`@Repository`** – Used for DAO (Data Access Object) classes that interact with the database.  
4. **`@Controller`** – Used for Spring MVC controllers to handle HTTP requests.  

All these annotations are **meta-annotations** of `@Component`, meaning they function similarly but are intended for specific use cases.

---

### **Example Usage**  
#### **Using `@Component`**  
```java
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
    public void show() {
        System.out.println("Hello from MyComponent!");
    }
}
```
#### **Using `@Service`**  
```java
import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String getData() {
        return "Data from service";
    }
}
```
#### **Using `@Repository`**  
```java
import org.springframework.stereotype.Repository;

@Repository
public class MyRepository {
    public void saveData() {
        System.out.println("Data saved to DB!");
    }
}
```
#### **Using `@Controller`**  
```java
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MyController {

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello from Controller!";
    }
}
```

---


## **Comparison: `@Bean` vs `@Component`**  

| Feature               | `@Bean`                                   | `@Component`                              |
|----------------------|-----------------------------------------|------------------------------------------|
| **Definition**        | Declares a bean manually inside a `@Configuration` class. | Automatically detects and registers a bean. |
| **Instantiation**    | The method inside `@Configuration` class returns an instance explicitly. | Spring automatically instantiates the class. |
| **Number of Instances** | Multiple instances can be defined for different beans (method names control them). | Only one instance per class unless explicitly modified. |
| **Scope**            | More flexible—can specify scope (`@Scope("prototype")`, etc.). | By default, Singleton scope but can be changed. |
| **Dependency Injection** | Fully customizable—you can provide arguments manually when creating the bean. | Uses constructor or field injection automatically. |
| **Multiple Beans of Same Type** | You can define multiple beans of the same type with different names inside `@Configuration`. | Needs `@Qualifier` if multiple beans of the same type exist. |
| **Custom Name** | The method name acts as the bean name, but you can override it with `@Bean("customName")`. | Uses class name (camelCase) but can be overridden using `@Component("customName")`. |
| **Third-party Library Beans** | **Best suited** for creating beans of third-party libraries. | **Not suitable** for third-party classes, since you can’t modify them to add `@Component`. |
| **Use Case**         | When you need to **manually configure** a bean or create beans from external libraries. | When you want **Spring to handle component scanning automatically**. |

---
### **Example of `@Bean`**
```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MyComponent myComponent() {
        return new MyComponent();
    }
}
```

### **Example of `@Component`**
```java
import org.springframework.stereotype.Component;

@Component
public class MyComponent {
    public void show() {
        System.out.println("Hello from MyComponent!");
    }
}
```
---
### **When to Use Which?**
- Use `@Component` when you want **Spring to manage beans automatically**.
- Use `@Bean` when you need **more control over bean instantiation**, such as:
  - Creating beans from third-party libraries.
  - Initializing beans with specific constructor arguments.

---

## ❓ FAQs
### ❓ Can we use `@Primary` on multiple Beans?
**No**. If you annotate multiple beans with `@Primary`, Spring will throw an error because it won't know which one to prioritize.

### ❓ What is the difference between `@Component` and `@Bean`?
| Feature | `@Component` | `@Bean` |
|---------|-------------|---------|
| Used on | Class-level | Method-level |
| Automatic Scanning | Yes | No (explicitly defined in config class) |
| Flexibility | Less control | More control over bean creation |

---

### ❓ Can I create Beans without `@Component`?
Yes! You can use `@Bean` inside a configuration class instead of `@Component`.
```java
@Bean
public GreetingService greetingService() {
    return new GreetingService();
}
```

---
## 🎯 Summary
✅ **We learned how to create Spring Beans using `@Component`.**  
✅ **We enabled automatic scanning using `@ComponentScan`.**  
✅ **We retrieved Beans using `ApplicationContext`.**  
✅ **We handled multiple Beans and resolved conflicts.**  
✅ **We explored related annotations like `@Service` and `@Repository`.**  

Now you're ready to use **Spring Beans** in real-world applications! 🎉🔥

