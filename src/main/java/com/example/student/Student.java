package com.example.student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Student {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    private int id;
    private String name;
    private String address;
    private LocalDate dob;
    private String grade;
    private String gender;

    public Student() {
    }

    public Student(int id, String name, String address, LocalDate dob, String grade, String gender) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dob = dob;
        this.grade = grade;
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDobText() {
        return dob == null ? "" : dob.format(DATE_FORMAT);
    }
}
