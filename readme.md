# 🛠️ Helpdesk Management Application

A full-featured **Helpdesk Management System** built using **Spring Boot** that allows users to create, manage, and track support tickets efficiently.

---

## 🚀 Features

* 🔐 User Authentication & Authorization
* 🎫 Create, Update, and Manage Tickets
* 📊 Admin Dashboard
* 👥 User Management
* 🏢 Office & Hardware Type Management
* 📌 Ticket Status Tracking
* ⚠️ Global Exception Handling

---

## 🧰 Tech Stack

* **Backend:** Java, Spring Boot
* **Frontend:** Thymeleaf, HTML, CSS
* **Build Tool:** Maven
* **Database:** (Add your DB here, e.g., MySQL / H2)
* **Security:** Spring Security

---

## 📁 Project Structure

```
HelpdeskManagementApplication/
├── src/
│   ├── main/
│   │   ├── java/com/terracis/helpdeskmanagementapp/
│   │   │   ├── configuration/        # Security & app configuration
│   │   │   ├── controller/           # MVC Controllers (Auth, Ticket, User, Dashboard)
│   │   │   ├── service/              # Business logic layer
│   │   │   ├── repo/                 # JPA repositories (database access)
│   │   │   ├── entity/               # Entity classes (User, Ticket, etc.)
│   │   │   ├── exception/            # Custom exceptions & handlers
│   │   │   └── HelpdeskApplication.java  # Main Spring Boot class
│   │   └── resources/
│   │       ├── templates/            # Thymeleaf HTML pages
│   │       ├── static/               # CSS, JS, static assets
│   │       └── application.properties # App configuration
│   └── test/
│       └── java/...                  # Unit tests
├── pom.xml                           # Maven dependencies
├── mvnw / mvnw.cmd                   # Maven wrapper
└── .gitignore
```


## ⚙️ Setup & Run Locally

### 1️⃣ Clone the repository

```bash
git clone https://github.com/rajibbirlapur/HelpdeskManagementApplication.git
```

### 2️⃣ Navigate to project folder

```bash
cd HelpdeskManagementApplication
```

### 3️⃣ Run the application

```bash
./mvnw spring-boot:run
```

Or using IDE:

* Open in Spring Tool Suite / IntelliJ / VS Code
* Run `HelpdeskApplication.java`

---

## 🌐 Application Access

Once started, open:

```
http://localhost:8080
```

---

## 🔐 Default Roles (if applicable)

* **Admin**
* **User**

(Add default credentials if you have any)

---

## 📌 Future Improvements

* REST API support
* JWT Authentication
* Email notifications
* File attachments for tickets
* Deployment on cloud (AWS/Render)

---

## 🤝 Contributing

Contributions are welcome! Feel free to fork the repo and submit a pull request.

---

## 📄 License

This project is open-source and available under the MIT License.

---

## 👨‍💻 Author

**Rajib Das**

GitHub: https://github.com/rajibbirlapur

---

⭐ If you like this project, give it a star!
