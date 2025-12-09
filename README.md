# 🐳 Employee Management Application - Full Stack Containerization

This project demonstrates the containerization of a simple full-stack application using **Docker** and **Docker Compose**. The application consists of a Java (Spring Boot/Maven) backend, a React frontend, and a PostgreSQL database.

The project follows a staged approach to containerization:

* **Level 1:** Basic Dockerfiles for individual services.
* **Level 2:** Multi-stage Dockerfiles for optimized production images.
* **Level 3 (Current State):** Orchestration of all services (backend, frontend, database) using `docker-compose.yml`.

---

## 🚀 Project Overview (Level 3)

The application architecture is as follows:

| Service | Technology | Port (Container) | Port (Host) | Description |
| :--- | :--- | :--- | :--- | :--- |
| **backend** | Java (Maven/Spring Boot) | `8080` | `8080` | Provides REST API for employee data. |
| **frontend** | React/Angular | `3000` | `3000` | User interface to view and manage employees. |
| **postgres** | PostgreSQL | `5432` | `5432` | Persistence layer for application data. |

All services are connected via a dedicated network managed by Docker Compose, ensuring seamless **inter-container communication** using service names (`backend`, `postgres`).

---

## 🛠 Prerequisites

To run this project, you need to have the following installed:

* **Docker:** Used for building and running containers.
* **Docker Compose:** Used for defining and running multi-container Docker applications.

---

## 📦 Level 1 & 2: Individual Dockerization

This section outlines the key containerization steps, implemented in `Dockerfile` and `Dockerfile.multi` (if applicable) for each service.

### 1. Backend (`dev-pro-backend`)

* **Technology:** Java (Maven-based application, likely Spring Boot).
* **Goal:** Create an image that packages the Java application (`app.jar`).
* **Optimization (Level 2):** Use a **multi-stage build** to separate the build environment (using a full JDK and Maven) from the final runtime environment (using a lightweight JRE base image like `openjdk:17-jre-slim`). This significantly reduces the final image size.
    * **Final Image Command:** `java -jar app.jar`
* **Port:** Exposes **`8080`**.

### 2. Frontend (`dev-pro-frontend`)

* **Technology:** React or Angular.
* **Goal:** Create an image that serves the static web application files.
* **Optimization (Level 2):** Use a **multi-stage build** to separate the build phase (installing dependencies, building the app) from the serving phase (using a lightweight web server like Nginx or a simple static file server).
* **Port:** Exposes **`3000`**.

---

## ✨ Level 3: Orchestration with Docker Compose

The `docker-compose.yml` file is the heart of this setup, defining and linking all three services.

### 1. Database Setup (`postgres`)

The PostgreSQL service is configured with essential environment variables to set up the initial database, user, and password, which the backend uses to connect.

**Environment Variables:**

| Variable | Value | Purpose |
| :--- | :--- | :--- |
| `POSTGRES_USER` | `user` | Database user for connection. |
| `POSTGRES_PASSWORD` | `password` | Password for the database user. |
| `POSTGRES_DB` | `employeesdb` | Database name. |

### 2. Backend Connection

The backend service is configured to connect to the database using the **service name** `postgres` (instead of `localhost` or an IP address).

**Key Connection URL (Example):**
`jdbc:postgresql://postgres:5432/employeesdb`

---

## ▶️ Getting Started

Follow these steps to get the full application stack running using Docker Compose:

1.  **Clone the Repository:** (Assume you have the project files)
    ```bash
    git clone <your-repo-link>
    cd <project-directory>
    ```

2.  **Build and Run the Services:**
    The following command will build the necessary images (if they don't exist), create the network, and start all three containers in detached mode:

    ```bash
    docker-compose up -d --build
    ```

3.  **Verify Running Containers:**
    You can confirm all three containers are running using the command you showed in the terminal:
    ```bash
    docker ps -a
    # Expected output should show backend, frontend, and postgres containers are "Up"
    ```
    
4.  **Access the Application:**

    * **Frontend (UI):** Open your browser and navigate to `http://<EC2_PUBLIC_IP>:3000`
        * *(Your images show the frontend is running, but with no data initially.)*

    * **Backend (API Check):** You can verify the API is accessible and working via the exposed port:
        * `http://<EC2_PUBLIC_IP>:8080/api/v1/employees`
        * *(Your first image confirms the API returns a JSON array, even if the data fields are `null` initially.)*

5.  **Test the API (Postman/cURL):**
    Use a tool like Postman to insert an initial record and confirm the end-to-end flow:

    * **Method:** `POST`
    * **URL:** `http://<EC2_PUBLIC_IP>:8080/api/v1/employees`
    * **Body (JSON):**
        ```json
        {
          "name": "John Doe",
          "location": "USA",
          "department": "IT"
        }
        ```
    * *(Your Postman image confirms a successful `POST` request to the backend.)*
    
---

## 🛑 Stopping the Services

To stop and remove all containers, networks, and volumes created by `docker-compose up`:

```bash
docker-compose downt 