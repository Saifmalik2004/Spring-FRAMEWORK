
# **Building the Holidays Page in a Spring Boot Application**

In this guide, we will go through **step by step** how to:
1. **Define a Model (`Holiday.java`)**
2. **Create a Controller (`HolidaysController.java`)**
3. **Design the Frontend (`holidays.html`) using Thymeleaf**
4. **Explain How the Data is Processed and Displayed**

---

## **1. Creating the `Holiday` Model**

📁 **File:** `com.example.demo.model.Holiday.java`  

```java
package com.example.demo.model;

public class Holiday {
    private final String day;
    private final String reason;
    private final Type type;

    // Enum for Holiday Type
    public enum Type {
        FESTIVAL, FEDERAL
    }

    // Constructor
    public Holiday(String day, String reason, Type type) {
        this.day = day;
        this.reason = reason;
        this.type = type;
    }

    // Getters
    public String getDay() {
        return day;
    }

    public String getReason() {
        return reason;
    }

    public Type getType() {
        return type;
    }
}
```

### **Explanation of the Model:**
- **Attributes:**
  - `day`: Stores the date of the holiday.
  - `reason`: Stores the name or purpose of the holiday.
  - `type`: Uses an **enum** to classify the holiday as `FESTIVAL` or `FEDERAL`.

- **Enum `Type`**:
  - Defines two types of holidays: `FESTIVAL` (like Christmas) and `FEDERAL` (like Independence Day).

- **Constructor:**
  - Initializes all the attributes.

- **Getter Methods:**
  - Provide access to the private attributes.

---

## **2. Creating the `HolidaysController`**

📁 **File:** `com.example.demo.controller.HolidaysController.java`  

```java
package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.model.Holiday;

@Controller
public class HolidaysController {

    @GetMapping("/holidays")
    public String displayHolidays(Model model) {

        // Creating a list of holidays manually (mock data)
        List<Holiday> holidays = Arrays.asList(
                new Holiday("Jan 1", "New Year's Day", Holiday.Type.FESTIVAL),
                new Holiday("Oct 31", "Halloween", Holiday.Type.FESTIVAL),
                new Holiday("Nov 24", "Thanksgiving Day", Holiday.Type.FESTIVAL),
                new Holiday("Dec 25", "Christmas", Holiday.Type.FESTIVAL),
                new Holiday("Jan 17", "Martin Luther King Jr. Day", Holiday.Type.FEDERAL),
                new Holiday("July 4", "Independence Day", Holiday.Type.FEDERAL),
                new Holiday("Sep 5", "Labor Day", Holiday.Type.FEDERAL),
                new Holiday("Nov 11", "Veterans Day", Holiday.Type.FEDERAL)
        );

        // Loop through the holiday types and filter holidays accordingly
        Holiday.Type[] types = Holiday.Type.values();
        for (Holiday.Type type : types) {
            model.addAttribute(type.toString(),
                    holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
        }

        return "holidays"; // Returns the holidays.html view
    }
}
```

### **Explanation of the Controller:**
1. **`@Controller` Annotation**  
   - Defines this as a Spring MVC controller.

2. **`@GetMapping("/holidays")`**  
   - Maps the request `GET /holidays` to this method.

3. **Creating a List of Holidays**  
   - Manually defining holidays (instead of fetching from a database).

4. **Filtering the Holidays Using Streams**  
   - Uses **Streams and `filter()`** to separate `FESTIVAL` and `FEDERAL` holidays.

5. **Passing Data to Thymeleaf Template (`holidays.html`)**  
   - Adds `FESTIVAL` and `FEDERAL` lists to the `Model`.

---
### **Model Interface in Controller (Spring MVC)**
In Spring MVC, the **`Model`** interface is used in a controller to send data from the backend (Java) to the frontend (Thymeleaf or JSP). It acts as a container to store attributes (data) that the view (HTML page) can use for rendering.

---

### **Understanding `Model` in `HolidaysController`**
Let's analyze the **`HolidaysController`** class and how the **`Model`** is used to pass holiday data to the frontend.


---

### **How the `Model` Interface Works in the Controller**
1. **Method `displayHolidays()`**  
   - This method is mapped to the `/holidays/all` URL.
   - It accepts a `Model` object, which will be used to pass data to the frontend.

2. **Creating a List of Holidays**  
   - The list contains **predefined holidays** with attributes: `day`, `reason`, and `type` (either `FESTIVAL` or `FEDERAL`).
   - This simulates data that would normally be fetched from a **database**.

3. **Using the `Model` to Pass Data to the View**
   ```java
   model.addAttribute(type.toString(),
      holidays.stream()
              .filter(holiday -> holiday.getType().equals(type))
              .collect(Collectors.toList()));
   ```
   - Here, we **filter holidays** by type (`FESTIVAL` or `FEDERAL`) using **Streams**.
   - Then, we **add them to the model** with keys `"FESTIVAL"` and `"FEDERAL"`, which will be used in the Thymeleaf template.

4. **Returning the View Name**
   ```java
   return "holidays.html";
   ```
   - This tells Spring Boot to **render the `holidays.html` page**, where the holidays will be displayed.

---


- The **`Model`** in Spring MVC helps pass **backend data** (Java objects) to the **frontend** (Thymeleaf).


This approach can be extended to work with **databases** (using JPA & MySQL) instead of hardcoded data. 🚀
## **3. Creating the `holidays.html` View**

📁 **File:** `src/main/resources/templates/holidays.html`

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Holidays</title>
    <link rel="stylesheet" href="/css/styles.css"> 
</head>
<body>

    <!-- Holidays Section -->
    <section class="w3l-timeline-1 py-5">
        <div class="container py-lg-5 py-4">
            <div class="title-heading-w3 text-center mb-sm-5 mb-4">
                <h5 class="title-small">Eazy School</h5>
                <h3 class="title-style">Awesome Holidays</h3>
            </div>
            
            <div class="row">
                <!-- Festival Holidays Column -->
                <div class="col-lg-6" th:if="${FESTIVAL}">
                    <h5 class="sub-title-timeline"><i class="fas fa-snowman"></i> Festival Holidays</h5>
                    <div class="timeline">
                        <div th:each="holiday : ${FESTIVAL}" class="column">
                            <div class="title">
                                <h2 th:text="${holiday.reason}"></h2>
                            </div>
                            <div class="description">
                                <h6 th:text="${holiday.day}" class="fas fa-calendar-alt"><i class="fas fa-calendar-alt"></i></h6>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Federal Holidays Column -->
                <div class="col-lg-6 mt-lg-0 mt-4" th:if="${FEDERAL}">
                    <h5 class="sub-title-timeline"><i class="fas fa-flag-usa"></i> Federal Holidays</h5>
                    <div class="timeline">
                        <div th:each="holiday : ${FEDERAL}" class="column">
                            <div class="title">
                                <h2 th:text="${holiday.reason}"></h2>
                            </div>
                            <div class="description">
                                <h6 th:text="${holiday.day}" class="fas fa-calendar-alt"><i class="fas fa-calendar-alt"></i></h6>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </section>

</body>
</html>
```

---

## **4. Explanation of Thymeleaf (`holidays.html`)**
### **Page Structure**
- **Container Div (`<div class="container">`)**  
  - Wraps the entire content inside a Bootstrap container.

- **Two Columns (`<div class="col-lg-6">`)**  
  - The left column displays **Festival Holidays** (`FESTIVAL` list).
  - The right column displays **Federal Holidays** (`FEDERAL` list).

### **Thymeleaf Attributes Used**
| **Thymeleaf Attribute** | **Purpose** |
|------------------------|-------------|
| `th:if="${FESTIVAL}"` | Only displays the section if there are festival holidays. |
| `th:if="${FEDERAL}"` | Only displays the section if there are federal holidays. |
| `th:each="holiday : ${FESTIVAL}"` | Loops through all festival holidays and creates a list. |
| `th:each="holiday : ${FEDERAL}"` | Loops through all federal holidays and creates a list. |
| `th:text="${holiday.reason}"` | Displays the holiday name dynamically. |
| `th:text="${holiday.day}"` | Displays the holiday date dynamically. |

---

## **5. How Everything Works Together**
1. **User Visits `/holidays`**
   - The `HolidaysController` is triggered.

2. **Controller Fetches and Filters Data**
   - Creates the `Holiday` list.
   - Uses **Streams** to filter `FESTIVAL` and `FEDERAL` holidays.
   - Passes filtered lists to the frontend.

3. **Thymeleaf Processes and Renders HTML**
   - Loops through **each holiday** and displays it inside its respective section.

---

### **🚀 Summary**
✅ **Created `Holiday` model**  
✅ **Built `HolidaysController` to fetch & send data**  
✅ **Designed `holidays.html` using Thymeleaf**  
✅ **Explained Thymeleaf syntax and working**  

### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/8-ThymeleafAndSpringMVC/10-holidayPage/project_3/)**.  

The **`project_3`** folder contains the full project with all the latest changes, including the updated `holidays.html` file. You can download the folder and run the project with the modifications applied.  

If you just need the updated HTML files, you can copy and paste the code from the **`src`** directory.