# 🚀 Configuring Before Advice in Spring AOP (Step-by-Step)

## 🎯 Introduction
Aspect-Oriented Programming (AOP) allows us to separate cross-cutting concerns from business logic. **Before Advice** is one of the most commonly used types of advice, as it allows us to execute some logic **before** a target method runs.

In this guide, we'll create a **simple Spring project (not Spring Boot)** and configure **Before Advice** step by step using **pure Java-based configuration**. 🔥

---

## 🏗️ Step 1: Create a Simple Spring Project
To get started, create a **basic Maven project** and add the necessary dependencies manually.

1. Create a new **Maven Project** in your IDE.
2. Add the following dependencies in `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.23</version>
    </dependency>
    
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aop</artifactId>
        <version>5.3.23</version>
    </dependency>
    
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.9.7</version>
    </dependency>
</dependencies>
```

Then, run:
```sh
mvn clean install
```

---

## 🏗️ Step 2: Create a Service Class
Let's create a **target class** that will have methods where we want to apply AOP.

📌 Create `UserService.java` in `com.example.service` package:

```java
package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public void registerUser(String username) {
        System.out.println("User " + username + " registered successfully!");
    }
}
```
Here, `registerUser()` is the **target method** where we want to apply our **Before Advice**.

---

## ✨ Step 3: Create an Aspect Class
We will now create an **Aspect** that contains a **Before Advice**.

📌 Create `LoggingAspect.java` in `com.example.aspect` package:

```java
package com.example.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.UserService.registerUser(..))")
    public void logBeforeMethod() {
        System.out.println("[LOG] A user is being registered...");
    }
}
```
### 🔍 Breakdown:
✅ `@Aspect` → Marks this class as an Aspect.
✅ `@Component` → Registers this aspect as a Spring bean.
✅ `@Before("execution(* com.example.service.UserService.registerUser(..))")` → Defines the **pointcut** where the advice should run.
   - `execution(* com.example.service.UserService.registerUser(..))` →
     - `*` → Matches any return type.
     - `com.example.service.UserService` → The target class.
     - `registerUser(..)` → The method where we apply AOP (with any number of parameters).
   - The advice runs **before** the method execution.

---

## 🏗️ Step 4: Configure Spring with Java-Based Configuration
Instead of XML, we will use **pure Java-based configuration**.

📌 Create `AppConfig.java` in `com.example.config` package:

```java
package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

### 🔍 Breakdown:
✅ `@Configuration` → Marks this as a configuration class.
✅ `@EnableAspectJAutoProxy` → Enables AOP proxy support in Spring.
✅ `@ComponentScan("com.example")` → Scans all beans (services, aspects, etc.) inside `com.example` package.

---

## 🏃‍♂️ Step 5: Run the Application
Now, let's test our AOP setup!

📌 Create `MainApp.java`:

```java
package com.example;

import com.example.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(com.example.config.AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        userService.registerUser("Saif");
    }
}
```

### 🔥 Expected Output:
```
[LOG] A user is being registered...
User Saif registered successfully!
```
🎉 **Success!** Before Advice executed **before** the `registerUser()` method.

---

## 📝 Summary
| Step  | Action |
|-------|--------|
| 1️⃣ | Create a Simple Spring Project |
| 2️⃣ | Add AOP Dependency in `pom.xml` |
| 3️⃣ | Create `UserService` (Target Class) |
| 4️⃣ | Create `LoggingAspect` (Aspect) with `@Before` Advice |
| 5️⃣ | Configure Spring AOP using Java-based configuration |
| 6️⃣ | Run the App and Test |

---

## 🔜 What's Next?
Now that we've implemented **Before Advice**, in the next README, we'll configure **After, Around, and Exception Advices**! 🚀🔥

Stay tuned! 🎯

