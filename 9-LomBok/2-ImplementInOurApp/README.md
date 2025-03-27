# **Using Lombok to Simplify Holiday and Contact Models in Java**

## **Changes in Holiday and Contact Models**
We have optimized our `Holiday` and `Contact` models using Lombok to remove unnecessary boilerplate code like getters, setters, and `toString()` methods. Below, we explain what changes were made and how Lombok helps.

---

## **Changes in the `Holiday` Model**

### **Step 1: Add Lombok Dependency (For Maven Projects)**
Add this in `pom.xml`:
```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <scope>provided</scope>
</dependency>
```
### **Previous Approach**
Before using Lombok, if we had manually written getters, setters, and constructors, our class would have looked like this:

```java
package com.example.demo.model;

public class Holiday {
    private final String day;
    private final String reason;
    private final Type type;

    // Constructor
    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }

    // Getters
    public String getDay() { return day; }
    public String getReason() { return reason; }
    public Type getType() { return type; }

    @Override
    public String toString() {
        return "Holiday{" +
                "day='" + day + '\'' +
                ", reason='" + reason + '\'' +
                ", type=" + type +
                '}';
    }

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
```
### **Optimized Approach with Lombok**
Now, we use Lombok's `@Data` annotation, which automatically generates:
- **Getters**
- **`toString()`**
- **`equals()` and `hashCode()`**
- **A required constructor for `final` fields**

```java
package com.example.demo.model;

import lombok.Data;

@Data
public class Holiday {
    private final String day;
    private final String reason;
    private final Type type;

    public enum Type {
        FESTIVAL, FEDERAL
    }
}
```
### **Benefits of This Change**
✅ **No need to manually define getters and `toString()`**  
✅ **Reduces code length and improves readability**  
✅ **Automatically generates the required constructor for `final` fields**  

---

## **Changes in the `Contact` Model**
Similarly, we optimized the `Contact` model.

### **Previous Approach**
Before using Lombok, the `Contact` model contained unnecessary boilerplate code:

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

### **Optimized Approach with Lombok**
With Lombok, the `Contact` class is much cleaner:

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

### **Benefits of This Change**
✅ **Removes unnecessary getter, setter, and `toString()` methods**  
✅ **Automatically generates all required methods**  
✅ **Makes the class cleaner and easier to maintain**  

---

## **Summary of Changes**
| Model  | Before Lombok | After Lombok |
|---------|-------------|--------------|
| **Holiday**  | Manually defined getters and constructor | Used `@Data`, removed boilerplate code |
| **Contact**  | Manually defined getters, setters, and `toString()` | Used `@Data`, reduced code length |


With Lombok, our code is **more readable**, **maintainable**, and **efficient**. 🚀