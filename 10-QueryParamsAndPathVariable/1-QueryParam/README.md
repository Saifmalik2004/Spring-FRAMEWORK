

# **Getting Query Parameters in Spring Boot using `@RequestParam`**
In Spring Boot, we often need to pass data in the URL. One common way to do this is by using **query parameters**. In this guide, we'll cover:  
1. **What are Query Parameters?**  
2. **How to Pass Query Parameters in a URL**  
3. **Using `@RequestParam` in Spring Boot**  
4. **Handling Optional & Default Values**  
5. **Handling Multiple Query Parameters**  
6. **Handling List of Values in Query Params**  

---

## **1. What are Query Parameters?**
Query parameters are key-value pairs that are appended to a URL **after a `?` (question mark)**.  
They are used to send data from the client to the server in an HTTP request.

### **Syntax:**
```
https://example.com/api/users?name=John&age=25
```
- Here, `name` and `age` are **query parameters**.
- `?` starts the query parameters.
- `&` separates multiple query parameters.

---

## **2. How to Pass Query Parameters in a URL**
When making a request, you can pass query parameters in different ways:

### **Example URLs with Query Parameters:**
#### ✅ **Single Query Parameter:**
```
https://myapi.com/products?category=electronics
```
#### ✅ **Multiple Query Parameters:**
```
https://myapi.com/products?category=electronics&brand=Apple&price=1000
```
#### ✅ **List of Values in Query Parameters:**
```
https://myapi.com/orders?status=delivered&status=pending
```

---

## **3. Using `@RequestParam` in Spring Boot**
In Spring Boot, we use the `@RequestParam` annotation to extract query parameters from the URL.

### **Basic Example**
```java
@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/users")
    public String getUser(@RequestParam String name) {
        return "User Name: " + name;
    }
}
```
### **How It Works**
- The user sends a request:  
  ```
  GET /api/users?name=John
  ```
- Spring Boot automatically extracts `name=John` from the URL.
- The method `getUser()` receives `"John"` as the `name` parameter.
- The response is:
  ```json
  "User Name: John"
  ```

---

## **4. Handling Optional & Default Values**
Sometimes, a query parameter might be **optional**. If the user doesn’t provide it, the API should handle it gracefully.

### **Using `required = false` (Optional Parameter)**
```java
@GetMapping("/users")
public String getUser(@RequestParam(required = false) String name) {
    return "User Name: " + (name != null ? name : "Guest");
}
```
### **Using Default Value**
Instead of checking `null`, we can provide a default value.
```java
@GetMapping("/users")
public String getUser(@RequestParam(defaultValue = "Guest") String name) {
    return "User Name: " + name;
}
```
#### **Example Requests:**
1️⃣ **User provides `name`**
   ```
   GET /api/users?name=John
   ```
   **Response:** `"User Name: John"`

2️⃣ **User does NOT provide `name`**
   ```
   GET /api/users
   ```
   **Response:** `"User Name: Guest"`

---

## **5. Handling Multiple Query Parameters**
We can extract multiple query parameters using multiple `@RequestParam` annotations.

```java
@GetMapping("/users")
public String getUser(
    @RequestParam String name, 
    @RequestParam int age
) {
    return "User Name: " + name + ", Age: " + age;
}
```
#### **Example Request**
```
GET /api/users?name=Alice&age=30
```
#### **Response**
```json
"User Name: Alice, Age: 30"
```

---

## **6. Handling List of Values in Query Params**
Sometimes, we need to pass multiple values for the same parameter.

```java
@GetMapping("/orders")
public String getOrders(@RequestParam List<String> status) {
    return "Order Statuses: " + status;
}
```
#### **Example Request**
```
GET /api/orders?status=delivered&status=pending
```
#### **Spring Boot Converts It Into:**
```json
["delivered", "pending"]
```
#### **Response**
```
Order Statuses: [delivered, pending]
```

---

## **Conclusion**
✅ `@RequestParam` is used to extract query parameters from the URL.  
✅ We can handle optional parameters, set default values, and pass multiple query parameters.  
✅ We can even pass lists of values in query parameters.  

With this knowledge, you can now handle query parameters effectively in Spring Boot! 🚀