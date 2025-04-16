

# 🚨 Handling Global Exceptions in Spring Security

## 💡 Why Do We Need Exception Handling?

When something goes wrong (like a server crash, null pointer, or any unhandled exception) — Spring will by default show an ugly technical error page (called the "Whitelabel Error Page").  

👉 This is confusing for users and looks unprofessional.

To give users a friendly message and guide them back to the app safely, we handle exceptions and show a clean **error page** instead.

---

## ⚙️ Exception Handling in Spring — The Clean Way

Spring provides two powerful annotations for handling exceptions:

| Annotation | Purpose |
|------------|---------|
| `@ControllerAdvice` | Catches exceptions globally for all controllers in your app. |
| `@ExceptionHandler` | Handles specific exception types and defines custom response logic. |

---

## 💻 Code: Global Exception Controller

```java
@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception) {
        ModelAndView errorPage = new ModelAndView();
        errorPage.setViewName("error");
        errorPage.addObject("errormsg", exception.getMessage());
        return errorPage;
    }
}
```

---

### 💡 Code Breakdown:

- `@ControllerAdvice`:  
Spring scans this class and uses it to **handle exceptions globally**.
No need to write try-catch in every controller.

- `@ExceptionHandler(Exception.class)`:  
Whenever any `Exception` is thrown, this method will catch it.

- `ModelAndView`:  
Creates a response that tells Spring to load `error.html` and pass the error message using `errormsg`.

---

## 🎨 Creating the `error.html` Page

This is the page shown when an exception is caught:

```html
<!-- error block -->
<section class="page_404">
    <div class="container">
        <div class="row">
            <div class="col-sm-12 ">
                <div class="col-sm-10 col-sm-offset-1  text-center">
                    <div class="four_zero_four_bg">
                        <h1 class="text-center">oops...</h1>
                    </div>
                    <div class="contant_box_404">
                        <h3 class="h2">Look like you're lost</h3>
                        <p th:text="${'Error Message - ' + errormsg}"></p>
                        <a th:href="@{/home}" class="link_404 btn btn-style btn-style-3">Go to Home</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<!-- //error block -->
```

💡 This view:
- Shows a friendly "oops" message.
- Displays the real error message using Thymeleaf (`errormsg`).
- Offers a direct link back to `/home`.

---

## 💅 Add Styling for `error.html`

Add this CSS in your styles file for the error page:

```css
/* //error section */
.page_404 img{
    width:100%;
}

.four_zero_four_bg{
    background-image: url(/assets/images/dribbble.gif);
    height: 400px;
    background-position: center;
}

.four_zero_four_bg h1{
    font-size:80px;
}

.four_zero_four_bg h3{
    font-size:80px;
}

.link_404{
    padding: 10px 20px;
    margin: 20px 0;
    display: inline-block;
}

.contant_box_404{
    margin-top:-50px;
}
/* //error section */
```


## 💅 Add image from project 10  `dribbble.gif`

---
Absolutely! Your write-up is already clear and nicely structured. Let me suggest some **extra points** you can add at the end to make it even more complete and helpful for understanding **Exception Handling in Spring Security & Spring Boot**.

---

## 🧠 More About Exception Handling in Spring

### 💡 Different Levels of Exception Handling:

| Level | Description | Example |
|-------|-------------|---------|
| Method Level | Handle exceptions for a **specific method** only. | `try-catch` block inside a method. |
| Controller Level | Handle exceptions for a **specific controller class**. | `@ExceptionHandler` inside a controller. |
| Global Level | Handle exceptions across the **entire application**. | `@ControllerAdvice` + `@ExceptionHandler`. |

---

### 🔥 Handling Specific Exceptions

Instead of catching all exceptions (`Exception.class`), you can handle specific ones like:

```java
@ExceptionHandler(NullPointerException.class)
public ModelAndView handleNullPointer(NullPointerException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("error");
    mav.addObject("errormsg", "Null Pointer: " + ex.getMessage());
    return mav;
}
```

✅ This way you can show **different messages or pages** based on the error type!

---

### ⚠️ Security-Specific Exception Handling

When you're using **Spring Security**, some common exceptions include:

| Exception Class | Purpose |
|-----------------|---------|
| `AccessDeniedException` | Triggered when user tries to access a **protected resource** without permission. |
| `AuthenticationException` | Thrown when login or authentication fails. |

You can handle them globally like this:

```java
@ExceptionHandler(AccessDeniedException.class)
public ModelAndView handleAccessDenied(AccessDeniedException ex) {
    ModelAndView mav = new ModelAndView();
    mav.setViewName("accessDenied");
    mav.addObject("errormsg", "You don't have permission to access this page!");
    return mav;
}
```

---

### 🧾 Bonus: Custom `ErrorController`

You can also customize the default `/error` path by implementing `ErrorController`:

```java
@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";  // Your custom error.html
    }
}
```

This gives you even more control for both **unexpected errors** and **Spring Security-related errors**.

---

### ⚡ Best Practices:

✅ **Don’t leak sensitive info** in error messages (like stack traces, server info).  
✅ Log exceptions using `@Slf4j` or `Logger` for easier debugging.  
✅ Show meaningful messages to users (avoid technical jargon).  
✅ Create different error pages for different HTTP status codes (404, 403, 500).  

---

### 💯 Conclusion:

- Exception handling isn't just about avoiding crashes — it’s about offering a smooth user experience even when things go wrong.
- Global exception handling keeps your code clean and consistent.
- For security-related scenarios, it's important to handle **AccessDenied** and **AuthenticationException** separately.
- You can fully customize error pages using `ErrorController` for extra flexibility.

---

## ✅ Summary:

- Global exception handling improves the user experience and avoids exposing internal errors.
- `@ControllerAdvice` and `@ExceptionHandler` allow you to handle all errors in one place.
- A friendly `error.html` page makes your app look polished even during failure.
- You can check the full working setup in **Project-10**.

👉 [💻 Grab Project 10 Code](/14-ExceptionHandling/project_10/)*

---
