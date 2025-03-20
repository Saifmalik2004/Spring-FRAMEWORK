# 🚀 Configuring After Returning & After Throwing Advice in Spring AOP (Step-by-Step)

## 🎯 Introduction
Spring AOP provides different types of advice to manage cross-cutting concerns. In this guide, we will explore **After Returning** and **After Throwing** advice:

- **After Returning Advice**: Executes **only if** a method completes successfully (returns a value).
- **After Throwing Advice**: Executes **only if** a method throws an exception.

We will configure both types of advice step by step using a **simple Spring project (not Spring Boot)** with **pure Java-based configuration**. 🔥

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
Let's create a **target class** where we will apply our AOP advice.

📌 Create `OrderService.java` in `com.example.service` package:

```java
package com.example.service;

import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String placeOrder(String item) {
        System.out.println("Order placed successfully for: " + item);
        return "Order Confirmed";
    }
    
    public String cancelOrder(String item) {
        System.out.println("Order cancellation failed for: " + item);
        throw new RuntimeException("Cancellation Error!");
    }
}
```

### 🔍 Breakdown:
✅ `placeOrder()` - A normal method that **returns a success message**.
✅ `cancelOrder()` - A method that **throws an exception**.

---

## ✨ Step 3: Create an Aspect Class
Now, let's create an **Aspect** that contains both `After Returning` and `After Throwing` advice.

📌 Create `LoggingAspect.java` in `com.example.aspect` package:

```java
package com.example.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    
    @AfterReturning(pointcut = "execution(* com.example.service.OrderService.placeOrder(..))", returning = "result")
    public void logAfterReturning(Object result) {
        System.out.println("[LOG] After Returning: Method executed successfully, result: " + result);
    }
    
    @AfterThrowing(pointcut = "execution(* com.example.service.OrderService.cancelOrder(..))", throwing = "ex")
    public void logAfterThrowing(Exception ex) {
        System.out.println("[LOG] After Throwing: Exception occurred - " + ex.getMessage());
    }
}
```

### 🔍 Breakdown:
✅ `@AfterReturning` → Executes **only if** the method returns successfully.
✅ `@AfterThrowing` → Executes **only if** the method throws an exception.
✅ `pointcut = "execution(* com.example.service.OrderService.placeOrder(..))"` → Targets `placeOrder()`.
✅ `returning = "result"` → Captures the returned value.
✅ `pointcut = "execution(* com.example.service.OrderService.cancelOrder(..))"` → Targets `cancelOrder()`.
✅ `throwing = "ex"` → Captures the thrown exception.

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

import com.example.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(com.example.config.AppConfig.class);
        OrderService orderService = context.getBean(OrderService.class);
        
        // Successful execution
        orderService.placeOrder("Laptop");
        
        // Exception case
        try {
            orderService.cancelOrder("Phone");
        } catch (Exception ignored) {}
    }
}
```

### 🔥 Expected Output:
```
Order placed successfully for: Laptop
[LOG] After Returning: Method executed successfully, result: Order Confirmed
Order cancellation failed for: Phone
[LOG] After Throwing: Exception occurred - Cancellation Error!
```
🎉 **Success!**
- **After Returning Advice** executed only when `placeOrder()` completed successfully.
- **After Throwing Advice** executed when `cancelOrder()` threw an exception.

---

## 📝 Difference Between After, After Returning & After Throwing
| Advice Type | When It Executes | Handles Exceptions? | Captures Return Value? |
|------------|----------------|-----------------|----------------|
| **After** | After method execution (both success & failure) | Yes | No |
| **After Returning** | After successful execution only | No | Yes |
| **After Throwing** | After method throws an exception | Yes | No |

---


Stay tuned! 🎯

