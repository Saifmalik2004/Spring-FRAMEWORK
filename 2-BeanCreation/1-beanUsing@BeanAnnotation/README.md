# Creating a Bean Using @Bean Annotation and ApplicationContext

In this guide, we will learn how to create a **Spring Bean** using the `@Bean` annotation and manage it using `ApplicationContext`.

---
## 📌 Prerequisites
Before we begin, ensure that you have:
- Java JDK 17+ installed
- Apache Maven installed
- Visual Studio Code (or any IDE) installed
- Basic knowledge of Java

---
## 🏗️ Project Structure
```
SpringBeanDemo/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── AppConfig.java
│   │   │       ├── HelloService.java
│   │   │       ├── App.java
│── pom.xml
```

---
## Step 1️⃣: Create a Maven Project
If you haven't already set up a Maven project, follow these steps:
1. Open a terminal and run:
   ```sh
   mvn archetype:generate -DgroupId=com.example.demo -DartifactId=SpringBeanDemo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
   ```
2. Navigate to the project directory:
   ```sh
   cd SpringBeanDemo
   ```

---
## Step 2️⃣: Add Spring Context Dependency
Open the `pom.xml` file and add the following dependency inside `<dependencies>`:
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```
Run the following command to download dependencies:
```sh
mvn clean install
```

---
## Step 3️⃣: Create a Bean Using `@Bean` Annotation
### ➤ Create a Service Class
Create a new file `HelloService.java` inside `src/main/java/com/example/demo/`:
```java
package com.example.demo;

public class HelloService {
    public String sayHello() {
        return "Hello, Spring Bean!";
    }
}
```

### ➤ Define a Configuration Class
Create `AppConfig.java` inside `src/main/java/com/example/demo/`:
```java
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public HelloService helloService() {
        return new HelloService();
    }
}
```

Here, `@Configuration` tells Spring that this class provides bean definitions, and `@Bean` registers the `HelloService` as a bean.

---
## Step 4️⃣: Use `ApplicationContext` to Access the Bean
Now, create `App.java` inside `src/main/java/com/example/demo/`:
```java
package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        // Create application context
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Retrieve the bean
        HelloService helloService = context.getBean(HelloService.class);
        
        // Use the bean
        System.out.println(helloService.sayHello());
    }
}
```

---
## Step 5️⃣: Run the Application
To run the application, execute:
```sh
mvn exec:java -Dexec.mainClass=com.example.demo.App
```
You should see the output:
```
Hello, Spring Bean!
```

---
## 🎯 Conclusion
✅ We successfully created a **Spring Bean** using the `@Bean` annotation.
✅ We used `ApplicationContext` to manage the bean lifecycle.
✅ We built and executed a Spring application step by step.

# 🚨 Handling `NoUniqueBeanDefinitionException` in Spring  

## ⚠️ The Problem: Multiple Beans of the Same Type  

In Spring, if you define multiple beans of the **same type** and try to retrieve one **without specifying a name**, Spring will get confused and throw an error.  

### ❌ Example: Defining Multiple Beans of the Same Type  

```java
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public HelloService service1() {
        return new HelloService("Hello from Service 1!");
    }

    @Bean
    public HelloService service2() {
        return new HelloService("Hello from Service 2!");
    }
}
```

Now, if we try to retrieve a bean **without specifying a name**, like this:  

```java
ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
HelloService helloService = context.getBean(HelloService.class); // ❌ ERROR
```

Spring will throw this exception:  

```
org.springframework.beans.factory.NoUniqueBeanDefinitionException: 
No qualifying bean of type 'com.example.demo.HelloService' available: 
expected single matching bean but found 2: service1, service2
```

This happens because **Spring finds two beans (`service1` and `service2`) of type `HelloService` and doesn’t know which one to use.**  

---

## ✅ Solution 1: Specify the Bean Name  

To solve this issue, we can **pass the bean name as the first parameter** in `getBean()`.  

```java
HelloService helloService = context.getBean("service1", HelloService.class);
System.out.println(helloService.sayHello()); // Output: Hello from Service 1!
```

Here, we explicitly tell Spring **which bean to retrieve**, avoiding the exception.

---

## ✅ Solution 2: Use `@Primary` to Set a Default Bean  

If you want Spring to **automatically** select a default bean when there are multiple, you can use `@Primary`. **However, you can add `@Primary` to only one bean; otherwise, Spring will throw an error.**  

```java
@Configuration
public class AppConfig {
    @Bean
    @Primary  // 👈 This bean will be selected by default
    public HelloService service1() {
        return new HelloService("Hello from Service 1!");
    }

    @Bean
    public HelloService service2() {
        return new HelloService("Hello from Service 2!");
    }
}
```

Now, if we call:  

```java
HelloService helloService = context.getBean(HelloService.class);
System.out.println(helloService.sayHello()); // Output: Hello from Service 1!
```

Spring **automatically picks** `service1` because it has the `@Primary` annotation.  

---
Ohh got it bro! In Spring, there are **three ways** to assign a **custom name** to a bean using the `@Bean` annotation. Let's break it down properly!  

---

## 🔹Custom Name to a Bean in `@Bean`  

### ✅ **1. Using `@Bean("customName")`** (Shorter Syntax)  
This is a shorthand way to assign a **single name** to a bean.  

```java
@Bean("customService")
public HelloService helloService() {
    return new HelloService("Hello from Custom Bean!");
}
```
👉 **Retrieving the bean:**  
```java
HelloService service = (HelloService) context.getBean("customService");
```

---

### ✅ **2. Using `@Bean(name = "customName")`** (Explicit Naming)  
This is the standard and more explicit way of naming a bean.  

```java
@Bean(name = "customService")
public HelloService helloService() {
    return new HelloService("Hello from Named Bean!");
}
```
👉 **Retrieving the bean:**  
```java
HelloService service = (HelloService) context.getBean("customService");
```

---

### ✅ **3. Using `@Bean(value = "customName")`** (Alternative to `name`)  
The `value` attribute works the same as `name`, but it's less commonly used.  

```java
@Bean(value = "customService")
public HelloService helloService() {
    return new HelloService("Hello from Value Bean!");
}
```
👉 **Retrieving the bean:**  
```java
HelloService service = (HelloService) context.getBean("customService");
```

---

### 🎯 **Which One to Use?**
| Method | Usage |
|--------|-------|
| `@Bean("name")` | ✅ Short and clean, commonly used |
| `@Bean(name = "name")` | ✅ More explicit and readable |
| `@Bean(value = "name")` | ❌ Less common, but works the same as `name` |

🔥 **Pro Tip:** Stick to `@Bean("name")` or `@Bean(name = "name")` for better readability and consistency! 🚀


