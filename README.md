# UrbanAttire Backend Service

This project provides backend services for **UrbanAttire**, a shopping platform focused on trendy fashion for girls. The backend is built using **Spring Boot** and offers a range of functionalities for user management, product management, orders, and cart management. Additionally, product images and user profile pictures are stored and managed through **Cloudinary**.

### UrbanAttire FrontEnd React-Native Repository Link
```bash
https://github.com/jhantu626/UrbanAttire
```

## Features

1. **Authentication & Authorization**
   - User Registration and Login using JWT tokens.
   - Secure access to protected routes via token validation.

2. **User Management**
   - View user profile.
   - Update mobile number, address, and profile picture.
   - Manage user authentication tokens.

3. **Product Management**
   - Add new products with details and images.
   - Fetch single or multiple products by category with pagination.
   - Update product information.

4. **Cart Management**
   - Add products to the user's cart with size and color options.
   - View all items in the user's cart.
   - Remove items from the cart.

5. **Order Management**
   - Place orders based on cart items and delivery address.
   - Fetch all orders for a user.

6. **Image Upload & Storage**
   - Product images and user profile pictures are uploaded to **Cloudinary** for secure and scalable storage.

---

## API Documentation

This project uses **Swagger** for API documentation. You can explore the available API endpoints using the following Swagger URLs:

- **Swagger UI**: [http://localhost:9001/swagger-ui/index.html](http://localhost:9001/swagger-ui/index.html)
- **API Docs**: [http://localhost:9001/v3/api-docs](http://localhost:8080/v3/api-docs)

These URLs provide a user-friendly interface to test and document API endpoints.

---

## API Endpoints

### Authentication
- **POST** `/api/v1/auth/register` - Register a new user.
- **POST** `/api/v1/auth/login` - Login and get authentication token.

### User
- **GET** `/api/v1/user/profile` - Fetch user profile details.
- **PUT** `/api/v1/user/update-address` - Update user address.
- **PUT** `/api/v1/user/update-number/{number}` - Update mobile number.
- **PUT** `/api/v1/user/update-profile-pic` - Upload/update profile picture.
- **GET** `/api/v1/user/profile-pic` - Fetch profile picture.

### Product
- **POST** `/api/v1/product/create-product` - Add a new product with image upload.
- **GET** `/api/v1/product/{id}` - Get product by ID.
- **GET** `/api/v1/product/all-products` - Get all products, with optional category filter and pagination.

### Cart
- **POST** `/api/v1/cart/add-cart/{productId}` - Add a product to cart.
- **GET** `/api/v1/cart/all-carts` - Fetch all cart items for a user.
- **DELETE** `/api/v1/cart/{id}` - Remove an item from the cart.

### Order
- **POST** `/api/v1/order/make-order` - Place an order based on the cart and selected address.
- **GET** `/api/v1/order/all-orders` - Get all orders placed by a user.

---

## Tech Stack

- **Java** with **Spring Boot** for the backend.
- **MySQL** for database management.
- **Cloudinary** for image uploads and storage.
- **JWT** for securing API endpoints.
- **Maven** for project build management.

---

## Getting Started

### Prerequisites

- JDK 11+
- MySQL
- Maven
- Cloudinary account (for image storage)

### Setup

1. Clone the repository:
   ```bash
   https://github.com/jhantu626/UrbanAttire-Backend.git
   ```
2. Update application.properties/application.yaml file
3. Add your mysql urls and password
4. add your cloudinary details(Api Key,Name,Url)


## Contribution

Feel free to fork the project and create a pull request with improvements or bug fixes.

---

## Contact

For any questions or feedback, reach out to:
- **Jhantu Bala**: [Email](mailto:jhantubala626@gmail.com)


