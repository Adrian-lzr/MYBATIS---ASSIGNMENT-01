package com.example.student;

import java.util.List;

public interface StudentService {
    void insertStudent(Student s);

    Student getStudentByRollNumber(int roll_no);

    List<Student> getAllStudents();

    void updateStudent(Student s);

    void deleteStudent(int id);
}
