# Penny Tracker (Smart Expense Manager)

## 📌 About The Project

Penny Tracker is a comprehensive, full-stack personal finance and expense management application. It allows users to track their daily expenses, create and manage monthly budgets, organize spending into categories, and view aggregated insights through an interactive dashboard.

## 🚀 Tech Stack

This project is built using a modern, scalable, and robust technology stack:

### Backend
* **Java 17 & Spring Boot 3:** The core framework for the backend. Provides a powerful, production-ready environment for building enterprise-grade APIs.
* **Spring Security & JWT:** Handles user authentication and authorization securely using stateless JSON Web Tokens.
* **Spring Data JPA & Hibernate:** Manages database interactions and ORM (Object-Relational Mapping), making database queries clean and maintainable.
* **PostgreSQL / H2 Database:** H2 is used for rapid local development/testing, while PostgreSQL is the target production relational database.
* **Maven:** Dependency management and build tool.

### Frontend
* **React 19:** A modern UI library for building fast, interactive user interfaces.
* **Vite:** A blazing-fast frontend build tool that significantly improves development speed and Hot Module Replacement (HMR) compared to traditional bundlers like Webpack.
* **TypeScript:** Adds static typing to JavaScript, catching errors at compile-time and improving developer experience and code quality.
* **Tailwind CSS v4:** A utility-first CSS framework that allows for rapid, custom UI styling without leaving the HTML/JSX.
* **Recharts:** A composable charting library built on React components, used for visualizing financial data on the dashboard.
* **React Router:** Handles client-side routing for seamless navigation between pages (Dashboard, Login, Settings, etc.).
* **Lucide React:** Beautiful, consistent iconography.

## 🤔 Why This Tech Stack?

1. **Separation of Concerns:** By splitting the application into a standalone REST API (Spring Boot) and a Single Page Application (React), the frontend and backend can be developed, scaled, and deployed independently.
2. **Robustness:** Spring Boot offers unmatched stability and security out-of-the-box, making it perfect for handling sensitive financial data.
3. **Developer Velocity:** Vite and Tailwind CSS provide an incredibly fast feedback loop for frontend development.
4. **Maintainability:** TypeScript in the frontend and strong static typing in Java ensure that the codebase remains maintainable and less prone to runtime errors as it grows.

## ⚙️ How It Works

1. **Authentication Flow:** 
   - A user signs up or logs in via the React frontend.
   - The backend validates the credentials and returns a secure JWT (JSON Web Token).
   - The frontend stores this token and attaches it as a `Bearer` token in the `Authorization` header of all subsequent API requests.

2. **Data Flow:**
   - **Expenses & Budgets:** Users can create categories, log expenses, and set budgets. The React frontend sends this data via HTTP POST/PUT requests to the Spring Boot REST API.
   - **Persistence:** The Spring Boot service layer validates the business logic (e.g., checking if a budget already exists for that category) and uses Spring Data JPA to persist the records in the database.
   - **Dashboard Analytics:** When the user visits the dashboard, the frontend requests aggregated data. The backend performs SQL aggregation (using JPA `@Query`) to calculate total spending, remaining budget, and category-wise breakdowns, returning a comprehensive `DashboardDto` to the frontend, which renders it using `Recharts`.

## 📂 Repository Structure

* `/backend` - Contains the Java Spring Boot application (Controllers, Services, Repositories, Entities).
* `/frontend` - Contains the React Vite application (Pages, Components, Context, Services).
