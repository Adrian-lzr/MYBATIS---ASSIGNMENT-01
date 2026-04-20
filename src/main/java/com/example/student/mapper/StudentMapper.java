package com.example.student.mapper;

import com.example.student.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    int insertStudent(Student student);

    Student getStudentByRollNumber(@Param("roll_no") int rollNo);

    List<Student> getAllStudents();

    int updateStudent(Student student);

    int deleteStudent(@Param("id") int id);
}
