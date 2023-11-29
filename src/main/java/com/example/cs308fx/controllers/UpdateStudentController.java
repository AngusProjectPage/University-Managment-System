package com.example.cs308fx.controllers;

import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Student;

public class UpdateStudentController {

    Lecturer loggedInLecturer;
    Student student;

    public void init(Lecturer loggedInLecturer, Student student) {
        this.loggedInLecturer = loggedInLecturer;
        this.student = student;
    }
}
