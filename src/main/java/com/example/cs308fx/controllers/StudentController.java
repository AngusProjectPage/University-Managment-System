package com.example.cs308fx.controllers;

import com.example.cs308fx.Manager;
import com.example.cs308fx.Student;
import com.example.cs308fx.UserModel;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudentController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label studentIdLabel;
    @FXML
    private Label courseLabel;

    private Student loggedInStudent;


    public void setLoggedInUser(Student student) {
        this.loggedInStudent = student;
        updateLabels();
    }

    private void updateLabels() {
        studentIdLabel.setText("Student ID: " + loggedInStudent.getId());
        courseLabel.setText("Course: " + loggedInStudent.getCourseName());
    }
}
