
### **1. Updating Footer Links**
You have multiple `th:href="@{/contact}"` links that need to be assigned proper URLs. Below is the corrected structure:

#### **Quick Links Section**
```html
<ul>
    <h6 class="footer-title-29">Quick Links</h6>
    <li><a th:href="@{/about}">About Us</a></li>
    <li><a th:href="@{/courses}">Courses</a></li>
    <li><a th:href="@{/contact}">Become a Teacher</a></li>
    <li><a th:href="@{/contact}">Contact Us</a></li>
    <li><a th:href="@{/contact}">Career</a></li>
</ul>
```

#### **Explore Section**
```html
<ul>
    <h6 class="footer-title-29">Explore</h6>
    <li><a th:href="@{/holidays}">Holidays</a></li>
    <li><a th:href="@{/contact}">Privacy Policy</a></li>
    <li><a th:href="@{/contact}">Contact Us</a></li>
    <li><a th:href="@{/contact}">License & Uses</a></li>
    <li><a th:href="@{/courses}">Courses</a></li>
</ul>
```

---

### **2. Updating Home Page Links**
These links/buttons should also be updated:

#### **Admission Now Button**
```html
<div class="d-flex align-items-center buttons-banner"> 
    <a th:href="@{/contact}" class="btn btn-style mt-lg-5 mt-4">Admission Now</a>
</div>
```

#### **Apply Now Button**
```html
<a th:href="@{/contact}" class="btn btn-style mt-5">Apply Now</a>
```

#### **Browse More Courses Button**
```html
<div class="text-center mt-sm-5 mt-4 pt-sm-0 pt-1">
    <a th:href="@{/courses}" class="btn btn-style btn-style-secondary mt-sm-3">
        Browse More Courses
    </a>
</div>
```

#### **Join for Free Button**
```html
<li>
    <a th:href="@{/contact}" class="btn btn-style btn-style-2 mt-lg-0 mt-3">Join for Free</a>
</li>
```
