# Penny Tracker - Frontend

Welcome to the frontend application for **Penny Tracker** (Smart Expense Manager). This is a modern, responsive single-page application designed to help users track their expenses, manage budgets, and visualize their financial data.

## 🚀 Tech Stack

- **Framework:** [React 19](https://react.dev/)
- **Build Tool:** [Vite](https://vitejs.dev/)
- **Language:** [TypeScript](https://www.typescriptlang.org/)
- **Styling:** [Tailwind CSS v4](https://tailwindcss.com/)
- **Routing:** [React Router](https://reactrouter.com/)
- **Icons:** [Lucide React](https://lucide.dev/)
- **Charts:** [Recharts](https://recharts.org/)

## 🛠️ Getting Started

### Prerequisites

Ensure you have [Node.js](https://nodejs.org/) installed on your machine (v18 or higher is recommended).

### Installation

1. Navigate to the frontend directory (if you aren't already there):
   ```bash
   cd frontend
   ```

2. Install the required dependencies:
   ```bash
   npm install
   ```

### Running Locally

To start the local Vite development server, run:

```bash
npm run dev
```

The application will typically be available at `http://localhost:5173`. 

> **Note:** For the application to function correctly, make sure the Java Spring Boot backend is also running simultaneously!

### Building for Production

To create an optimized, production-ready build, run:

```bash
npm run build
```

The compiled assets will be output to the `dist` directory.

## 📁 Project Structure

- `src/components/` - Reusable React components (UI elements, Layouts).
- `src/pages/` - Main view components corresponding to routes (Dashboard, Login, etc.).
- `src/context/` - React contexts (e.g., AuthContext) for global state management.
- `src/services/` - API integration and HTTP requests to the backend.
