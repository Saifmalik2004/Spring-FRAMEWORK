# Bean Scope in Spring

## 🌱 Introduction
In Spring, **bean scope** defines the lifecycle and visibility of a bean in the Spring container. Different scopes allow us to control how objects are created, shared, or disposed of, making our application more efficient and scalable.

### 📌 Real-Life Analogy
Think of a **coffee shop**:
- **Singleton Scope**: The coffee shop has **one coffee machine** that all baristas share.
- **Prototype Scope**: Every customer gets their **own personal coffee cup**.
- **Request Scope**: In a restaurant, every customer gets **a separate order slip**, valid only during their visit.
- **Session Scope**: A customer has **a membership card**, which lasts throughout their stay but is discarded once they leave.

Spring provides **five built-in scopes**, but for now, we will focus on the first two:
1. **Singleton** (Default) – One instance per container.
2. **Prototype** – New instance every time.

We will learn about **Request, Session, and Application scopes** later when we discuss **HTTP requests**. 🚀

---

## 🔹 Types of Bean Scopes in Spring
### 🔹 Singleton Scope in Detail

#### 📌 What is Singleton Scope?
In **singleton scope**, Spring creates **only one instance** of the bean per Spring container. This instance is **shared** across the entire application, meaning all components that depend on this bean receive the **same instance**.

#### 🏪 Real-Life Analogy
Imagine a **coffee shop** where there is **one coffee machine** that all baristas use. No matter how many baristas are working, they all share the **same coffee machine**.

#### 🛠 How to Define a Singleton Bean?
By default, all beans in Spring have **singleton scope** unless explicitly specified otherwise.

```java
package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public CoffeeMachine coffeeMachine() {
        return new CoffeeMachine();
    }
}
```

#### 🔥 Verifying Singleton Behavior
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
CoffeeMachine cm1 = context.getBean(CoffeeMachine.class);
CoffeeMachine cm2 = context.getBean(CoffeeMachine.class);
System.out.println(cm1 == cm2); // true (Same instance)
```
Since `cm1` and `cm2` are the **same instance**, any change made to `cm1` will reflect in `cm2`.

---

### ⚠️ Race Condition in Singleton Beans
Since a singleton bean is **shared among multiple threads**, it can lead to **race conditions** in a multi-threaded environment.

#### ❌ Problem: Unsafe Shared State
```java
@Component
public class Counter {
    private int count = 0;
    
    public void increment() {
        count++;
    }
    
    public int getCount() {
        return count;
    }
}
```
- If multiple threads call `increment()` simultaneously, the value of `count` may become **inconsistent**.
- This is because **threads may override each other’s updates**.

#### ✅ Solution 1: Synchronization
We can make the `increment()` method **thread-safe** by synchronizing it.
```java
public synchronized void increment() {
    count++;
}
```

#### ✅ Solution 2: Prototype Scope (Avoid Shared State)
If each thread requires a **separate instance**, we can use `@Scope("prototype")`.

```java
@Bean
@Scope("prototype")
public Counter counter() {
    return new Counter();
}
```
Now, each call to `context.getBean(Counter.class)` will return a **new Counter instance**.

---

### ✅ Use Cases for Singleton Scope
Singleton scope is ideal when we need to **reuse a shared resource** across the application.

✔ **Database Connection Pools**
   - Creating new database connections every time is expensive.
   - A singleton **manages a pool of connections** for efficiency.

✔ **Logging Services**
   - A single logger instance ensures that **logs remain consistent** across all classes.

✔ **Caching Mechanisms**
   - Storing common data in a singleton avoids **repetitive calculations**.

✔ **Configuration Managers**
   - Global application settings are accessed via a **single instance**.

---

### 🚀 Summary
- **Singleton scope** creates **one instance per container**.
- It is efficient but can lead to **race conditions** in a multi-threaded environment.
- Used for **shared resources** like database pools, loggers, and caches.
- If thread safety is a concern, consider using **synchronization** or **prototype scope**.



### 2️⃣ Prototype Scope
A new bean instance is created **every time** it is requested.

```java
@Bean
@Scope("prototype")
public CoffeeCup coffeeCup() {
    return new CoffeeCup();
}
```

**Usage:**
```java
CoffeeCup cup1 = context.getBean(CoffeeCup.class);
CoffeeCup cup2 = context.getBean(CoffeeCup.class);
System.out.println(cup1 == cup2); // false (Different instances)
```
✔ **Good for independent, stateful beans** ❌ **Memory overhead due to multiple instances**

---


We will explore **Request, Session, and Application scopes** when we dive into **HTTP request handling** in Spring. Stay tuned! 🚀

### 🔥 Lazy vs. Eager Instantiation in Singleton Beans  

#### 📌 What is Instantiation in Singleton Beans?  
When a singleton bean is created, it is instantiated **only once** per Spring container. However, we can control **when** this instantiation happens using **lazy and eager instantiation**.

---

### 1️⃣ **Eager Instantiation (Default Behavior)**  
By default, Spring follows **eager instantiation**, meaning all singleton beans are created **at the time of application startup**.  

#### 🏪 Real-Life Analogy  
Imagine a **hotel kitchen** where all chefs are assigned tasks **before guests arrive**. Even if some chefs are not needed immediately, they are still **ready** in the kitchen.  

#### 🛠 Example of Eager Instantiation  
```java
@Component
public class EagerBean {
    public EagerBean() {
        System.out.println("EagerBean is created at startup!");
    }
}
```
✔ **Advantage:** Ensures that beans are available when needed.  
❌ **Disadvantage:** Increases startup time, even if some beans are never used.  

---

### 2️⃣ **Lazy Instantiation (On-Demand Bean Creation)**  
Lazy instantiation delays the creation of a singleton bean **until it is first accessed**.  

#### 🏪 Real-Life Analogy  
Imagine a **hotel room heater** that turns on **only when a guest enters** instead of running all the time.  

#### 🛠 How to Enable Lazy Instantiation?  
We can use `@Lazy` to make a bean lazily initialized.  

```java
@Component
@Lazy
public class LazyBean {
    public LazyBean() {
        System.out.println("LazyBean is created only when accessed!");
    }
}
```

Now, if we **never call** `context.getBean(LazyBean.class)`, the bean **won't be created at all**.  

#### 🔥 Example of Lazy Instantiation in Action  
```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
System.out.println("Application started!");

// Bean is not created yet
LazyBean lb = context.getBean(LazyBean.class); // Now it's created
```
✔ **Advantage:** Reduces startup time and saves memory.  
❌ **Disadvantage:** Can cause a slight delay when the bean is first accessed.  

---

### 🏆 When to Use Lazy vs. Eager Instantiation?  
| Instantiation Type | When Bean is Created | Best Use Case |
|-------------------|-------------------|--------------|
| **Eager (Default)** | At application startup | Shared resources like database pools, loggers |
| **Lazy (`@Lazy`)** | When first accessed | Heavy objects that may not always be needed |

👉 **Use eager instantiation** when the bean is required immediately.  
👉 **Use lazy instantiation** for beans that may not always be used, **reducing startup time**.  
## 🔥 **Singleton vs. Prototype: Key Differences**  

| Feature         | Singleton Scope  | Prototype Scope |
|---------------|----------------|----------------|
| **Instance Creation** | Single instance per container | New instance per request |
| **Shared or Separate?** | Shared among all requests | Unique per request |
| **Memory Usage** | Low (only one instance) | High (multiple instances) |
| **Best Use Case** | Shared resources (loggers, DB connections) | Stateful beans (temporary user data) |
| **Default Behavior?** | Yes | No (must explicitly define with `@Scope("prototype")`) |

---

## 🏆 **When to Use Singleton vs. Prototype?**  

✅ **Use Singleton** when:  
- You need a **shared object** like a database connection, logging service, or configuration manager.  
- The bean has **no state** that varies per request.  

✅ **Use Prototype** when:  
- You need **separate instances** for different users or processes.  
- The bean holds **user-specific data** or is stateful (e.g., form data).  

---

## ⚠️ **Common Pitfall: Injecting Prototype Bean in a Singleton**  
If a prototype bean is injected into a singleton bean, it behaves **like a singleton** (because the singleton keeps a reference to the first created instance).  

#### ❌ Wrong Way  
```java
@Component
public class CoffeeShop {
    private final CoffeeCup coffeeCup;

    @Autowired
    public CoffeeShop(CoffeeCup coffeeCup) {
        this.coffeeCup = coffeeCup;
    }

    public CoffeeCup getCup() {
        return coffeeCup;
    }
}
```
📌 **Issue**: Every time `getCup()` is called, it **returns the same CoffeeCup instance** instead of creating a new one!  

#### ✅ Correct Way (Using `ObjectFactory`)  
```java
@Component
public class CoffeeShop {
    @Autowired
    private ObjectFactory<CoffeeCup> coffeeCupFactory;

    public CoffeeCup getCup() {
        return coffeeCupFactory.getObject(); // New instance each time
    }
}
```

---

### 🎯 **Final Thought**  
- **Singleton = One shared instance** (like a coffee machine in an office).  
- **Prototype = New instance every time** (like a coffee cup for each customer).  
- **Injecting a prototype bean in a singleton requires careful handling**.  
