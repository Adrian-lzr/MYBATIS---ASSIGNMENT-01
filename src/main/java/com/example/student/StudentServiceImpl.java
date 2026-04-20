package com.example.student;

import com.example.student.mapper.StudentMapper;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    @Override
    public void insertStudent(Student s) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.insertStudent(s);
            session.commit();
        }
    }

    @Override
    public Student getStudentByRollNumber(int roll_no) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            return mapper.getStudentByRollNumber(roll_no);
        }
    }

    @Override
    public List<Student> getAllStudents() {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            return mapper.getAllStudents();
        }
    }

    @Override
    public void updateStudent(Student s) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.updateStudent(s);
            session.commit();
        }
    }

    @Override
    public void deleteStudent(int id) {
        try (SqlSession session = MyBatisUtil.getSqlSessionFactory().openSession()) {
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            mapper.deleteStudent(id);
            session.commit();
        }
    }
}
