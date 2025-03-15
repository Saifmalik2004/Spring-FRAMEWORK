# Setting Up a Maven Java Project in VS Code

This guide explains how to create a Maven Java project in Visual Studio Code and add the `spring-context` dependency for Spring Framework.

## Prerequisites
- Install [Java JDK 17+](https://adoptopenjdk.net/)
- Install [Apache Maven](https://maven.apache.org/download.cgi)
- Install [Visual Studio Code](https://code.visualstudio.com/)
- Install the **Extension Pack for Java** in VS Code

## Step 1: Create a Maven Project
1. Open VS Code and go to **View > Command Palette** (`Ctrl + Shift + P`).
2. Search for **Maven: Create Maven Project** and select it.
3. Choose **maven-archetype-quickstart**.
4. Select the latest version.
5. Enter the required details:
   - **Group ID**: `com.example`
   - **Artifact ID**: `myapp`
   - **Package Name**: `com.example.myapp`
6. Select a folder where the project should be created.
7. Wait for the project setup to complete.

## Step 2: Open the Project in VS Code
- Navigate to the project directory and open it in VS Code:
  ```sh
  cd myapp
  code .
  ```

## Step 3: Add Spring Context Dependency
1. Open the `pom.xml` file.
2. Add the following dependency inside `<dependencies>`:
   ```xml
   <dependency>
       <groupId>org.springframework</groupId>
       <artifactId>spring-context</artifactId>
       <version>5.3.23</version>
   </dependency>
   ```
3. Save the file.

## Step 4: Download Dependencies
- Open the VS Code terminal and run:
  ```sh
  mvn clean install
  ```
  This will download all required dependencies and build the project.

## Step 5: Create a Sample Spring Application
1. Inside `src/main/java/com/example/myapp`, create a new Java class `AppConfig.java`:
   ```java
   package com.example.myapp;

   import org.springframework.context.annotation.Bean;
   import org.springframework.context.annotation.Configuration;

   @Configuration
   public class AppConfig {
       @Bean
       public String message() {
           return "Hello, Spring Context!";
       }
   }
   ```

2. Modify `App.java` to test the Spring context:
   ```java
   package com.example.myapp;

   import org.springframework.context.ApplicationContext;
   import org.springframework.context.annotation.AnnotationConfigApplicationContext;

   public class App {
       public static void main(String[] args) {
           ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
           String message = context.getBean(String.class);
           System.out.println(message);
       }
   }
   ```

## Step 6: Run the Application
- Execute the program using:
  ```sh
  mvn exec:java -Dexec.mainClass=com.example.myapp.App
  ```

You should see the output:
```
Hello, Spring Context!
```

## Conclusion
You have successfully created a Maven Java project in VS Code, added the `spring-context` dependency, and built a simple Spring application!

