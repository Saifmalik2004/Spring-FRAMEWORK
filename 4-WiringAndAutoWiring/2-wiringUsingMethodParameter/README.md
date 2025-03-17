# Wiring Beans Using Method Parameters in Spring

## 🌱 Introduction
Wiring beans using **method parameters** is a more structured way to inject dependencies in Spring. Instead of manually calling a method, Spring automatically provides the required bean by passing it as a parameter inside the `@Bean` method.

This approach ensures that the same instance of a bean is injected whenever required, improving maintainability and reducing tight coupling.

## ✅ How It Works
Instead of explicitly calling a method (`engine()`), we declare the dependency as a parameter inside another `@Bean` method. Spring automatically resolves and injects the dependency.

### 🔍 Project Structure
```
SpringWiringMethodParam/
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

public class Engine {
    public void start() {
        System.out.println("Engine started!");
    }
}
```
**Explanation:** This class represents the `Engine` bean, which will be injected into the `Car` class.

### 2️⃣ Define the Car Class (Dependent Bean)
```java
package com.example;

public class Car {
    private Engine engine;

    public Car(Engine engine) { // Dependency injected via constructor
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is moving!");
    }
}
```
**Explanation:** Instead of creating an `Engine` instance inside `Car`, we use constructor injection to accept an `Engine` dependency.

### 3️⃣ Create the Configuration Class
```java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public Engine engine() {
        return new Engine();
    }
    
    @Bean
    public Car car(Engine engine) { // Spring automatically injects the Engine bean
        return new Car(engine);
    }
}
```
**Explanation:**
- `engine()` defines the `Engine` bean.
- `car(Engine engine)` receives the `Engine` bean as a parameter, and Spring injects it automatically.

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
- It automatically injects the `Engine` bean into `Car`.
- The `Car` instance is retrieved and its `drive()` method is executed.

---

## 📌 Summary
✔ **Ensures Reusability** – The same bean instance is used whenever required.
✔ **Less Boilerplate Code** – No need to call methods explicitly.
✔ **Better Maintainability** – Dependencies are injected automatically.

Next, we'll explore **wiring using `@Autowired` annotation**, which provides even more flexibility! 🚀

