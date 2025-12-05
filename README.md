
# 📘 **JWT Auth System with Book Management API**

A clean and production-ready Spring Boot project implementing:

✔ JWT Authentication & Authorization
✔ Custom Exceptions
✔ Global Exception Handling
✔ Swagger UI Integration
✔ Clean Controller Structure
✔ Proper Error Handling
✔ Book CRUD APIs
✔ User Approval Check (Active/Inactive Users)

---

## 🚀 **Tech Stack**

* **Java 25**
* **Spring Boot 4**
* **Spring Security + JWT**
* **Spring Data JPA**
* **MySQL**
* **Swagger(OpenAPI 3)**
* **Lombok**

---

## 📂 **Project Features**

### 🔐 **1. JWT Authentication System**

* Register/Login users
* Generate JWT token
* Validate token
* Role-based access (optional)
* Token expiration (1 hours)

---

### 🧾 **2. Book Management (CRUD APIs)**

All book APIs are **secured with JWT**.

* Create Book
* Get All Books
* Get Book by ID
* Update Book
* Delete Book

Each book stores:

* Title
* Author
* ISBN
* Price
* **createdBy (User ID of creator)**

---

### 🧰 **3. Custom Exception Handling**

Includes:

* `ApiException`
* `ResourceNotFoundException`
* `ValidationException`
* `UnauthorizedException`

---

### 🌐 **4. Global Exception Handler**

Centralized handler using:


@RestControllerAdvice
public class GlobalExceptionHandler { ... }
```

This ensures uniform API error responses.

---

### 📘 **5. Swagger Documentation**

Swagger UI available at:

```
http://localhost:8080/swagger-ui/index.html
```

Includes:

* All secured Book APIs
* Auth APIs
* Error response examples

---

## 🔑 **Authentication Flow**

1. User registers
2. User logs in
3. JWT token is returned
4. Token is attached in headers for all Book API calls

### 🔐 **Postman Header**

```
Authorization: Bearer <your-token>
```

---

## 🚀 **How to Run the Project**

### **1. Clone Repository**

```bash
git clone https://github.com/<your-username>/<your-repository>.git
```

### **2. Configure Database**

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_system
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### **3. Run Application**

```bash
mvn spring-boot:run
```

---

## 🔍 **Swagger Demo**

Visit:

```
http://localhost:8080/swagger-ui/index.html
```

---

## ⭐ **Contributor**

**Your Name – Sheetal Rajput**


