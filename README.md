# MyBatis Assignment 01

This version uses real MyBatis + H2 database storage.

## Structure

- `src/main/java/com/example/student/Main.java` - menu program
- `src/main/java/com/example/student/Student.java` - entity class
- `src/main/java/com/example/student/StudentService.java` - interface
- `src/main/java/com/example/student/StudentServiceImpl.java` - MyBatis service
- `src/main/java/com/example/student/DatabaseInitializer.java` - creates the table and seed row
- `src/main/java/com/example/student/mapper/StudentMapper.java` - MyBatis mapper interface
- `src/main/resources/com/example/student/mapper/StudentMapper.xml` - mapper SQL

## How to run in IDEA

1. Open the project root in IntelliJ IDEA.
2. Wait for Maven to import `pom.xml`.
3. Make sure the Project SDK is Java 17.
4. Run `com.example.student.Main`.
5. A ready-made IDEA run configuration named `Main` is included in `.idea/runConfigurations/Main.xml`.

## Database Inspector Script

Run this in PowerShell from the project root:

```powershell
powershell -ExecutionPolicy Bypass -File scripts\show-database.ps1
```

It prints the table list and the full `students` table.

## What it uses

- MyBatis 3.5.19
- H2 2.4.240
- MySQL Connector/J 9.6.0

## Notes

- The first run creates a local database file under `data/`.
- A sample `John` record is inserted automatically if the table is empty.
- The H2 URL uses `AUTO_SERVER=TRUE`, so the inspector script can read the database while the app is running.
- To switch to MySQL, set `db.type=mysql` in [src/main/resources/db.properties](E:/作业/4.21/src/main/resources/db.properties) and fill in your MySQL username/password.
- If the MySQL account has permission, the app will create `student_db` automatically on first run.
