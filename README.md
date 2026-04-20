# MyBatis Assignment 01

Console-based Student Management System with real MyBatis + database storage.

## Overview

This project implements a student CRUD system for the MyBatis assignment. It supports adding, viewing, updating, and deleting students from a database.

## Features

- Console menu with CRUD operations
- MyBatis mapper-based database access
- Automatic table creation on first run
- Sample `John` record inserted when the table is empty
- Database inspector script for showing table data

## Tech Stack

- Java 17
- MyBatis 3.5.19
- H2 2.4.240
- MySQL Connector/J 9.6.0

## Project Structure

- `src/main/java/com/example/student/Main.java` - menu program
- `src/main/java/com/example/student/Student.java` - entity class
- `src/main/java/com/example/student/StudentService.java` - service interface
- `src/main/java/com/example/student/StudentServiceImpl.java` - MyBatis service implementation
- `src/main/java/com/example/student/DatabaseInitializer.java` - creates table and inserts seed data
- `src/main/java/com/example/student/DbInspector.java` - prints database tables and rows
- `src/main/java/com/example/student/mapper/StudentMapper.java` - mapper interface
- `src/main/resources/com/example/student/mapper/StudentMapper.xml` - SQL mapping

## Run in IDEA

1. Open the project root in IntelliJ IDEA.
2. Wait for Maven to import `pom.xml`.
3. Set the Project SDK to Java 17 or higher.
4. Run `com.example.student.Main`.
5. A ready-made run configuration named `Main` is included in `.idea/runConfigurations/Main.xml`.

## Database Inspector Script

Use this PowerShell command from the project root:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\show-database.ps1
```

It will compile the project and print the table list plus the full `students` table.

## Database Notes

- Default mode: H2
- H2 database file: `data/student_db.mv.db`
- To switch to MySQL, set `db.type=mysql` in `src/main/resources/db.properties`
- If the MySQL account has permission, the app will create `student_db` automatically

## Video Demo Outline

1. Show the GitHub repository.
2. Run the main program and demonstrate add, view, update, delete.
3. Run the database inspector script.
4. Explain one method, such as `insertStudent()` in `StudentServiceImpl`.

## Submission Reminder

- Repository URL: your GitHub/Gitee link
- Video length: 5 to 8 minutes
- Submission deadline: 22 April 2026
