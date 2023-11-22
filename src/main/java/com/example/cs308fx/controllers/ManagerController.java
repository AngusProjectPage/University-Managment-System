package com.example.cs308fx.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

import com.example.cs308fx.UserModel;
public class ManagerController {

    private UserModel userModel;
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        updateManagerIdLabel();
    }

    @FXML
    private Button addCourseButton;

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseDescriptionField;

    @FXML
    private Button addModuleButton;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private TextField moduleNameField;

    @FXML
    private TextField moduleCreditField;

    @FXML
    private ComboBox<String> usersComboBox;

    @FXML
    private Label managerIdLabel;

    @FXML
    private Button updatePasswordButton;

    private void updateManagerIdLabel() {
        // Check if userModel is not null and login was successful
        if (userModel != null && userModel.isLoginSuccessful()) {
            int managerId = userModel.getId();
            managerIdLabel.setText("Manager ID: " + managerId);
        } else {
            managerIdLabel.setText("Manager ID: Not Available");
        }
    }

    // Example of an event handler method
    @FXML
    private void handleAddCourseAction() {
        // Handle add course action
    }

}
