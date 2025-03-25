### **Separation of Header and Footer in Thymeleaf**  

To improve maintainability and avoid repetition, we separate the **header** and **footer** from the main `home.html` file and place them into their own files. Thymeleaf allows us to include these components dynamically using `th:replace`.  

Even after this separation, our webpage will still **look exactly the same** as before, but the code will be more modular and easier to manage. 🚀  

---

## **Step 1: Move the Header and Footer Code to Separate Files**  

Instead of keeping all the code inside `home.html`, we **copy-paste** the header and footer sections into separate files and remove them from `home.html`.  

---

### **1. Create `header.html`**  
This file will contain only the **header** section of our website.  

#### 📌 **Steps:**  
✅ Copy the **header section** from `home.html`.  
✅ Paste it into a new file called `header.html`.  
✅ Remove the header code from `home.html`.  

#### **`header.html`**
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<body>
   <!-- header -->
    <header id="site-header" class="fixed-top">
        <div class="container">
            <nav class="navbar navbar-expand-lg navbar-light">
                <a class="navbar-brand"><i class="fas fa-graduation-cap"></i>Education Hub</a>
                <button class="navbar-toggler collapsed" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon fa icon-expand fa-bars"></span>
                    <span class="navbar-toggler-icon fa icon-close fa-times"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarScroll">
                    <ul class="navbar-nav ms-auto my-2 my-lg-0 navbar-nav-scroll">
                        <li class="nav-item"><a class="nav-link active">Home</a></li>
                        <li class="nav-item"><a class="nav-link">Courses</a></li>
                        <li class="nav-item"><a class="nav-link">Contact</a></li>
                    </ul>
                </div>
                <div class="cont-ser-position">
                    <nav class="navigation">
                        <div class="theme-switch-wrapper">
                            <label class="theme-switch" for="checkbox">
                                <input type="checkbox" id="checkbox">
                                <div class="mode-container">
                                    <i class="gg-sun"></i>
                                    <i class="gg-moon"></i>
                                </div>
                            </label>
                        </div>
                    </nav>
                </div>
            </nav>
        </div>
    </header>
    <!-- //header -->
</body>
</html>
```

---

### **2. Create `footer.html`**  
This file will contain only the **footer** section of our website.  

#### 📌 **Steps:**  
✅ Copy the **footer section** from `home.html`.  
✅ Paste it into a new file called `footer.html`.  
✅ Remove the footer code from `home.html`.  

#### **`footer.html`**
```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<body>
    <!-- footer block -->
    <footer class="w3l-footer-29-main">
        <div class="footer-29 pt-5 pb-4">
            <div class="container pt-md-4">
                <div class="row footer-top-29">
                    <div class="col-lg-4 col-md-6 footer-list-29">
                        <h6 class="footer-title-29">Contact Info </h6>
                        <p class="mb-2 pe-xl-5">Address : Education Hub, 10001, 5th Avenue, #06 lane street, NY - 62617.</p>
                        <p class="mb-2">Phone Number : <a href="tel:+1(21) 234 4567">+1(21) 673 4587</a></p>
                        <p class="mb-2">Email : <a href="mailto:info@educationhub.com">info@educationhub.com</a></p>
                    </div>
                    <div class="col-lg-2 col-md-3 col-6 footer-list-29">
                        <h6 class="footer-title-29">Quick Links</h6>
                        <ul>
                            <li><a>About Us</a></li>
                            <li><a>Courses</a></li>
                            <li><a>Become a Teacher</a></li>
                            <li><a>Contact Us</a></li>
                            <li><a>Career</a></li>
                        </ul>
                    </div>
                    <div class="col-lg-2 col-md-3 col-6 footer-list-29">
                        <h6 class="footer-title-29">Explore</h6>
                        <ul>
                            <li><a>Blog Posts</a></li>
                            <li><a>Privacy Policy</a></li>
                            <li><a>Contact Us</a></li>
                            <li><a>License & Uses</a></li>
                            <li><a>Courses</a></li>
                        </ul>
                    </div>
                    <div class="col-lg-4 col-md-6 col-sm-8 footer-list-29">
                        <h6 class="footer-title-29">Subscribe</h6>
                        <form action="#" class="subscribe d-flex" method="post">
                            <input type="email" name="email" placeholder="Email Address" required="">
                            <button class="button-style"><span class="fa fa-paper-plane" aria-hidden="true"></span></button>
                        </form>
                        <p class="mt-3">Subscribe to our mailing list and get updates to your email inbox.</p>
                    </div>
                </div>
                <p class="copy-footer-29 text-center pt-lg-2 mt-5 pb-2">
                    © 2023 Education Hub. All rights reserved. Design by 
                    <a href="https://w3layouts.com/" target="_blank">W3Layouts</a>
                </p>
            </div>
        </div>
    </footer>
    <!-- //footer block -->
</body>
</html>
```

---

## **Step 2: Update `home.html` to Use `th:replace`**  
Now, instead of writing the full header and footer in `home.html`, we **use Thymeleaf’s `th:replace`** to insert them dynamically.  

#### 📌 **Steps:**  
✅ Remove the header and footer code from `home.html`.  
✅ Use `th:replace` to dynamically include `header.html` and `footer.html`.  

#### **Updated `home.html`**
```html
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Education Hub - Best Educational Institute for your Child</title>
    <link href="//fonts.googleapis.com/css2?family=Ubuntu:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/style-starter.css">
</head>
<body>

    <!-- header -->
    <div th:replace="~{header :: header}"></div>
    <!-- //header -->


    <!-- banner and other all section -->
   
   

    <!-- footer block -->
    <div th:replace="~{footer :: footer}"></div>
    <!-- //footer block -->

</body>
</html>
```

---

## **Step 3: How `th:replace` Works**  
Thymeleaf’s `th:replace` dynamically replaces the `<div>` with the referenced file’s content.  

#### **Syntax:**
```html
<div th:replace="~{fileName :: fragmentName}"></div>
```
- `fileName` → The name of the file (without `.html` extension).  
- `fragmentName` → The specific fragment (section) to be included.  

**Example in Our Case:**  
```html
<div th:replace="~{header :: header}"></div> 
```
🔹 This replaces the `<div>` with the contents of `header.html`.  

```html
<div th:replace="~{footer :: footer}"></div> 
```
🔹 This replaces the `<div>` with the contents of `footer.html`.  

---
### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/8-ThymeleafAndSpringMVC/10-holidayPage/project_3/)** 

If you just need the updated HTML files, you can copy and paste the code from the **`src`** directory.

## **Step 4: Benefits of Using `th:replace`**  
✅ **Code Reusability** – The same header and footer can be used across multiple pages.  
✅ **Easier Maintenance** – Any update in `header.html` or `footer.html` reflects across all pages.  
✅ **Better Structure** – The code is now modular and easy to manage.  

💡 **Final Result?** Your webpage **looks exactly the same** but is now **cleaner and more efficient!** 🚀