# Understanding @Autowired with Multiple Beans & Avoiding Circular Dependencies in Spring

## 🌱 Introduction
Spring's `@Autowired` annotation simplifies dependency injection, but issues arise when multiple beans of the same type exist or when circular dependencies occur. In this guide, we will cover:

1. How `@Autowired` works when multiple beans of the same type are present.
2. How to specify which bean to inject.
3. What circular dependency is and how to avoid it.

---

## 🔹 `@Autowired` with Multiple Beans of the Same Type
### ✅ The Problem
When multiple beans of the same type exist, Spring does not know which one to inject. This results in the **`NoUniqueBeanDefinitionException`**.

### 🛠 Example
#### 1️⃣ Creating Multiple Beans of the Same Type
```java
package com.example;

public interface PaymentService {
    void processPayment();
}
```

```java
package com.example;
import org.springframework.stereotype.Service;

@Service
public class PayPalService implements PaymentService {
    public void processPayment() {
        System.out.println("Processing payment via PayPal");
    }
}
```

```java
package com.example;
import org.springframework.stereotype.Service;

@Service
public class StripeService implements PaymentService {
    public void processPayment() {
        System.out.println("Processing payment via Stripe");
    }
}
```

#### 2️⃣ Injecting `@Autowired` Without Specifying Bean Name
```java
package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentProcessor {
    @Autowired
    private PaymentService paymentService; // Ambiguity issue!

    public void makePayment() {
        paymentService.processPayment();
    }
}
```

### 🚨 Error: `NoUniqueBeanDefinitionException`
Since `PayPalService` and `StripeService` both implement `PaymentService`, Spring doesn’t know which one to inject.

---

## 🛠 Solutions
### 🔹 1. Use `@Qualifier` to Specify Bean Name
```java
@Component
public class PaymentProcessor {
    @Autowired
    @Qualifier("stripeService") // Specify which bean to use
    private PaymentService paymentService;

    public void makePayment() {
        paymentService.processPayment();
    }
}
```
✅ **This approach works when we need a specific implementation.**

---

### 🔹 2. Use `@Primary` for Default Bean Selection
```java
@Service
@Primary // Marks this bean as the default one
public class PayPalService implements PaymentService {
    public void processPayment() {
        System.out.println("Processing payment via PayPal");
    }
}
```
✅ **Now, if no `@Qualifier` is provided, Spring will inject `PayPalService` by default.**

---

## 🔥 Circular Dependency Problem
### ❌ The Issue
A circular dependency occurs when two or more beans depend on each other, creating an infinite loop.

### 🛠 Example of Circular Dependency
#### 1️⃣ Defining Two Interdependent Beans
```java
package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassA {
    @Autowired
    private ClassB classB;
}
```

```java
package com.example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClassB {
    @Autowired
    private ClassA classA;
}
```

🚨 **Spring will throw `BeanCurrentlyInCreationException` because it cannot resolve the dependencies.**

---

## 🛠 Solutions to Avoid Circular Dependencies
### 🔹 1. Use `@Lazy` to Delay Initialization
```java
@Component
public class ClassA {
    @Autowired
    @Lazy // Delays initialization until needed
    private ClassB classB;
}
```
✅ **This prevents immediate instantiation, breaking the cycle.**

---

### 🔹 2. Use Constructor Injection Instead of Field Injection
```java
@Component
public class ClassA {
    private final ClassB classB;

    @Autowired
    public ClassA(ClassB classB) {
        this.classB = classB;
    }
}
```

```java
@Component
public class ClassB {
    private final ClassA classA;

    @Autowired
    public ClassB(ClassA classA) {
        this.classA = classA;
    }
}
```
✅ **Spring can now resolve the dependencies better as it prioritizes constructor injection.**

---

### 🔹 3. Refactor Code to Remove Unnecessary Dependencies
**Instead of directly depending on each other, introduce a third class to manage interactions.**

```java
@Component
public class Mediator {
    private final ClassA classA;
    private final ClassB classB;

    @Autowired
    public Mediator(ClassA classA, ClassB classB) {
        this.classA = classA;
        this.classB = classB;
    }
}
```
✅ **Now, `ClassA` and `ClassB` no longer depend directly on each other.**

---

## 📌 Summary
| Problem | Solution |
|---------|----------|
| Multiple Beans of Same Type | Use `@Qualifier` or `@Primary` |
| Circular Dependency | Use `@Lazy`, Constructor Injection, or Refactor Code |

🚀 **Now you understand how `@Autowired` works with multiple beans and how to avoid circular dependencies in Spring!** Happy coding! 🎯

