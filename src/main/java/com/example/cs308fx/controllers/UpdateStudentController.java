package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;

import java.sql.SQLException;

public class UpdateStudentController {

    Lecturer loggedInLecturer;
    Student student;

    Module module;

    @FXML
    Label studentNameLabel;

    @FXML
    TextField labMarkField;

    @FXML
    TextField examMarkField;

    String errorMsg;

    public void init(Lecturer loggedInLecturer, Student student, Module module) {
        this.loggedInLecturer = loggedInLecturer;
        this.student = student;
        this.module = module;
        setStudentNameLabel();
    }

    public void setStudentNameLabel() {
        studentNameLabel.setText("Altering " + student.getFirstName() + " "  + student.getSurname() + " grades");
    }

    public void updateExamMark(ActionEvent event) {
    String examMarkString = examMarkField.getText();
        try {
            int examMark = Integer.parseInt(examMarkString);
            student.addExamMark(examMark, student, module);
        } catch(NumberFormatException | SQLException e) {
            errorMsg = "Please only enter floating point numbers";
        }
    }

    public void updateLabMark(ActionEvent event) {
        String labMarkString = labMarkField.getText();
        try {
            int labMark = Integer.parseInt(labMarkString);
            student.addLabMark(labMark, student, module);
        } catch(NumberFormatException | SQLException e) {
            errorMsg = "Please only enter floating point numbers";
        }
    }


}
