# 🚀 Configuring Around Advice in Spring AOP (Step-by-Step)

## 🎯 Introduction
Aspect-Oriented Programming (AOP) helps us apply cross-cutting concerns like **logging, security, transaction management, etc.** without modifying business logic.

One of the most powerful AOP advices is **Around Advice**, which allows us to **execute code before and after** the target method.

### 🔍 What is Around Advice?
- Unlike `@Before` and `@After`, **`@Around` Advice gives full control** over the method execution.
- It allows **modifying input arguments, changing return values, or even preventing method execution**.
- Uses `ProceedingJoinPoint` to **proceed with the target method execution**.

In this guide, we will **configure Around Advice** in a simple **Spring (non-Boot) project** step by step! 🔥

---

## 🛠️ Step 1: Create a Simple Spring Project

1. **Create a new Maven project** in your IDE.
2. **Add the following dependencies** in `pom.xml`:

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

## 🛠️ Step 2: Create a Service Class (Target Method)
We will create a **UserService** class that contains the method to register a user.

📌 **Create `UserService.java` in `com.example.service` package:**

```java
package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String registerUser(String username) {
        System.out.println("User " + username + " is being registered...");
        return "User " + username + " registered successfully!";
    }
}
```

Here, `registerUser()` is the **target method** where we will apply `@Around` advice.

---

## 🛠️ Step 3: Create an Aspect with Around Advice
Now, let's create an **Aspect** that contains `@Around` advice to log execution time.

📌 **Create `ExecutionTimeAspect.java` in `com.example.aspect` package:**

```java
package com.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAspect {
    
    @Around("execution(* com.example.service.UserService.registerUser(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        Object result = joinPoint.proceed(); // Proceed with method execution
        
        long end = System.currentTimeMillis();
        System.out.println("[LOG] Execution time: " + (end - start) + "ms");
        
        return result;
    }
}
```

### 🔍 Breakdown:
✅ `@Aspect` → Marks this class as an Aspect.
✅ `@Component` → Registers this aspect as a Spring bean.
✅ `@Around("execution(* com.example.service.UserService.registerUser(..))")` → Intercepts `registerUser()` method.
✅ `ProceedingJoinPoint joinPoint` → Allows controlling method execution.
✅ `joinPoint.proceed()` → Calls the original method.
✅ Measures **execution time** and prints a log message.

---

## 🛠️ Step 4: Configure Spring with Java-Based Configuration
Instead of XML, we will use **pure Java-based configuration**.

📌 **Create `AppConfig.java` in `com.example.config` package:**

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

## 🏃️ Step 5: Run the Application
Now, let's test our AOP setup!

📌 **Create `MainApp.java`**:

```java
package com.example;

import com.example.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(com.example.config.AppConfig.class);
        UserService userService = context.getBean(UserService.class);
        
        String response = userService.registerUser("Saif");
        System.out.println(response);
    }
}
```

### 🔥 Expected Output:
```
User Saif is being registered...
[LOG] Execution time: 2ms
User Saif registered successfully!
```

🌟 **Success!**
- **Around Advice** measured execution time **before and after** method execution.
- `joinPoint.proceed()` executed the `registerUser()` method.
- We got **full control over the method execution**.

---

## 🔍 Summary
| Step  | Action |
|-------|--------|
| 1️⃣ | Create a Simple Spring Project |
| 2️⃣ | Add AOP Dependency in `pom.xml` |
| 3️⃣ | Create `UserService` (Target Class) |
| 4️⃣ | Create `ExecutionTimeAspect` with `@Around` Advice |
| 5️⃣ | Configure Spring AOP using Java-based configuration |
| 6️⃣ | Run the App and Test |

---

## 💡 Real-Life Use Cases of Around Advice
🔄 **Logging**: Log method execution time, method parameters, etc.
🔒 **Security**: Validate user permissions before executing a method.
🛠️ **Transaction Management**: Open a transaction before a method, commit or rollback after.

---

## 🔄 What's Next?
Now that we've implemented **Around Advice**, in the next README, we'll learn **Exception Handling Advice**! 🚀🔥

Stay tuned! 🎯

