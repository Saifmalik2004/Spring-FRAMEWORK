# **Using Lombok to Simplify the Contact Model in Java**

## **Problem Statement**
In our current Java project, we have a `Contact` model that defines various properties such as `name`, `mobileNum`, `email`, `subject`, and `message`. However, as seen in the following code:

```java
package com.learnwithsaif.project_3.model;

public class Contact {

    private String name;
    private String mobileNum;
    private String email;
    private String subject;
    private String message;

    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getMobileNum() { return mobileNum; }
    public void setMobileNum(String mobileNum) { this.mobileNum = mobileNum; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", mobileNum='" + mobileNum + '\'' +
                ", email='" + email + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
```
### **Issues with This Approach**
1. **Boilerplate Code** – We manually define getters, setters, and `toString()`, making the class unnecessarily long.
2. **Hard to Maintain** – If we add or remove fields, we must update the getter, setter, and `toString()` method.
3. **Code Redundancy** – Many classes in a project may require similar boilerplate code, leading to repetition.

---

## **How Lombok Solves This Problem**
[Lombok](https://projectlombok.org/) is a Java library that reduces boilerplate code by generating methods at compile time using annotations. It eliminates the need for writing explicit getters, setters, constructors, and `toString()`.

### **Refactored Code with Lombok**
```java
package com.learnwithsaif.project_3.model;

import lombok.Data;

@Data
public class Contact {
    private String name;
    private String mobileNum;
    private String email;
    private String subject;
    private String message;
}
```
### **How Lombok Improves Our Code**
- **Removes Boilerplate Code** – No need to write explicit getters, setters, or `toString()`.
- **Easy to Maintain** – Adding or removing fields does not require modifying methods.
- **Cleaner and Readable Code** – The class focuses only on defining fields.

---

## **Most Common Lombok Annotations**
Lombok provides various annotations to simplify Java development. Here are the most commonly used ones:

### **1. `@Data`**
- Generates **getters**, **setters**, **`toString()`**, **`equals()`**, and **`hashCode()`**.
- Best for **DTOs (Data Transfer Objects)**.

```java
import lombok.Data;

@Data
public class User {
    private String name;
    private String email;
}
```

---

### **2. `@Getter` and `@Setter`**
- Use these if you **only** want getter and setter methods.

```java
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private String productName;
    private double price;
}
```

---

### **3. `@ToString`**
- Generates a `toString()` method automatically.

```java
import lombok.ToString;

@ToString
public class Order {
    private int orderId;
    private String item;
}
```

---

### **4. `@NoArgsConstructor` and `@AllArgsConstructor`**
- `@NoArgsConstructor` → Creates a constructor **without parameters**.
- `@AllArgsConstructor` → Creates a constructor **with all parameters**.

```java
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private int age;
}
```

---

### **5. `@Builder`**
- Helps in **creating objects using the Builder pattern**.

```java
import lombok.Builder;

@Builder
public class Employee {
    private String name;
    private String department;
}
```
Usage:
```java
Employee emp = Employee.builder()
                       .name("Saif")
                       .department("IT")
                       .build();
```

---

## **How to Use Lombok in Your Project**
### **Step 1: Add Lombok Dependency (For Maven Projects)**
Add this in `pom.xml`:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```

### **Step 2: Enable Annotation Processing in IDE**
- **IntelliJ IDEA** → Settings → Build, Execution, Deployment → Compiler → Annotation Processors → Enable annotation processing.
- **Eclipse** → Install Lombok Plugin.

---

## **Conclusion**
Lombok makes Java development easier by removing repetitive boilerplate code. It provides powerful annotations like `@Data`, `@Getter`, `@Setter`, `@ToString`, `@NoArgsConstructor`, `@AllArgsConstructor`, and `@Builder`.  

By using Lombok, we can focus more on **business logic** instead of writing **redundant** getter, setter, and constructor methods. 🚀