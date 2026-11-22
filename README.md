# 🎬 CineSpot – Movie Ticket Booking System

## 📌 Project Overview

**CineSpot** is a full-stack web application designed to simplify the movie ticket booking process. It allows users to explore movies, view theaters and available shows, select seats, and book movie tickets online. The system provides a seamless booking experience with user authentication, seat availability management, booking confirmation, and payment management.

## 🎯 Problem Statement

Traditional movie ticket booking processes can be time-consuming and inconvenient. Users often need to visit theaters physically or use different platforms to check movie schedules, seat availability, and ticket prices. Managing bookings and preventing duplicate seat reservations can also be challenging.

CineSpot provides a centralized online platform where users can browse movies, select theaters and shows, book seats, and manage their reservations efficiently.

## ✨ Features

- 🎥 Browse and explore movies
- 🔍 Search and view movie details
- 🏢 Explore theaters and screens
- 🕒 View available movie shows
- 💺 Select seats for movie bookings
- 🔒 Seat locking mechanism to prevent duplicate bookings
- 👤 User registration and login
- 🎟️ Online ticket booking
- 💳 Payment management
- 📋 Booking history and booking details
- ❌ Booking cancellation
- 💰 Payment refund management
- 🔄 Real-time seat availability management
- 📱 Responsive user interface

## 🛠️ Technologies Used

### Frontend

- React.js
- Vite
- JavaScript
- HTML5
- CSS3
- Axios

### Backend

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- REST APIs

### Database

- MySQL

### Tools

- Visual Studio Code
- IntelliJ IDEA
- Postman
- Git
- GitHub

## ⚙️ Working Mechanism

1. Users register or log in to the CineSpot application.
2. Users browse available movies and view movie details.
3. Users select a theater and available movie show.
4. The system displays the available seats for the selected show.
5. Users select their preferred seats.
6. The backend validates seat availability and locks the selected seats temporarily.
7. Users confirm their booking and proceed with payment.
8. The system processes payment information and updates the booking status.
9. Successfully booked seats are marked as BOOKED.
10. Users can view their booking details, booking history, and manage cancellations.

## 💺 Seat Booking and Locking Mechanism

CineSpot implements a seat availability and locking mechanism to prevent multiple users from booking the same seat for the same show.

**Basic Logic:**

```text
User selects a movie and show

Fetch available seats

User selects preferred seats

Validate seat availability

Lock selected seats temporarily

Process booking and payment

If payment is successful:
    Mark seats as BOOKED
    Confirm booking
Else:
    Release locked seats
    Mark booking as FAILED
```

This mechanism helps maintain booking consistency and prevents duplicate seat reservations.

## 📂 Project Structure

```text
CineSpot/
│
├── cinespot-frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   ├── assets/
│   │   └── ...
│   ├── public/
│   ├── package.json
│   └── ...
│
├── darsh_cinespot/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/dm/darsh_cinespot/
│   │   │   │       ├── controller/
│   │   │   │       ├── service/
│   │   │   │       ├── repository/
│   │   │   │       ├── model/
│   │   │   │       ├── dto/
│   │   │   │       └── exception/
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── ...
│   ├── pom.xml
│   └── ...
│
└── README.md
```

*Note: Update the folder names and structure according to your actual repository.*

## 🚀 Installation and Setup

### 1. Clone the Repository

```bash
git clone https://github.com/Shri0Darshan/CineSpot.git
```

### 2. Navigate to the Project Directory

```bash
cd CineSpot
```

### 3. Setup the Backend

Navigate to the Spring Boot backend directory:

```bash
cd darsh_cinespot
```

Configure your MySQL database in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cine_spot
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Start the Backend Server

Run the Spring Boot application using Maven:

```bash
mvn spring-boot:run
```

The backend server runs on:

```text
http://localhost:8082
```

### 5. Setup the Frontend

Open another terminal and navigate to the frontend directory:

```bash
cd cinespot-frontend
```

Install dependencies:

```bash
npm install
```

### 6. Start the Frontend Server

```bash
npm run dev
```

The frontend application runs on the local Vite development server.

## 🗄️ Database Entities

The application uses MySQL to store and manage the following entities:

- User
- Movie
- Theater
- Screen
- Seat
- Show
- ShowSeat
- Booking
- Payment

These entities are connected through relationships to manage movie information, theaters, shows, seat availability, bookings, and payments.

## 🔮 Future Scope

- Integration with real payment gateways
- QR-code-based digital movie tickets
- Email and SMS booking confirmations
- Admin dashboard for movie and theater management
- Movie reviews and ratings
- Advanced movie recommendations
- Promotional offers and coupon management
- Improved booking analytics
- Mobile application integration

## 👨‍💻 Developer

**Darshan Pravin Mali**

B.Tech – CSE(Data Science)

GitHub: [Shri0Darshan](https://github.com/Shri0Darshan)

## 📄 License.

This project is developed for educational and academic purposes.
