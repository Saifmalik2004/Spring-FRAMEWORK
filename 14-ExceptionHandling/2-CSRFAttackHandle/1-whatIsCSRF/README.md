

# 🛡️ Understanding CSRF Attack (Cross-Site Request Forgery)

---

## 💡 What is CSRF?

**CSRF (Cross-Site Request Forgery)** is a type of attack where a hacker tricks an authenticated user into unknowingly sending **malicious requests** to a web application.

👉 In simple terms:  
> "You are logged in to your bank account, and a hacker forces your browser to perform actions (like transferring money) **without your permission**."

---

## 🧠 How CSRF Works

1. **Victim logs into a secure website**  
   (e.g. `https://mybank.com`) and a **session cookie** is saved in the browser.

2. **Without logging out**, the victim visits a malicious site (`https://hacker-site.com`).

3. The malicious site silently makes a request to `https://mybank.com` using the victim’s **browser session**.

4. Since the user is already logged in, the bank server processes the request **thinking it's valid**.

---

## 💥 Real-World Example

### 🏦 Scenario:

You are logged into your bank:

```
https://mybank.com/transfer?amount=1000&toAccount=HACKER_ACCOUNT
```

Normally, you'd click a "Send Money" button and the bank would process the transfer.

---

### ⚠️ CSRF Attack:

A hacker creates a malicious page:

```html
<html>
  <body>
    <h1>Click Here for Free Gift!</h1>
    <img src="https://mybank.com/transfer?amount=1000&toAccount=HACKER_ACCOUNT" style="display:none;">
  </body>
</html>
```

When you (still logged into your bank) visit this page, your browser will:

- Automatically include your **session cookie**.
- The bank **thinks the request is from you**.
- 💸 **Money gets transferred to the attacker's account.**

---

## 🧐 Why Does This Happen?

- The browser automatically sends cookies for **any request** to the same site.
- Servers rely only on session cookies to validate users.
- No way for the server to check **if the request is genuine or forged**.

---

## 🧯 Solution — CSRF Token 💡

To defend against CSRF:

1. Server generates a **unique CSRF token** for each form.
2. Token is stored in the form as a hidden field:

```html
<input type="hidden" name="csrf_token" value="X1a2b3c4d5e6">
```

3. When the form is submitted, the server checks:

```
Does submitted CSRF token match the one in the session?
```

✅ If **yes** → process request.  
❌ If **no** → reject request.

---

## 💪 CSRF Protection in Spring Security

Spring Security has CSRF protection **enabled by default**.

When you submit a POST/PUT/DELETE request:

1. A CSRF token is required in the form.
2. If the token is missing or incorrect, Spring will block the request.

In Thymeleaf, you can add CSRF token easily:

```html
<form th:action="@{/transfer}" method="post">
    <input type="hidden" th:name="${_csrf.parameterName}" th:value="${_csrf.token}" />
    <input type="text" name="amount" />
    <input type="text" name="toAccount" />
    <button type="submit">Send Money</button>
</form>
```

---

## ⚡ Key Takeaways:

| Concept | Description |
|---------|-------------|
| CSRF | Tricks a logged-in user to send unintended requests. |
| Attack Vector | Often embedded in `<img>`, `<iframe>`, JavaScript, or hidden forms. |
| Solution | CSRF tokens + SameSite Cookies + Logging out when done. |
| Spring Security | Handles CSRF out of the box (token validation). |

---

## 🧠 Summary:

- CSRF attacks exploit **trust in the browser**.
- They happen when an attacker makes the browser send unintended requests using **stored session cookies**.
- Protection methods:
   - CSRF Token validation.
   - `SameSite` cookie attribute.
   - Re-authentication before sensitive actions.

---

✅ **Pro Tip:**  
Always test your web forms using developer tools — disable cookies manually and try submitting, or attempt sending the same request from another origin to see how your server handles it!

