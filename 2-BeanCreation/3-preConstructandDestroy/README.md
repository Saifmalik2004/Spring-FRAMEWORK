# Spring Learning Repository - @PostConstruct and @PreDestroy

## 🌱 Introduction

Spring provides powerful lifecycle management for beans, allowing us to execute logic **before** and **after** a bean is initialized or destroyed. Two key annotations that help us achieve this are:

- **`@PostConstruct`** → Runs **after** the bean is created and dependencies are injected.
- **`@PreDestroy`** → Runs **before** the bean is removed from the context.

These annotations allow us to execute setup and cleanup logic **automatically** without manual intervention.

---
## 🎯 Why Use @PostConstruct and @PreDestroy?

- `@PostConstruct`: Ideal for initializing resources, validating configuration, or performing setup tasks.
- `@PreDestroy`: Useful for closing connections, releasing resources, or saving final states before destruction.

Using these annotations ensures **clean, maintainable, and efficient resource management** in your application.

---
## 🏗️ Setting Up a Spring Project

### Step 1️⃣: Create a Spring Boot Application
If you don't have a Spring Boot project, create one using:
```sh
mvn archetype:generate -DgroupId=com.example -DartifactId=SpringLifecycleDemo \
    -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
cd SpringLifecycleDemo
```

### Step 2️⃣: Add Spring Dependencies
In your `pom.xml`, add:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```

Run:
```sh
mvn clean install
```

---
## 🔹 Using @PostConstruct and @PreDestroy

### Step 3️⃣: Create a Service Class
Create `LifecycleService.java` inside `src/main/java/com/example/`:
```java
package com.example;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
public class LifecycleService {
    public LifecycleService() {
        System.out.println("Constructor: Bean instance created");
    }

    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct: Initializing bean");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("@PreDestroy: Cleaning up before bean destruction");
    }
}
```

👉 **Explanation:**
- The constructor is called when the bean is created.
- `@PostConstruct` runs **after** the bean is initialized.
- `@PreDestroy` runs **before** the bean is destroyed.

---
## 🔥 Testing Lifecycle Methods

### Step 4️⃣: Create a Main Application Class
Create `SpringLifecycleApplication.java`:
```java
package com.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringLifecycleApplication {
    public static void main(String[] args) {
        System.out.println("Starting Application...");
        
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        LifecycleService service = context.getBean(LifecycleService.class);
        System.out.println("Bean retrieved and used");
        
        context.close();
        System.out.println("Application stopped");
    }
}
```

### Step 5️⃣: Define a Configuration Class
Create `AppConfig.java`:
```java
package com.example;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.example")
public class AppConfig {
}
```

### Expected Output:
```sh
Starting Application...
Constructor: Bean instance created
@PostConstruct: Initializing bean
Bean retrieved and used
@PreDestroy: Cleaning up before bean destruction
Application stopped
```

---
## ❗ Important Note

1️⃣ **Annotation Support:** `@PostConstruct` and `@PreDestroy` come from **Jakarta EE** (`jakarta.annotation`). Ensure you have the required dependency in `pom.xml`:
```xml
<dependency>
    <groupId>jakarta.annotation</groupId>
    <artifactId>jakarta.annotation-api</artifactId>
    <version>2.0.0</version>
    <scope>provided</scope>
</dependency>
```



---
## 🧐 FAQs

### ❓ Can I have multiple `@PostConstruct` or `@PreDestroy` methods?
❌ No. Each bean should have only **one** `@PostConstruct` and `@PreDestroy` method. If multiple are declared, Spring will throw an error.

### ❓ What happens if I don't use `@PreDestroy`?
If you don’t manually clean up resources, you **risk memory leaks** (especially in database connections, sockets, or file handlers).

### ❓ How is `@PostConstruct` different from the constructor?
- The **constructor** runs **before dependencies are injected**.
- `@PostConstruct` runs **after** dependencies are injected and the bean is ready.

### ❓ What if I need multiple initialization steps?
Use `@PostConstruct` for general setup, and create separate methods for additional initialization.

---
## 🎯 Conclusion

✅ `@PostConstruct` ensures **proper initialization** after dependency injection.
✅ `@PreDestroy` helps in **resource cleanup** before a bean is destroyed.



