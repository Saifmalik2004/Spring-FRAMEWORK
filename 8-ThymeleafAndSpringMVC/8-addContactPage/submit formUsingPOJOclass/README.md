# **Handling Form Data in Spring Boot with POJO & Service Layer**  

In this guide, we'll learn how to **efficiently handle form data** in a Spring Boot application using:  
✅ **POJO (Plain Old Java Object) Class** to avoid multiple `@RequestParam` annotations.  
✅ **Service Layer** to separate business logic from the controller.  
✅ **Dependency Injection (`@Autowired`)** for cleaner and maintainable code.  

---

## **1️⃣ Problem with `@RequestParam`**  

Earlier, we handled form submission like this:  

```java
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
```

👉 **Issue:** The controller method has **too many parameters**, making it hard to manage and extend.

✅ **Solution:** We create a **POJO class (`Contact.java`)** to hold form data.

---

## **2️⃣ Creating the Model (`Contact.java`)**  

📌 **Location:** `src/main/java/com/example/demo/model/Contact.java`  

```java
package com.example.demo.model;

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

✅ **Why Use a POJO?**  
- **Avoids multiple `@RequestParam` annotations.**  
- **Encapsulates form data in a single object.**  
- **Easy to modify and extend** (e.g., adding new fields).  

---

## **3️⃣ Updating the Controller (`ContactController.java`)**  

📌 **Location:** `src/main/java/com/example/demo/controller/ContactController.java`  

```java
package com.example.demo.controller;

import com.example.demo.model.Contact;
import com.example.demo.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping(value = "/saveMsg")
    public String saveMessage(Contact contact) {
        contactService.saveMessageDetail(contact);
        return "redirect:/contact";
    }
}
```

✅ **Key Changes:**  
1. **Replaced multiple `@RequestParam` annotations** with a single `Contact` object.  
2. **Used `@Autowired` to inject `ContactService`**, following **Dependency Injection (DI)**.  
3. **Delegated business logic** to the `ContactService`, keeping the controller clean.  

---

## **4️⃣ Creating the Service Layer (`ContactService.java`)**  

📌 **Location:** `src/main/java/com/example/demo/service/ContactService.java`  

```java
package com.example.demo.service;

import com.example.demo.model.Contact;
import org.springframework.stereotype.Service;

@Service
public class ContactService {

    public boolean saveMessageDetail(Contact contact) {
        System.out.println(contact.toString());
        return true;
    }
}
```

✅ **Why Create a Service Layer?**  
- **Separates business logic** from the controller.  
- **Makes code reusable** (e.g., if we add database operations later).  
- **Improves maintainability and scalability.**  

---

## **5️⃣ Understanding `@Autowired` and Dependency Injection**  

### **What is `@Autowired`?**
- `@Autowired` is used to **inject dependencies automatically**.  
- Spring Boot creates an **instance of `ContactService`** and provides it to `ContactController`.  

```java
private final ContactService contactService;

@Autowired
public ContactController(ContactService contactService) {
    this.contactService = contactService;
}
```

### **Why Use Dependency Injection?**
- **Loose Coupling:** The controller **doesn't create an object manually**, making code flexible.  
- **Better Testability:** We can **mock the service layer** in unit tests.  

---

## **6️⃣ Summary of What We Have Done**  

✅ **Created a `Contact` POJO** to hold form data.  
✅ **Updated the controller** to accept `Contact` instead of multiple `@RequestParam`.  
✅ **Introduced a service layer** to separate logic from the controller.  
✅ **Used `@Autowired` for Dependency Injection**, making the code cleaner.  

---

## **7️⃣ Final Output in Terminal**  

When a user submits the form, we see this in the terminal:

```
Contact{name='Saif Khan', mobileNum='9876543210', email='saif@example.com', subject='Need Help', message='Hi, I have a question about Java'}
```

🚀 **Now our code is structured properly, making it easier to scale and maintain!** 🎯