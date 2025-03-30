# Implementing Validation in a Spring Boot Application using Jakarta Bean Validation

## 1. Defining the Model with Validation Annotations

First, we define our `Contact` model class with the necessary validation annotations in `com.example.demo.model`:

```java
package com.example.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Contact {

    @NotBlank(message="Name must not be blank")
    @Size(min=3, message = "Name must be at least 3 characters long")
    private String name;
    
    @NotBlank(message="Mobile must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})", message="Mobile number must be 10 digits")
    private String mobileNum;
    
    @NotBlank(message="Email must not be blank")
    @Email(message="Please provide a valid email address")
    private String email;
    
    @NotBlank(message="Subject must not be blank")
    @Size(min=5, message = "Subject must be at least 5 characters long")
    private String subject;
    
    @NotBlank(message="Message must not be blank")
    @Size(min=10, message = "Message must be at least 10 characters long")
    private String message;
}
```

### Explanation of Annotations:
- `@NotBlank` → Ensures the field is not empty or only whitespace.
- `@Size(min=X, message=...)` → Enforces a minimum number of characters.
- `@Pattern(regexp=...)` → Ensures the mobile number is exactly 10 digits.
- `@Email` → Validates that the input is a properly formatted email.

## 2. Updating the Controller to Handle the Model

Modify `ContactController` to initialize the contact form:

```java
@RequestMapping("/contact")
public String displayContactPage(Model model) {
    model.addAttribute("contact", new Contact());
    return "contact.html";
}
```

### Explanation:
- `@RequestMapping("/contact")` → Maps the `/contact` URL to this method.
- `Model model` → Passes data to the view.
- `model.addAttribute("contact", new Contact())` → Initializes an empty `Contact` object to be used in the form.

## 3. Updating the HTML Form to Use Model Binding

### **Original Form (Without Binding):**
```html
<form th:action="@{/saveMsg}" method="post" class="signin-form">
    <div class="input-grids">
        <div class="row">
            <div class="col-sm-6">
                <input type="text" name="name" id="name" placeholder="Your Name" class="contact-input" />
            </div>
            <div class="col-sm-6">
                <input type="text" name="mobileNum" id="mobileNum" placeholder="Your Mobile Number" class="contact-input" />
            </div>
        </div>
        <input type="text" name="email" id="email" placeholder="Your Email" class="contact-input" />
        <input type="text" name="subject" id="subject" placeholder="Subject" class="contact-input" />
    </div>
    <div class="form-input">
        <textarea name="message" id="message" placeholder="Type your message here"></textarea>
    </div>
    <div class="text-start">
        <button class="btn btn-style btn-style-3">Send Message</button>
    </div>
</form>
```

### **Updated Form (With Model Binding and Error Handling):**
```html
<ul>
    <li class="alert alert-danger" role="alert"
        th:each="error : ${#fields.errors('contact.*')}" th:text="${error}" />
</ul>
<form th:action="@{/saveMsg}" method="post" class="signin-form" th:object="${contact}">
    <div class="input-grids">
        <div class="row">
            <div class="col-sm-6">
                <input type="text" th:field="*{name}" placeholder="Your Name" class="contact-input" />
            </div>
            <div class="col-sm-6">
                <input type="text" th:field="*{mobileNum}" placeholder="Your Mobile Number" class="contact-input" />
            </div>
        </div>
        <input type="text" th:field="*{email}" placeholder="Your Email" class="contact-input" />
        <input type="text" th:field="*{subject}" placeholder="Subject" class="contact-input" />
    </div>
    <div class="form-input">
        <textarea th:field="*{message}" placeholder="Type your message here"></textarea>
    </div>
    <div class="text-start">
        <button class="btn btn-style btn-style-3">Send Message</button>
    </div>
</form>
```
### **Changes & Explanation:**
- `th:object="${contact}"` → Binds the form to the `Contact` object.
- `th:field="*{name}"` → Automatically maps input values to the `Contact` model.
- `th:each="error : ${#fields.errors('contact.*')}"` → Loops through validation errors and displays them.

## 4. Updating the Controller to Handle Validation

### **Old Method (Without Validation):**
```java
@PostMapping(value="/saveMsg")
public String saveMessage(Contact contact){
    contactService.saveMessageDetail(contact);
    return "redirect:/contact";
}
```

### **Updated Method (With Validation):**
```java
@RequestMapping(value = "/saveMsg", method = POST)
public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors){

    if(errors.hasErrors()){
        log.error("Contact form validation failed due to: " + errors.toString());
        return "contact.html";
    }
    contactService.saveMessageDetails(contact);
    return "redirect:/contact";
}
```
### **Changes & Explanation:**
- `@Valid` → Enables Jakarta Bean Validation.
- `@ModelAttribute("contact")` → Binds form data to the `Contact` object.
- `Errors errors` → Captures validation errors.
- `if(errors.hasErrors())` → Checks for validation errors and redisplays the form with error messages if needed.

## 5. Running the Project
Now, when we run the project and enter invalid data, the form will display validation errors. If all fields are valid, the data will be saved successfully.

## Conclusion
This guide provides a step-by-step approach to implementing Jakarta Bean Validation in a Spring Boot application. By integrating server-side validation with form binding and error handling, we ensure a robust and secure input validation process.

