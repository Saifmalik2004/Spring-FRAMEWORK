# Model-View-Controller (MVC) and Spring MVC Architecture

## What is MVC?
MVC (Model-View-Controller) is a software design pattern that separates an application into three interconnected components:

### 1. **Model**
- Represents the data and business logic.
- Communicates with the database to retrieve, update, and delete data.
- Encapsulates application logic and rules.

### 2. **View**
- Handles UI representation.
- Receives data from the controller and presents it to the user.
- Usually consists of HTML, JSP, Thymeleaf, or other templating engines.

### 3. **Controller**
- Manages user input and request handling.
- Processes user actions, interacts with the model, and selects the view for response.
- Acts as a bridge between Model and View.

This separation makes applications modular, maintainable, and scalable.

---

## Spring MVC Architecture - Internal Flow Explained
Spring MVC is a powerful Java-based framework that follows the MVC pattern. It simplifies web development by handling request routing, response processing, and dependency injection.

### **How a Request is Handled in Spring MVC**

A typical request in Spring MVC follows these steps:

### **Step 1: Web Client Sends a Request**
- A user enters a URL in the browser (e.g., `http://example.com/home`).
- This request is sent to the **Tomcat Server** (or any other servlet container).

### **Step 2: Front Controller - DispatcherServlet**
- The request is received by the `DispatcherServlet`.
- It acts as the Front Controller, managing the entire request-processing lifecycle.

### **Step 3: Handler Mapping (Finding the Controller)**
- `DispatcherServlet` consults the `HandlerMapping` to determine which controller should handle the request.
- `HandlerMapping` maps the request to an appropriate Controller method using annotations like `@RequestMapping`.

### **Step 4: Controller Processes the Request**
- The mapped controller method executes.
- It interacts with the **Model** (services, DAOs) to fetch or manipulate data.
- Returns a `ModelAndView` object containing:
  - Model data (business logic result).
  - Logical view name (template to be rendered).

### **Step 5: View Resolver (Finding the View)**
- The `ViewResolver` converts the logical view name (e.g., "home") into a physical view (e.g., `home.jsp` or `home.html`).
- It may use template engines like JSP, Thymeleaf, or FreeMarker.

### **Step 6: View is Rendered**
- The selected view processes the model data and generates the final HTML response.
- The response is sent back to the client (browser).

### **Complete Request Cycle in Spring MVC**
1. **User requests a resource** → `DispatcherServlet`
2. **DispatcherServlet finds the Controller** → `HandlerMapping`
3. **Controller processes business logic** → Calls Service and Model
4. **Model returns data** → Controller packages it in `ModelAndView`
5. **DispatcherServlet calls ViewResolver** → Resolves actual view
6. **View renders the response** → Sent back to user

---

## **Visualization Diagram**

![Home Page Preview](../../Images/internal%20flow.png)  

## **Key Components in Spring MVC**

### 1. **DispatcherServlet (Front Controller)**
- Central component managing the request-response cycle.
- Delegates work to controllers, view resolvers, and other components.

### 2. **HandlerMapping**
- Maps incoming requests to appropriate controller methods.

### 3. **Controller**
- Processes requests and interacts with the model.
- Annotated with `@Controller` or `@RestController`.

### 4. **Model & Service Layer**
- Contains business logic and interacts with the database.

### 5. **ViewResolver**
- Maps logical view names to physical views.
- Common implementations: `InternalResourceViewResolver`, `ThymeleafViewResolver`.

### 6. **View (JSP, Thymeleaf, HTML, JSON, etc.)**
- Renders data for the user.

---

## **Conclusion**
Spring MVC follows a well-defined architecture where `DispatcherServlet` acts as the central hub, efficiently managing requests and responses. This design pattern improves application modularity, making it easy to maintain and scale.

🚀 Happy Coding with Spring MVC! 🚀

