# Wiring Beans Using @Autowired on Class Fields in Spring

## 🌱 Introduction
Wiring beans using `@Autowired` on class fields is one of the simplest ways to inject dependencies in a Spring application. With this approach, Spring automatically injects the required bean without needing explicit setter or constructor methods.

However, while field injection is easy to implement, it has some drawbacks in terms of testability and maintainability.

## ✅ How It Works
Spring automatically finds a matching bean and injects it into the field marked with `@Autowired`. This eliminates the need for manual wiring in a configuration class.

### 🔍 Project Structure
```
SpringAutowiredField/
│-- src/main/java/com/example/
│   │-- AppConfig.java
│   │-- Engine.java
│   │-- Car.java
│   │-- MainApp.java
│-- pom.xml
```

---

## 🔹 Step-by-Step Implementation

### 1️⃣ Define the Engine Class (Dependency)
```java
package com.example;

import org.springframework.stereotype.Component;

@Component
public class Engine {
    public void start() {
        System.out.println("Engine started!");
    }
}
```
**Explanation:**
- The `@Component` annotation tells Spring to manage this class as a bean.
- The `start()` method simulates engine functionality.

### 2️⃣ Define the Car Class (Dependent Bean)
```java
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Car {
    @Autowired // Field Injection
    private Engine engine;

    public void drive() {
        engine.start();
        System.out.println("Car is moving!");
    }
}
```
**Explanation:**
- `@Component` makes `Car` a Spring-managed bean.
- `@Autowired` on the `engine` field tells Spring to automatically inject the required `Engine` bean.

### 3️⃣ Create the Configuration Class (Optional)
```java
package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```
**Explanation:**
- `@Configuration` defines a Spring configuration class.
- `@ComponentScan` tells Spring to scan the `com.example` package for components.

### 4️⃣ Create the Main Application
```java
package com.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Car car = context.getBean(Car.class);
        car.drive();
    }
}
```
**Explanation:**
- The Spring context loads `AppConfig.class`.
- It automatically scans for components and injects dependencies.
- The `Car` bean is retrieved and its `drive()` method is executed.

---

## 📌 Summary
✔ **Simplest Way to Inject Dependencies** – No need for setters or constructors.
✔ **Less Boilerplate Code** – Just use `@Autowired`.
❌ **Not Recommended for Testing** – Difficult to mock dependencies.
❌ **Tightly Coupled** – Directly modifying private fields is not ideal.

In the next guide, we'll explore **wiring using `@Autowired` on setter methods**, which provides better flexibility! 🚀

