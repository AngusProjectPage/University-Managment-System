package com.example.cs308fx.controllers;

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

    private UserModel userModel;

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        updateLabels();
    }

    private void updateLabels() {
        studentIdLabel.setText("Student ID: " + userModel.getId());
        courseLabel.setText("Course: " + userModel.getCourse().getName());
    }
}
