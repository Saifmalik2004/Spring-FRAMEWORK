# Query Parameters in Education Hub - Fetching Holidays

## Overview
In this project, we have modified our existing **Education Hub** application to use **query parameters** when fetching holiday data. The purpose of this change is to allow users to request specific types of holidays (festival holidays or federal holidays) dynamically through query parameters in the URL.

---

## Changes Implemented

### 1. **Modifying the Footer Section**
We updated the **footer** section where the request to fetch holidays is sent. Instead of a simple link, we now pass query parameters to specify the type of holidays the user wants to retrieve.

#### **Previous Code**
```html
<li><a th:href="@{/holidays}">Holidays</a></li>
```
#### **Updated Code**
```html
<li><a th:href="@{/holidays(festival='true',federal='true')}">Holidays</a></li>
```
Now, the URL sent will look like:
```
/holidays?festival=true&federal=true
```
This tells the backend that the user wants both **festival** and **federal** holidays.

---

### 2. **Updating the Controller to Accept Query Parameters**
We modified the `HolidaysController` to handle query parameters using `@RequestParam`.

#### **Previous Code (Without Query Parameters)**
```java
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
    
    // Filtering holidays based on type
    Holiday.Type[] types = Holiday.Type.values();
    for (Holiday.Type type : types) {
        model.addAttribute(type.toString(),
            holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList()));
    }
    return "holidays"; // Returns holidays.html view
}
```

#### **Updated Code (With Query Parameters)**
```java
@GetMapping("/holidays")
public String displayHolidays(@RequestParam(required = false) boolean festival,
                              @RequestParam(required = false) boolean federal, Model model) {
    
    model.addAttribute("festival", festival);
    model.addAttribute("federal", federal);
    
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
            model.addAttribute(type.toString(),
                    (holidays.stream().filter(holiday -> holiday.getType().equals(type)).collect(Collectors.toList())));
        }
        return "holidays.html";
}
```
Now, the controller only sends the requested type of holidays based on the **query parameters**.

---

### 3. **Updating `holidays.html` to Handle Query Parameters**
We updated `holidays.html` to check if query parameters are passed and conditionally render the respective holiday sections using **Thymeleaf's** `th:if`.

#### **Previous Code (Showing All Holidays Always)**
```html
<div class="col-lg-6">
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
```

#### **Updated Code (Using Query Parameters for Conditional Rendering)**
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
With this, if `festival=true` is passed in the URL, only **festival holidays** will be displayed, and if `federal=true` is passed, only **federal holidays** will be shown.

---

### **View the Changes & Download the Updated Project**  
You can check out the latest changes by visiting this link: **[Project Preview](/10-QueryParams&PathVariable/2-acceptingQueryParamWith@RequestParam/project_4/)**.  

The **`project_4`** folder contains the full project with all the latest changes, You can download the folder and run the project with the modifications applied.  

---

## Conclusion
With these updates, the **Education Hub** project now dynamically fetches and displays holidays based on user-selected filters using query parameters. This enhances flexibility and improves the **user experience** by allowing selective viewing of different types of holidays.

