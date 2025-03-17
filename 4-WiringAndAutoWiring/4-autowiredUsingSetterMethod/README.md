# Wiring Beans Using @Autowired on Setter Method in Spring

## 🌱 Introduction
Wiring beans using `@Autowired` on a setter method is a flexible way to inject dependencies in Spring. This approach allows optional dependencies, meaning the setter method can be omitted if the dependency is not required. It also improves testability compared to field injection.

## ✅ How It Works
Spring automatically injects the required bean by calling the setter method annotated with `@Autowired`. This allows for better control over bean initialization.

### 🔍 Project Structure
```
SpringAutowiredSetter/
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
    private Engine engine;

    @Autowired // Setter Injection
    public void setEngine(Engine engine) {
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
- `@Autowired` on the setter method tells Spring to automatically inject the required `Engine` bean.
- This approach allows optional dependencies since the setter can be omitted if needed.

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
✔ **More Flexible than Field Injection** – Allows optional dependencies.
✔ **Better for Testing** – Can easily replace dependencies with mocks.
✔ **Improves Maintainability** – Dependencies can be set at runtime.
❌ **More Boilerplate Code** – Requires explicit setter methods.

In the next guide, we'll explore **wiring using `@Autowired` on constructors**, which is the recommended approach! 🚀

