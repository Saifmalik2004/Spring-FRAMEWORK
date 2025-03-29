# Handling Path Variables in Spring Boot - Fetching Holidays

## Overview
In this project, we have modified our existing **Education Hub** application to use **path variables** instead of query parameters when fetching holiday data. This change allows users to request specific types of holidays (Festival Holidays or Federal Holidays) dynamically by passing the holiday type directly in the URL.

---

## Changes Implemented

### 1. **Modifying the Footer Section**
We updated the **footer** section where the request to fetch holidays is sent. Instead of using query parameters, we now include the holiday type in the URL path.

#### **Previous Code (Using Query Parameters)**
```html
<li><a th:href="@{/holidays(festival='true',federal='true')}">Holidays</a></li>
```
#### **Updated Code (Using Path Variables)**
```html
<li><a th:href="@{/holidays/all}">Holidays</a></li>
<li><a th:href="@{/holidays/festival}">Festival Holidays</a></li>
<li><a th:href="@{/holidays/federal}">Federal Holidays</a></li>
```
Now, the URL sent will look like:
```
/holidays/all
/holidays/festival
/holidays/federal
```
This tells the backend that the user wants either **all holidays**, only **Festival Holidays**, or only **Federal Holidays**.

---

### 2. **Updating the Controller to Accept Path Variables**
We modified the `HolidaysController` to handle path variables using `@PathVariable`.

#### **Updated Code (With Path Variables)**
```java
@GetMapping("/holidays/{display}")
public String displayHolidays(@PathVariable String display, Model model) {
    if ("all".equals(display)) {
        model.addAttribute("festival", true);
        model.addAttribute("federal", true);
    } else if ("federal".equals(display)) {
        model.addAttribute("federal", true);
    } else if ("festival".equals(display)) {
        model.addAttribute("festival", true);
    }

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

    Holiday.Type[] types = Holiday.Type.values();
    for (Holiday.Type type : types) {
        model.addAttribute(type.toString(), holidays.stream()
            .filter(holiday -> holiday.getType().equals(type))
            .collect(Collectors.toList()));
    }

    return "holidays.html";
}
```
Now, the controller fetches and displays holidays based on the **path variable** passed in the URL.

---

### 3. **Updating `holidays.html` to Handle Path Variables**
We updated `holidays.html` to check if the user requested all holidays, only Festival Holidays, or only Federal Holidays, and then render the appropriate sections using **Thymeleaf's** `th:if`.

#### **Updated Code**
```html
<div class="col-lg-6" th:if="${festival} == true">
    <h5 class="sub-title-timeline"><i class="fas fa-snowman"></i> Festival Holidays</h5>
    <div class="timeline">
        <div th:each="holiday : ${FESTIVAL}" class="column">
            <div class="title">
                <h2 th:text="${holiday.reason}"></h2>
            </div>
            <div class="description">
                <h6 th:text="${holiday.day}" class="fas fa-calendar-alt"></h6>
            </div>
        </div>
    </div>
</div>

<div class="col-lg-6 mt-lg-0 mt-4" th:if="${federal} == true">
    <h5 class="sub-title-timeline"><i class="fas fa-flag-usa"></i> Federal Holidays</h5>
    <div class="timeline">
        <div th:each="holiday : ${FEDERAL}" class="column">
            <div class="title">
                <h2 th:text="${holiday.reason}"></h2>
            </div>
            <div class="description">
                <h6 th:text="${holiday.day}" class="fas fa-calendar-alt"></h6>
            </div>
        </div>
    </div>
</div>
```
With this, when a user visits `/holidays/festival`, only **Festival Holidays** will be displayed, and when they visit `/holidays/federal`, only **Federal Holidays** will be shown. If they visit `/holidays/all`, both categories will be displayed.

---
### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/10-QueryParamsAndPathVariable/3-PathVariable/project_5/)**.  

The **`project_5`** folder contains the full project with all the latest changes, You can download the folder and run the project with the modifications applied. 
## Conclusion
With these updates, the **Education Hub** project now dynamically fetches and displays holidays based on user-selected filters using **path variables** instead of query parameters. This makes the URLs cleaner, more user-friendly, and **RESTful** in design.

