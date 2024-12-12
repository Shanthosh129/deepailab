# deepailab
# Order Management Application

This is a Dockerized application for managing orders. It uses **Spring Boot** for the backend and connects to an external MySQL database. This guide explains how to set up and run the application.

---

## Prerequisites

- [Docker](https://www.docker.com/products/docker-desktop/) installed on your system.
- A `.env` file for storing sensitive configuration details (see below).

---

## Environment Variables (`.env` File)

To securely configure sensitive data like secret keys and database credentials, you must create a `.env` file in the root directory of the project.

### **Steps to Create the `.env` File**
1. In the project directory, create a new file named `.env`.
2. Add the following content to the `.env` file:
   ```plaintext
   SECRET_KEY=5B6qM3E9zXr2vP1nA4kL7sT6uN3wQ8yH
   

