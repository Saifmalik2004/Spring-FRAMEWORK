### **Adding Contact Page with a Controller**  

Since the **Contact page** involves getting user data from a form, it's **not a static page**. Instead of handling it in `WebConfig`, we will create a **ContactController** to manage it.

---

### **1️⃣ Create `contact.html` in `templates/`**  

Inside `src/main/resources/templates/`, create a `contact.html` code will provided you can just copy and pate it  

---

### **2️⃣ Create `ContactController.java`**  

Since we need to process form data, we will **create a controller** instead of using `WebConfig`.  

📌 Inside `src/main/java/com/learnwithsaif/project/controller/`, create `ContactController.java`:  

```java
package com.learnwithsaif.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

    @RequestMapping("/contact")
    public String showContactForm() {
        return "contact"; // Returns contact.html from templates
    }

}
```

✅ **`@RequestMapping("/contact")`** → Loads the contact page when the user visits `/contact`.  

### **How the Contact Form Submits Data Using `POST` Request**  

In your `contact.html` file, we have a **form** that sends user input data to the backend when submitted:  

```html
<form th:action="@{/saveMsg}" method="post" class="signin-form">
    <div class="input-grids">
        <div class="row">
            <div class="col-sm-6">
                <input type="text" id="name" name="name" placeholder="Your Name" class="contact-input" />
            </div>
            <div class="col-sm-6">
                <input type="text" id="mobile" name="mobile" placeholder="Your Mobile Number" class="contact-input" />
            </div>
        </div>
        <input type="text" id="email" name="email" placeholder="Your Email" class="contact-input" />
        <input type="text" id="subject" name="subject" placeholder="Subject" class="contact-input" />
    </div>
    <div class="form-input">
        <textarea id="message" name="message" placeholder="Type your message here"></textarea>
    </div>
    <div class="text-start">
        <button class="btn btn-style btn-style-3">Send Message</button>
    </div>
</form>
```

---

## **1️⃣ How the Form Sends Data**
- The form uses `th:action="@{/saveMsg}"`, which means when the **submit button** is clicked, it **sends a POST request** to `/saveMsg`.  
- The `method="post"` attribute ensures the **form data is sent securely** (instead of being exposed in the URL as with `GET` requests).  
- Each input field has a `name` attribute (e.g., `name="name"`, `name="mobile"`, etc.), which **binds the user input** to corresponding backend parameters.

---

## **2️⃣ Handling the Form Submission in `ContactController.java`**

Now, we create a method in `ContactController.java` to handle the `POST` request.

```java
package com.learnwithsaif.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {
    @GetMapping
    public String showContactForm() {
        return "contact"; // Returns contact.html from templates
    }

    @PostMapping(value = "/saveMsg")
    public ModelAndView saveMessage(
        @RequestParam String name,
        @RequestParam String mobileNum,
        @RequestParam String email,
        @RequestParam String subject,
        @RequestParam String message
    ) {
        System.out.println("Name : " + name);
        System.out.println("Mobile Number : " + mobileNum);
        System.out.println("Email : " + email);
        System.out.println("Subject : " + subject);
        System.out.println("Message : " + message);

        return new ModelAndView("redirect:/contact");
    }
}
```

---

## **3️⃣ Step-by-Step Explanation of `saveMessage` Method**

### **Step 1: `@PostMapping(value="/saveMsg")`**
- This tells Spring Boot that this method should be called **whenever a POST request is sent to `/saveMsg`**.
- It listens for form submissions from `contact.html`.

### **Step 2: `@RequestParam` Annotations**
- Each `@RequestParam` binds the **form field's name** to a Java method parameter.
- Example:  
  - `@RequestParam String name` → **Gets the value entered in the `name` input field**.
  - `@RequestParam String mobileNum` → **Gets the value from `mobile` input field**.

### **Step 3: Print User Input to the Console**
- The method **prints each field’s value** in the terminal using `System.out.println()`.
- This helps **debugging** to check if data is received correctly.

### **Step 4: Redirect User to Contact Page**
- `return new ModelAndView("redirect:/contact");`  
- After processing the form, the user is **redirected back to the contact page**.

---

## **4️⃣ What Happens When You Submit the Form?**
When you fill in the form and click "Send Message", the following steps occur:

✅ **Step 1**: The form **sends a `POST` request** to `/saveMsg`.  
✅ **Step 2**: Spring Boot **receives the request** and calls `saveMessage()` method.  
✅ **Step 3**: The method **extracts form values** and **prints them in the terminal**.  
✅ **Step 4**: The user **is redirected back to the contact page** (`/contact`).  

---

## **5️⃣ Example Console Output**
When a user submits the form, Spring Boot will log the following in the terminal:

```
Name : Saif Khan
Mobile Number : 9876543210
Email : saif@example.com
Subject : Need help with a course
Message : Hi, I have a question about the Java course.
```
### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/8-ThymeleafAndSpringMVC/10-holidayPage/project_3/)** 

If you just need the updated HTML files, you can copy and paste the code from the **`src`** directory.
