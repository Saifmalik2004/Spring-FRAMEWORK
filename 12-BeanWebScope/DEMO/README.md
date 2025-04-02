

## **Understanding Spring Bean Scopes with Demo** 🚀  

Spring provides multiple scopes for managing beans, and in web applications, the most commonly used ones are:  
- `@RequestScope`
- `@SessionScope`
- `@ApplicationScope`

We will **test how these scopes behave** with a counter variable in `ContactService`.  

---

## **1️⃣ Understanding the Code**
### **📌 Controller: Contact Form Submission**  
```java
@PostMapping(value="/saveMsg")
public String saveMessage(@Valid @ModelAttribute("contact") Contact contact, Errors errors) {
    if(errors.hasErrors()){
        System.out.println("Contact form validation failed due to : " + errors.toString());
        return "contact.html";
    }
    
    contactService.saveMessageDetail(contact);
    
    // Increment and print counter
    contactService.setCounter(contactService.getCounter() + 1);
    System.out.println("Number of times the Contact form is submitted : " + contactService.getCounter());

    return "redirect:/contact";
}
```
**🔍 What’s Happening?**  
- When the form is submitted, validation is checked.
- If validation passes, the message is saved.
- We **increment the counter (`counter++`) in `ContactService`** every time the form is submitted.
- The counter value is printed, allowing us to observe how it behaves under different scopes.

---

### **📌 Service: ContactService with Different Scopes**
```java
package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
// import org.springframework.web.context.annotation.RequestScope;
// import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.model.Contact;

@Service
// @RequestScope
// @SessionScope
@ApplicationScope
public class ContactService {
    private int counter = 0;
    
    public boolean saveMessageDetail(Contact contact) {
        System.out.println(contact.toString());
        return true;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
```
---

## **2️⃣ Testing Different Scopes**
Now, let’s change the **scope annotation** (`@ApplicationScope`, `@SessionScope`, `@RequestScope`) one by one and observe the output.

---

### **🟢 Using `@ApplicationScope` (Singleton for the Entire App)**
```java
@ApplicationScope
```
📌 **Behavior:**  
- The **same instance** of `ContactService` is used across the entire application.
- The `counter` **persists across all users and requests**.
- **Example Output:**
  ```
  Number of times the Contact form is submitted: 1
  Number of times the Contact form is submitted: 2
  Number of times the Contact form is submitted: 3
  ```
- Since the bean is shared globally, **the counter keeps increasing across different users and requests**.

✅ **Pros:**  
✔️ Good for storing application-wide data.  
✔️ Efficient as it creates a single instance.  

❌ **Cons:**  
❌ **Not suitable for per-user tracking.**  
❌ If multiple users submit the form, the counter will count all users together.  

🔹 **Use Case:**  
- When you need to store global settings or cache data.

---

### **🟡 Using `@SessionScope` (Unique for Each User Session)**
```java
@SessionScope
```
📌 **Behavior:**  
- A new instance of `ContactService` is created **for each user session**.
- The `counter` **persists only for one user** and resets for a new user.
- **Example Output (for one user):**
  ```
  Number of times the Contact form is submitted: 1
  Number of times the Contact form is submitted: 2
  ```
  (If another user logs in, their counter starts from **1** again.)

✅ **Pros:**  
✔️ Tracks actions per user without interference.  
✔️ Useful for user-specific data (e.g., cart, preferences).  

❌ **Cons:**  
❌ Takes more memory than `@ApplicationScope`.  
❌ Data resets when the user logs out.  

🔹 **Use Case:**  
- Maintaining a **user-specific session** like shopping carts or authentication details.

---

### **🔴 Using `@RequestScope` (New Instance for Every Request)**
```java
@RequestScope
```
📌 **Behavior:**  
- A **new `ContactService` instance is created for every request**.
- The `counter` **never persists** and is **reset on every request**.
- **Example Output:**
  ```
  Number of times the Contact form is submitted: 1
  Number of times the Contact form is submitted: 1
  Number of times the Contact form is submitted: 1
  ```
  (**Counter always stays at 1!**)

✅ **Pros:**  
✔️ **No shared state** between requests.  
✔️ Ensures thread safety in multi-threaded applications.  

❌ **Cons:**  
❌ **Cannot track data between multiple requests.**  
❌ **Not useful for session or global tracking.**  

🔹 **Use Case:**  
- Suitable for **stateless requests**, such as REST APIs where every request is independent.

---

## **3️⃣ Summary Table: When to Use Which Scope?**

| Scope             | Persistence Level | Shared Across Requests? | Best Use Case |
|------------------|------------------|------------------------|---------------|
| `@ApplicationScope` | Entire application | ✅ Yes | Storing global data, app-wide counters |
| `@SessionScope` | Per user session | ❌ No | User-specific data (shopping cart, user login state) |
| `@RequestScope` | Per request | ❌ No | Stateless operations, API calls |

---

## **4️⃣ Conclusion**
- **Use `@ApplicationScope`** when you need **global data persistence**.
- **Use `@SessionScope`** when you want to maintain user-specific data **across multiple requests**.
- **Use `@RequestScope`** when each request should be **completely independent**.

---

### **5️⃣ Next Steps**
Now that we've understood how different scopes behave, you can:
✅ **Test different scopes in your project**  
✅ **Choose the right scope based on your needs**  
✅ **Avoid issues like unwanted data sharing or memory overhead**

🎯 **Happy Coding!** 🚀