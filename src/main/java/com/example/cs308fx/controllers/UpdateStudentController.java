package com.example.cs308fx.controllers;

import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class UpdateStudentController {

    Lecturer loggedInLecturer;
    Student student;

    @FXML
    Label studentNameLabel;

    public void init(Lecturer loggedInLecturer, Student student) {
        this.loggedInLecturer = loggedInLecturer;
        this.student = student;
        setStudentNameLabel();
    }

    public void setStudentNameLabel() {
        studentNameLabel.setText("Altering " + student.getFirstName() + " "  + student.getSurname() + " grades");
    }

    public void updateExamMark(ActionEvent event) {

    }

    public void updateLabMark(ActionEvent event) {

    }


}
