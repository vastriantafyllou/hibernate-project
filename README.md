# JPA / Hibernate School Management Demo

This is a demonstration project showcasing the core functionalities of **JPA** and **Hibernate** for managing a simple school database. The project is built with **Maven** and utilizes a **MySQL** database, with **Lombok** for reducing boilerplate code.

## Key Features & Concepts

* **Complex Data Modeling**: Implements a data model with entities like `Teacher`, `Course`, `Region`, and `TeacherMoreInfo`.
* **JPA Relationships**: Demonstrates all standard JPA relationship types: `@OneToOne`, `@OneToMany`, and `@ManyToMany`.
* **Bidirectional Relationship Management**: Bidirectional relationships are carefully managed with helper methods within the entities to ensure data consistency across the object model.
* **Advanced Querying**: Implements data retrieval using both **JPQL (Jakarta Persistence Query Language)** and the type-safe **Criteria API**.
* **Security**: All dynamic queries are built using **JPA Parameters** to protect against **SQL Injection** vulnerabilities.
* **Lombok Integration**: Uses Project Lombok annotations (`@NoArgsConstructor`, `@AllArgsConstructor`, `@Getter`, `@Setter`, etc.) to significantly reduce boilerplate code in model classes.
* **Enums**: Utilizes custom `enum` types (`GenderType`, `LessonType`) for well-defined, type-safe fields.

## Technologies Used

* **Java** (JDK 17+)
* **Apache Maven**
* **JPA (Jakarta Persistence)**
* **Hibernate ORM** (JPA Provider)
* **MySQL**
* **Project Lombok**
