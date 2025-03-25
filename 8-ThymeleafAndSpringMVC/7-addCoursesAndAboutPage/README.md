### **Understanding `th:href` in Thymeleaf**  
Thymeleaf provides `th:href` to dynamically generate URLs in Spring Boot applications. This replaces hardcoded links and ensures the URLs are correctly resolved based on the application's context.  

#### **Before Changes (Static Links)**
```html

<li class="nav-item">
    <a class="nav-link active" aria-current="page">Home</a>
</li>
<li class="nav-item">
    <a class="nav-link">Courses</a>
</li>
<li class="nav-item">
    <a class="nav-link">Contact</a>
</li>
```
- Here, the `<a>` tags are just plain text without a real link. Clicking them won’t navigate anywhere.

#### **After Changes (Using `th:href`)**
```html
 <a th:href="@{/home}" class="navbar-brand"><i class="fas fa-graduation-cap"></i>Education Hub 
 </a>
<li class="nav-item">
    <a th:href="@{/home}" class="nav-link active" aria-current="page">Home</a>
</li>
<li class="nav-item">
    <a th:href="@{/courses}" class="nav-link">Courses</a>
</li>
<li class="nav-item">
    <a th:href="@{/contact}" class="nav-link">Contact</a>
</li>
```
- `th:href="@{/home}"` → Generates the correct URL for the home page.
- `th:href="@{/courses}"` → Links to the new **Courses** page.
- `th:href="@{/contact}"` → Links to the **Contact** page.

Now, clicking on these links will navigate to the respective pages in the application.

---
2️⃣ Updating the Home Page (home.html)Modify the Why Choose Us section inside index.html to add links to the About page:
<!-- Why Choose Us Section -->
<section class="w3l-service-1 py-5">
    <div class="container py-md-5 py-4">
        <div class="title-main text-center mx-auto mb-md-5 mb-4" style="max-width:500px;">
            <p class="text-uppercase">Why Choose Us</p>
            <h3 class="title-style">Experienced Teachers And Safe Environment</h3>
        </div>
        <div class="row content23-col-2 text-center">
            <div class="col-md-6">
                <div class="content23-grid content23-grid1">
                    <h4><a th:href="@{/about}">Expert Teachers</a></h4>
                </div>
            </div>
            <div class="col-md-6 mt-md-0 mt-4">
                <div class="content23-grid content23-grid2">
                    <h4><a th:href="@{/about}">Safe Environment</a></h4>
                </div>
            </div>
        </div>
    </div>
</section>✅ Now, clicking Expert Teachers or Safe Environment navigates to /about.

### **Adding `courses.html` and `about.html` from `project_3` Folder in the `templates` Folder**  
Since Thymeleaf loads pages from the `templates` folder, we need to add the `courses.html` and `about.html` files from the `project_3` folder in this repository.

#### **File Structure**
```
src/main/resources/templates/
    ├── home.html
    ├── courses.html  <-- New File
    ├── about.html  <-- New File
    
```

#### **courses.html (Basic Structure)**
```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Education hub - Best Educational Institute for your Child</title>
    <!-- Google fonts -->
    <link href="//fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
    <!-- Template CSS Style link -->
    <link rel="stylesheet" href="assets/css/style-starter.css">
</head>

<body>

    <!-- header -->
    <div th:replace="~{header :: header}">...</div>
    <!-- //header -->

    <!-- inner banner -->
   
    <!-- //inner banner -->

    <!-- courses section -->
    

    <!-- footer block -->
    <div th:replace="~{footer :: footer}">...</div>
    <!-- //footer block -->
```
- This page will be displayed when we navigate to `/courses`.

---
### **Understanding and adding `WebConfig` in Detail (Instead of a Controller)**  

In a typical Spring Boot application, when we want to navigate to a page, we create a **controller** to handle the request and return the corresponding view. However, when a page is **static** (i.e., does not require backend data processing), we can configure Spring Boot to serve it directly without writing a controller.  

This is where `WebMvcConfigurer` comes in. It allows us to register simple view controllers that map URLs directly to view templates, **without needing a controller class**.  

---

## **Step-by-Step Breakdown of `WebConfig` Implementation**  

### **1️⃣ Creating `WebConfig.java`**  

In `com.learnwithsaif.project.config`, create a file named **`WebConfig.java`** and define the configuration as follows:  

```java
package com.learnwithsaif.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Marks this class as a Spring configuration class
public class WebConfig implements WebMvcConfigurer {  

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/courses").setViewName("courses");  
        registry.addViewController("/about").setViewName("about");  
    }
}
```

---

### **2️⃣ Explanation of the Code**  

| **Code** | **Explanation** |
|----------|---------------|
| `@Configuration` | This annotation tells Spring that this class is a configuration class that will modify the application's behavior. |
| `implements WebMvcConfigurer` | This interface provides methods to configure Spring MVC without extending the default configuration. |
| `addViewControllers(ViewControllerRegistry registry)` | This method registers view controllers for handling simple page navigation. |
| `registry.addViewController("/courses").setViewName("courses");` | Maps `/courses` URL to `courses.html` inside the `templates/` folder. |
| `registry.addViewController("/about").setViewName("about");` | Maps `/about` URL to `about.html`, serving it without needing a controller. |

---

## **3️⃣ Why Use `WebConfig` Instead of a Controller?**  

✅ **For Static Pages** – If a page does not need backend data (like a database or dynamic content), creating a controller is unnecessary.  
✅ **Reduces Boilerplate Code** – We avoid writing extra controller classes for simple page rendering.  
✅ **Better Performance** – Spring Boot directly maps the URL to the HTML file without unnecessary processing, making the application faster.  
✅ **Cleaner Project Structure** – Keeps the project organized by handling static page navigation separately from dynamic data handling.  

---

## **4️⃣ How This Works Internally?**  

1. When the user visits `http://localhost:8080/courses`, Spring Boot looks at `WebConfig` and sees that `"/courses"` is mapped to `"courses"`.  
2. Instead of looking for a controller, Spring Boot directly loads `courses.html` from the `templates/` folder.  
3. The user is served the **Courses page**, without any backend logic.  

---

## **5️⃣ File Structure After These Changes**  

```
src/main/java/com/learnwithsaif/project/config/
   ├── WebConfig.java  <-- Registers static pages
src/main/resources/templates/
   ├── home.html
   ├── header.html
   ├── footer.html
   ├── courses.html  <-- New static page
   ├── about.html  <-- New static page
  
```

---
### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/8-ThymeleafAndSpringMVC/10-holidayPage/project_3/)** 

If you just need the updated HTML files, you can copy and paste the code from the **`src`** directory.

---
### **Final Summary**
✅ Updated `th:href` in the navbar and why choose us section to enable navigation.  
✅ Added `courses.html`and `about.html` in `templates` for the new pages.  
✅ Used `WebConfig` to register static pages without a controller.  

Now, when you click on **Courses** in the navbar, the application correctly navigates to `/courses` without needing a backend controller. 🚀