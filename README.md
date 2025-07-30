# 🛍️ Scalable_And_Secure-E-Shop-API-Backend
In This Project Users can register, log in securely with JWT, browse products, add/remove items from cart, and place orders. Admins can manage products and view orders. The system ensures role-based access, secure sessions, and reliable data handling through Spring Boot and MySQL.


## 🚀 Supported Features

- **Authentication & Authorization** using JWT tokens via `AuthController`
- **User Management**: register new users, login, get/update user details
- **Product Management**: CRUD operations over `/product` endpoints
- **Category Management**: organize products by categories
- **Shopping Cart**: add/remove items, view cart via `CartController` and `CartItemController`
- **Order Processing**: place new orders and view order history
- **Image Handling**: upload/list images related to products
- **API Response Standardization**: consistent `ApiResponse<T>` structure
- **AOP**-based global exception handling and logging through `ControllerAdvice`

---

## 🛠️ Tech Stack

| Component       | Technology                         |
|----------------|------------------------------------|
| Language        | Java 17+                           |
| Framework       | Spring Boot                        |
| ORM             | Spring Data JPA (Hibernate)        |
| Security        | JWT-based authentication           |
| Database        | MySQL or in-memory H2 (development)|
| Build Tool      | Maven                              |
| Patterns        | Layered architecture, AOP, DTOs    |
| Developer Tools | Git, Postman, GitHub               |

---
