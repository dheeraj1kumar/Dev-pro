# 🐳 Employee Management Application - Full Stack Containerization (AWS EC2 Deployment)

This project demonstrates the containerization of a simple full-stack application using **Docker** and **Docker Compose**. The application consists of a Java (Spring Boot/Maven) backend, a React frontend, and a PostgreSQL database, deployed on **AWS EC2**.

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

## ⚙️ Deployment Environment Setup

This section confirms the cloud infrastructure required to run the containers.

### 1. AWS EC2 Instance Status
The containers are deployed on an AWS EC2 instance.
> *EC2 Instance details:*

### 2. AWS Security Group Configuration
The EC2 Security Group is configured to allow inbound traffic on the required application ports (`3000`, `8080`), as well as standard ports like `22` (SSH) and `80`.
> *Inbound Rules:*

---

## 🛠 Prerequisites

To run this project, you need to have the following installed:
* **EC2:** An active AWS EC2 instance (as shown above) to host the containers.
* **Docker:** Used for building and running containers.
* **Docker Compose:** Used for defining and running multi-container Docker applications.

---

## 📦 Level 1 & 2: Individual Dockerization

This section outlines the key containerization steps, implemented in `Dockerfile` and `Dockerfile.multi` (if applicable) for each service.

### 1. Backend (`dev-pro-backend`)

* **Technology:** Java (Maven-based application, likely Spring Boot).
* **Goal:** Create an image that packages the Java application (`app.jar`).
* **Optimization (Level 2):** Use a **multi-stage build** to separate the build environment (JDK) from the final runtime environment (JRE).
    * **Final Image Command:** `java -jar app.jar`
* **Port:** Exposes **`8080`**.

### 2. Frontend (`dev-pro-frontend`)

* **Technology:** React or Angular.
* **Goal:** Create an image that serves the static web application files.
* **Optimization (Level 2):** Use a **multi-stage build** to separate the build phase (e.g., `npm install`, `npm run build`) from the serving phase (e.g., Nginx).
* **Port:** Exposes **`3000`**.

---

## ✨ Level 3: Orchestration with Docker Compose

The `docker-compose.yml` file defines and links all three services.

### 1. Database Setup (`postgres`)

| Variable | Value | Purpose |
| :--- | :--- | :--- |
| `POSTGRES_USER` | `user` | Database user for connection. |
| `POSTGRES_PASSWORD` | `password` | Password for the database user. |
| `POSTGRES_DB` | `employeesdb` | Database name. |

### 2. Backend Connection

The backend connects to the database using the **service name** `postgres`.

**Key Connection URL (Example):**
`jdbc:postgresql://postgres:5432/employeesdb`

---

## ▶️ Getting Started

Follow these steps to get the full application stack running using Docker Compose on the EC2 instance:

1.  **Clone the Repository:**
    ```bash
    git clone <your-repo-link>
    cd <project-directory>
    ```

2.  **Build and Run the Services:**
    ```bash
    docker-compose up -d --build
    ```

3.  **Verify Running Containers:**
    Confirm all three containers are **Up** and their ports are mapped correctly.
    ```bash
    docker ps -a
    ```
    > *Running Containers:*
    
4.  **Access the Application:**

    * **Frontend (UI):** Open your browser and navigate to `http://<EC2_PUBLIC_IP>:3000`.
        > *Initial UI Load:*
        
    * **Backend (API Check):** Verify the API is accessible via `http://<EC2_PUBLIC_IP>:8080/api/v1/employees`.
        > *API Response:*
        
5.  **Test the API (Postman):**
    Use Postman to confirm the end-to-end flow by inserting a record.

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
    > *Successful POST Request:*
    
---

## 🛑 Stopping the Services

To stop and remove all containers, networks, and volumes created by `docker-compose up`:

```bash
docker-compose down