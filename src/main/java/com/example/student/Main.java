package com.example.student;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseInitializer.initialize();

        try (Scanner scanner = new Scanner(System.in)) {
            StudentService service = new StudentServiceImpl();

            while (true) {
                printMenu();
                int choice = readInt(scanner, "Enter choice: ");

                switch (choice) {
                    case 1:
                        addStudent(scanner, service);
                        break;
                    case 2:
                        viewAllStudents(scanner, service);
                        break;
                    case 3:
                        updateStudent(scanner, service);
                        break;
                    case 4:
                        deleteStudent(scanner, service);
                        break;
                    case 5:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("===== STUDENT MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Student");
        System.out.println("2. View All Students");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Exit");
    }

    private static void addStudent(Scanner scanner, StudentService service) {
        String name = readText(scanner, "Enter Name: ");
        String address = readText(scanner, "Enter Address: ");
        LocalDate dob = readDate(scanner, "Enter DOB (yyyy-MM-dd): ");
        String grade = readText(scanner, "Enter Grade: ");
        String gender = readGender(scanner, "Enter Gender (MALE/FEMALE): ");

        Student student = new Student(0, name, address, dob, grade, gender);
        service.insertStudent(student);

        System.out.println("Student Added!");
        pause(scanner);
    }

    private static void viewAllStudents(Scanner scanner, StudentService service) {
        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students found.");
            pause(scanner);
            return;
        }

        System.out.printf("%-5s %-15s %-15s %-12s %-8s %-8s%n",
                "ID", "Name", "Address", "DOB", "Grade", "Gender");
        for (Student student : students) {
            System.out.printf("%-5d %-15s %-15s %-12s %-8s %-8s%n",
                    student.getId(),
                    student.getName(),
                    student.getAddress(),
                    student.getDobText(),
                    student.getGrade(),
                    student.getGender());
        }

        pause(scanner);
    }

    private static void updateStudent(Scanner scanner, StudentService service) {
        int id = readInt(scanner, "Enter Roll No to Update: ");
        Student existing = service.getStudentByRollNumber(id);
        if (existing == null) {
            System.out.println("Student not found.");
            pause(scanner);
            return;
        }

        String name = readText(scanner, "Enter New Name: ");
        String address = readText(scanner, "Enter New Address: ");
        LocalDate dob = readDate(scanner, "Enter DOB (yyyy-MM-dd): ");
        String grade = readText(scanner, "Enter New Grade: ");
        String gender = readGender(scanner, "Gender (MALE/FEMALE): ");

        service.updateStudent(new Student(id, name, address, dob, grade, gender));
        System.out.println("Student Updated!");
        pause(scanner);
    }

    private static void deleteStudent(Scanner scanner, StudentService service) {
        int id = readInt(scanner, "Enter Roll No to Delete: ");
        Student existing = service.getStudentByRollNumber(id);
        if (existing == null) {
            System.out.println("Student not found.");
            pause(scanner);
            return;
        }

        service.deleteStudent(id);
        System.out.println("Student Deleted!");
        pause(scanner);
    }

    private static String readText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static LocalDate readDate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException ex) {
                System.out.println("Please enter a valid date in yyyy-MM-dd format.");
            }
        }
    }

    private static String readGender(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim().toUpperCase(Locale.ROOT);
            if ("MALE".equals(value) || "FEMALE".equals(value)) {
                return value;
            }
            System.out.println("Please enter MALE or FEMALE.");
        }
    }

    private static void pause(Scanner scanner) {
        System.out.println();
        System.out.print("Press ENTER to go back to menu...");
        scanner.nextLine();
        System.out.println();
    }
}
