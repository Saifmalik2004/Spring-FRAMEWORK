# Wiring Beans Using @Autowired on Constructor in Spring

## 🌱 Introduction
Wiring beans using `@Autowired` on a constructor is the most recommended approach for dependency injection in Spring. This method ensures that dependencies are provided at the time of object creation, making the class immutable and promoting better testability.

## ✅ How It Works
Spring automatically injects the required bean by calling the constructor annotated with `@Autowired`. If there is only one constructor in the class, Spring will inject dependencies even without the `@Autowired` annotation (starting from Spring 4.3).

### 🔍 Project Structure
```
SpringAutowiredConstructor/
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
    private final Engine engine;

    @Autowired // Constructor Injection
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is moving!");
    }
}
```
**Explanation:**
- `@Component` makes `Car` a Spring-managed bean.
- `@Autowired` on the constructor tells Spring to inject the required `Engine` bean.
- The `final` keyword ensures the dependency is immutable and cannot be changed later.
- If there is only one constructor, Spring automatically injects dependencies even without `@Autowired`.

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
✔ **Recommended Approach** – Ensures immutability and proper initialization.
✔ **Better for Testing** – Dependencies can be easily injected through constructors.
✔ **No Need for `@Autowired` (if only one constructor exists)** – Cleaner and more concise code.
❌ **Less Flexible than Setter Injection** – Requires dependencies at object creation.

In the next guide, we'll explore **advanced autowiring techniques like `@Qualifier` and `@Primary`**, which help resolve conflicts when multiple beans of the same type exist. 🚀

