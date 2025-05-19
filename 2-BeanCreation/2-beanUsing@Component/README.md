
# 🌱 Spring Learning: Creating Beans Using `@Component`

Welcome to the **Spring Learning Repository**! 🚀
In this guide, you'll learn one of the core concepts of the Spring Framework: **creating beans using the `@Component` annotation**.

---

## 📚 Table of Contents

1. [What is a Spring Bean?](#1-what-is-a-spring-bean)
2. [Creating a Bean using `@Component`](#2-creating-a-bean-using-component)

   * [Step 1: Project Setup](#step-1-project-setup)
   * [Step 2: Creating a Component](#step-2-creating-a-component)
   * [Step 3: Enabling Component Scanning](#step-3-enabling-component-scanning)
   * [Step 4: Using `ApplicationContext`](#step-4-using-applicationcontext)
3. [Related Annotations: `@Service`, `@Repository`, `@Controller`](#3-related-annotations)
4. [Handling Multiple Beans](#4-handling-multiple-beans)
5. [Stereotype Annotations](#5-stereotype-annotations)
6. [`@Bean` vs `@Component`](#6-bean-vs-component)
7. [FAQs](#7-faqs)
8. [Summary](#8-summary)

---

## 1. 🧐 What is a Spring Bean?

A **Spring Bean** is an object that is managed by the **Spring IoC (Inversion of Control) container**.
Spring takes care of its lifecycle, dependencies, and management — reducing boilerplate code and making your app scalable.

---

## 2. 🎯 Creating a Bean using `@Component`

### Step 1: Project Setup

**Add Spring Context dependency to your `pom.xml`:**

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```

### Step 2: Creating a Component

**Directory Structure:**

```
SpringComponentDemo/
└── src/main/java/com/example/demo/
    ├── AppConfig.java
    ├── GreetingService.java
    └── App.java
```

**GreetingService.java**

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

### Step 3: Enabling Component Scanning

**AppConfig.java**

```java
package com.example.demo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo")
public class AppConfig {
}
```

### Step 4: Using `ApplicationContext`

**App.java**

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

🖨️ **Output:**

```
Hello from Spring Component!
```

---

## 3. 🔍 Related Annotations

Spring provides special-purpose annotations, which are internally variations of `@Component`.

| Annotation    | Use Case                         |
| ------------- | -------------------------------- |
| `@Component`  | Generic bean                     |
| `@Service`    | Business logic classes           |
| `@Repository` | Data access classes (DAOs)       |
| `@Controller` | MVC Controller for web endpoints |

---

## 4. ⚠️ Handling Multiple Beans

If you define multiple beans of the same type, Spring will throw a `NoUniqueBeanDefinitionException`.

### Example:

```java
@Component("greetingService1")
public class GreetingService1 { ... }

@Component("greetingService2")
public class GreetingService2 { ... }
```

### Solutions:

✅ **Option 1: Use Bean Name**

```java
GreetingService gs = (GreetingService) context.getBean("greetingService1");
```

✅ **Option 2: Use `@Primary`**

```java
@Primary
@Component
public class GreetingServicePrimary {
    ...
}
```

---

## 5. 🏷️ Stereotype Annotations in Detail

All of the below are types of `@Component`:

### ➤ `@Component`

```java
@Component
public class MyComponent {
    public void show() {
        System.out.println("Hello from MyComponent!");
    }
}
```

### ➤ `@Service`

```java
@Service
public class MyService {
    public String getData() {
        return "Data from service";
    }
}
```

### ➤ `@Repository`

```java
@Repository
public class MyRepository {
    public void saveData() {
        System.out.println("Data saved to DB!");
    }
}
```

### ➤ `@Controller`

```java
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

## 6. 🆚 `@Bean` vs `@Component`

| Feature                  | `@Bean` (Manual)                     | `@Component` (Automatic)                |
| ------------------------ | ------------------------------------ | --------------------------------------- |
| Location                 | Method-level inside `@Configuration` | Class-level                             |
| Instantiation Control    | Full control over instantiation      | Spring auto-instantiates                |
| External Library Support | Best suited                          | Not suitable unless class is modifiable |
| Customization            | Highly customizable                  | Less flexible                           |
| Dependency Injection     | Manual (through method args)         | Automatic (via constructor/field)       |
| Naming                   | From method name or `@Bean("name")`  | From class name or `@Component("name")` |

### Example of `@Bean`:

```java
@Configuration
public class AppConfig {
    @Bean
    public MyComponent myComponent() {
        return new MyComponent();
    }
}
```

---

## 7. ❓ FAQs

### Can we use `@Primary` on multiple beans?

**No.** Spring will throw an error — only one bean can be marked as primary.

### Difference between `@Component` and `@Bean`?

| Feature       | `@Component` | `@Bean`                    |
| ------------- | ------------ | -------------------------- |
| Target        | Class        | Method inside config class |
| Auto-detected | Yes          | No (manually declared)     |
| Flexibility   | Limited      | High                       |

### Can we create beans without `@Component`?

**Yes!** Use `@Bean` inside a `@Configuration` class:

```java
@Bean
public GreetingService greetingService() {
    return new GreetingService();
}
```

---

## 8. ✅ Summary

* ✅ Learned what a Spring Bean is.
* ✅ Used `@Component` to create a Spring-managed bean.
* ✅ Understood how component scanning works with `@ComponentScan`.
* ✅ Used `ApplicationContext` to retrieve beans.
* ✅ Explored stereotype annotations like `@Service`, `@Repository`, `@Controller`.
* ✅ Compared `@Component` with `@Bean` for different use cases.
* ✅ Handled multiple beans with `@Primary` or bean names.

---

Let me know if you want this converted into a markdown file (`README.md`) or need visual diagrams for better understanding.
