# 📚 Understanding Advice in Spring AOP

## 🚀 What is Advice in AOP?
**Advice** in Aspect-Oriented Programming (AOP) defines **what action should be taken** and **when it should be executed** at a specific point in the application.

In simple terms, **Advice** is the logic that gets executed **before, after, or around** a method execution, based on the applied aspect.

### 👉 Real-World Analogy
Think of Advice as **security checks at an airport**:
- Before boarding, security checks your luggage (**@Before Advice**)
- After landing, security may verify passengers again (**@After Advice**)
- If something suspicious is found, security takes action (**@AfterThrowing Advice**)
- If everything is fine, passengers exit smoothly (**@AfterReturning Advice**)
- Some security checks wrap the entire process (**@Around Advice**)

---

## 🔑 Types of Advice in Spring AOP
Spring AOP provides **five** types of Advice:

### 1️⃣ **@Before Advice** - Runs Before a Method Execution

**Definition:** Executes **before** the target method runs.

**Use Case:** Logging method execution, validation checks.

**Example:**
```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.UserService.*(..))")
    public void logBeforeMethod() {
        System.out.println("[LOG] Method execution started...");
    }
}
```
**Output:**
```
[LOG] Method execution started...
```

---

### 2️⃣ **@After Advice** - Runs After a Method Execution

**Definition:** Executes **after** the method completes (whether it succeeds or fails).

**Use Case:** Cleaning up resources, logging method completion.

**Example:**
```java
@After("execution(* com.example.service.UserService.*(..))")
public void logAfterMethod() {
    System.out.println("[LOG] Method execution completed.");
}
```
**Output:**
```
[LOG] Method execution completed.
```

---

### 3️⃣ **@AfterReturning Advice** - Runs After a Successful Method Execution

**Definition:** Executes **only if** the method successfully returns a value.

**Use Case:** Logging return values, modifying results.

**Example:**
```java
@AfterReturning(pointcut = "execution(* com.example.service.UserService.getUser(..))", returning = "result")
public void logReturnValue(Object result) {
    System.out.println("[LOG] Method returned: " + result);
}
```
**Output (if method returns "John"):**
```
[LOG] Method returned: John
```

---

### 4️⃣ **@AfterThrowing Advice** - Runs When a Method Throws an Exception

**Definition:** Executes **only if** the method throws an exception.

**Use Case:** Exception logging, error tracking.

**Example:**
```java
@AfterThrowing(pointcut = "execution(* com.example.service.UserService.*(..))", throwing = "ex")
public void logException(Exception ex) {
    System.out.println("[ERROR] Exception occurred: " + ex.getMessage());
}
```
**Output (if exception occurs):**
```
[ERROR] Exception occurred: Null Pointer Exception
```

---

### 5️⃣ **@Around Advice** - Wraps Around the Target Method

**Definition:** Runs **before and after** the method execution. Can modify arguments or return values.

**Use Case:** Performance monitoring, transaction management.

**Example:**
```java
@Around("execution(* com.example.service.UserService.*(..))")
public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object result = joinPoint.proceed(); // Executes the actual method
    long end = System.currentTimeMillis();
    System.out.println("[LOG] Execution time: " + (end - start) + "ms");
    return result;
}
```
**Output:**
```
[LOG] Execution time: 10ms
```

---

## 📈 Summary Table of Advice Types

| Advice Type        | Execution Time   | Use Case |
|--------------------|----------------|------------------------|
| **@Before**       | Before method   | Logging, validation |
| **@After**        | After method    | Cleanup, logging |
| **@AfterReturning** | After success  | Return value logging |
| **@AfterThrowing** | On Exception   | Error tracking |
| **@Around**       | Before & After  | Performance tracking |

---

## 🚀 Conclusion
Advice in Spring AOP allows us to **intercept method execution** at different points, making applications more modular and maintainable.

Next, we'll see **how to combine multiple Advices effectively in a single Aspect!** 🚀

