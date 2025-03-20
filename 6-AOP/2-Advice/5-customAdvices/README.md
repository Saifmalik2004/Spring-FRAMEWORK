# 🚀 Alternative Approach: Custom Annotations & Ordered Execution in Spring AOP

## 🎯 Introduction
Spring AOP provides built-in annotations like `@Before`, `@After`, and `@Around` to apply cross-cutting concerns. However, in some cases, we may want more control and flexibility.

This guide covers:
✅ Creating **custom AOP annotations** with `@Retention` and `@Target`.
✅ Using `@Order` to **control execution order** when multiple aspects exist.
✅ A **full example project** demonstrating these concepts.
✅ **Detailed monitoring of method execution**.

---

# 🏗 Step 1: Creating a Custom Annotation for Logging
Instead of using `@Before`, we will create a custom annotation `@LogExecution`.

### 📌 Define the Custom Annotation
Create `LogExecution.java` inside `com.example.annotations`:

```java
package com.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  // Can be used on methods only
@Retention(RetentionPolicy.RUNTIME)  // Available at runtime for AOP processing
public @interface LogExecution {}
```

✅ `@Target(ElementType.METHOD)`: Applies to methods only.  
✅ `@Retention(RetentionPolicy.RUNTIME)`: Ensures annotation is available during runtime.

---

# 🏗 Step 2: Creating an Aspect That Uses the Custom Annotation
We create an **aspect** that triggers before methods marked with `@LogExecution`.

### 📌 Define LoggingAspect
Create `LoggingAspect.java` inside `com.example.aspect`:

```java
package com.example.aspect;

import com.example.annotations.LogExecution;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)  // Ensures this runs first if multiple aspects exist
public class LoggingAspect {

    @Around("@annotation(logExecution)")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint, LogExecution logExecution) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[LOG] Starting execution of method: " + methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Executes the actual method
        long endTime = System.currentTimeMillis();

        System.out.println("[LOG] Execution completed for: " + methodName + " in " + (endTime - startTime) + "ms");
        return result;
    }
}
```

✅ `@Aspect`: Marks this class as an AOP aspect.  
✅ `@Around("@annotation(logExecution)")`: Runs around any method annotated with `@LogExecution`.  
✅ `joinPoint.proceed()`: Calls the actual method and captures its return value.  
✅ `@Order(1)`: Ensures it executes **before other aspects**.

---

# 🏗 Step 3: Applying the Custom Annotation in a Service
Now, we use `@LogExecution` inside a service class.

### 📌 Define UserService
Create `UserService.java` inside `com.example.service`:

```java
package com.example.service;

import com.example.annotations.LogExecution;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @LogExecution  // Triggers the LoggingAspect
    public void registerUser(String username) {
        System.out.println("User " + username + " registered successfully!");
    }
}
```

✅ When `registerUser()` is called, `LoggingAspect` logs the method execution.

---

# 🏗 Step 4: Configuring Spring AOP
We must **enable AOP** for our annotation to work.

### 📌 Define AppConfig
Create `AppConfig.java` inside `com.example.config`:

```java
package com.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy  // Enables AOP support
@ComponentScan(basePackages = "com.example")
public class AppConfig {}
```

✅ `@EnableAspectJAutoProxy`: Enables AOP functionality.  
✅ `@ComponentScan("com.example")`: Scans the package for components and aspects.

---

# 🏗 Step 5: Running the Application

### 📌 Define Main Class
Create `MainApp.java` inside `com.example`:

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

### 🔥 Expected Output
```
[LOG] Starting execution of method: registerUser
User Saif registered successfully!
[LOG] Execution completed for: registerUser in 5ms
```

🎉 **Success!** Our `@LogExecution` annotation triggered the logging aspect.

---

# 📌 Summary
| Feature | Description |
|---------|------------|
| `@Retention(RUNTIME)` | Ensures custom annotations are available at runtime for AOP. |
| `@Target(ElementType.METHOD)` | Restricts annotation usage to methods only. |
| `@annotation(customAnnotation)` | Used inside aspects to target methods with a specific annotation. |
| `@Order(n)` | Defines execution order for multiple aspects (`n=1` runs first). |

---

# 🎯 Conclusion
✅ We learned an **alternative approach** to configuring AOP in Spring.  
✅ We created a **custom annotation** (`@LogExecution`) instead of using built-in ones.  
✅ We controlled **aspect execution order** using `@Order`.  
✅ **Detailed monitoring** of method execution time was implemented.  
✅ This approach provides more **flexibility and cleaner code organization**.  

🚀 Now you can use **custom AOP annotations and ordered execution** in your Spring projects! 🔥

