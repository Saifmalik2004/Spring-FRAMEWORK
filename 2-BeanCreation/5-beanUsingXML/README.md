# Spring Framework - Creating Beans Using XML Configuration

## 📌 Introduction
Welcome to this hands-on guide on **Creating Beans using XML Configuration in Spring**! 🎉 In this tutorial, we will explore how to define and manage beans using **Spring's XML-based configuration** instead of annotations. This method was widely used before **Java-based configuration** (`@Configuration`) and is still relevant for legacy applications.

By the end of this guide, you will:
✔️ Understand how to define beans in an XML file.
✔️ Learn how to load XML-based configurations using **ApplicationContext**.
✔️ Manage dependencies using **constructor and setter injection**.
✔️ Explore real-world **examples** with proper code snippets.

---

## 🏗️ Project Structure
Before diving into the code, let's define a simple **Maven project structure**:

```
SpringXMLDemo/
│── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── HelloService.java
│   │   │       ├── App.java
│   │   ├── resources/
│   │   │   ├── applicationContext.xml
│── pom.xml
```

---

## 🛠️ Step 1: Create a Maven Project
If you haven’t set up a Maven project, open a terminal and run:
```sh
mvn archetype:generate -DgroupId=com.example.demo -DartifactId=SpringXMLDemo -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

Navigate to the project directory:
```sh
cd SpringXMLDemo
```

---

## 📦 Step 2: Add Spring Core Dependency
Modify the `pom.xml` file to include the **Spring Context** dependency:

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.23</version>
</dependency>
```

Then run:
```sh
mvn clean install
```

---

## ✍️ Step 3: Create a Simple Bean
We will create a **HelloService** class that returns a greeting message.

### ➤ `HelloService.java`
```java
package com.example.demo;

public class HelloService {
    private String message;
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void sayHello() {
        System.out.println(message);
    }
}
```

This class has a **setter method** to inject a message.

---

## 📄 Step 4: Define the Bean in `applicationContext.xml`
Now, create an XML file inside `src/main/resources/` named **`applicationContext.xml`** and define the bean configuration.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <!-- Defining a bean with setter injection -->
    <bean id="helloService" class="com.example.demo.HelloService">
        <property name="message" value="Hello, Spring with XML Configuration!"/>
    </bean>
</beans>
```

🔹 Here, **`<bean>`** defines the bean with an `id` and a `class` reference.  
🔹 **`<property>`** sets the value for the `message` field using **setter injection**.

---

## 🚀 Step 5: Load the XML Configuration in `App.java`
Now, we will use **ApplicationContext** to load the XML configuration and get the bean instance.

### ➤ `App.java`
```java
package com.example.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) {
        // Load XML Configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Retrieve bean
        HelloService helloService = (HelloService) context.getBean("helloService");
        
        // Call method
        helloService.sayHello();
    }
}
```

🔹 **`ClassPathXmlApplicationContext("applicationContext.xml")`** loads the XML configuration.  
🔹 **`getBean("helloService")`** retrieves the bean by its **id**.  
🔹 The output should be:
```sh
Hello, Spring with XML Configuration!
```

---

## 🏗️ Step 6: Constructor Injection (Alternative Approach)
Instead of setter injection, we can use **constructor injection**.

### ➤ Modify `HelloService.java`
```java
package com.example.demo;

public class HelloService {
    private String message;
    
    public HelloService(String message) {
        this.message = message;
    }
    
    public void sayHello() {
        System.out.println(message);
    }
}
```

### ➤ Update `applicationContext.xml`
```xml
<bean id="helloService" class="com.example.demo.HelloService">
    <constructor-arg value="Hello from Constructor Injection!"/>
</bean>
```

---

## ❓ FAQs

### 1️⃣ What if I define multiple beans of the same type?
If multiple beans of the same class exist and you try `context.getBean(HelloService.class)`, you will get:
```
NoUniqueBeanDefinitionException: No qualifying bean of type 'com.example.demo.HelloService' available
```
✅ **Solution:** Use `@Primary`, `@Qualifier`, or specify the bean name explicitly:
```java
HelloService helloService = (HelloService) context.getBean("helloService");
```

### 2️⃣ Can I define multiple beans with different IDs?
Yes! You can define multiple beans in `applicationContext.xml`:
```xml
<bean id="helloService1" class="com.example.demo.HelloService">
    <property name="message" value="Hello from Bean 1!"/>
</bean>
<bean id="helloService2" class="com.example.demo.HelloService">
    <property name="message" value="Hello from Bean 2!"/>
</bean>
```
Then, retrieve the specific bean by ID:
```java
HelloService service1 = (HelloService) context.getBean("helloService1");
HelloService service2 = (HelloService) context.getBean("helloService2");
```

---

## 🎯 Conclusion
✅ We learned how to **create Spring Beans using XML configuration**.  
✅ We explored **setter and constructor injection**.  
✅ We understood how to **load XML-based configuration using ApplicationContext**.  

🔗 **Next Steps:** Try adding dependencies between beans using `<ref bean="beanId" />`. 🚀 Happy Learning!

