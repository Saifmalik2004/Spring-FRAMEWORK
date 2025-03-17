# Wiring Beans Using Method Calls in Spring

## 🌱 Introduction
Wiring in Spring refers to connecting components (beans) so they can work together. One simple way to wire beans is by manually calling one method inside another in a configuration class.

## ✅ How It Works
This approach involves defining Spring beans in a `@Configuration` class and explicitly calling one bean inside another.

### 🔍 Project Structure
```
SpringWiringMethod/
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

### 2️⃣ Define the Car Class (Dependent Bean)
```java
package com.example;

public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is moving!");
    }
}
```

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
    public Car car() {
        return new Car(engine()); // Manually passing dependency
    }
}
```

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

---

## 📌 Summary
✔ **Simple to Implement** – Directly calling methods makes it easy to set up dependencies.
✔ **Clear Structure** – Each bean is explicitly defined.
❌ **Tightly Coupled** – Creates a new instance each time unless managed by Spring’s singleton scope.

In the next guide, we'll explore **wiring using method parameters** to improve reusability! 🚀

# Wiring Beans Using Method Calls in Spring

## 🌱 Introduction
Wiring in Spring refers to connecting components (beans) so they can work together. One way to wire beans is by manually calling one method inside another in a configuration class.

## ✅ How It Works
This approach involves defining Spring beans in a `@Configuration` class and explicitly calling one bean inside another.

### 🔍 Project Structure
```
SpringWiringMethod/
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

### 2️⃣ Define the Car Class (Dependent Bean)
```java
package com.example;

public class Car {
    private Engine engine;

    public Car(Engine engine) {
        this.engine = engine;
    }

    public void drive() {
        engine.start();
        System.out.println("Car is moving!");
    }
}
```

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
    public Car car() {
        return new Car(engine()); // Manually passing dependency
    }
}
```

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

---

## 📌 Summary
✔ **Simple to Implement** – Directly calling methods makes it easy to set up dependencies.
✔ **Clear Structure** – Each bean is explicitly defined.
❌ **Potential Issue** – Creates a **new instance** each time unless managed by Spring’s singleton scope.

---

In the next guide, we'll explore **wiring using method parameters** to improve reusability! 🚀

