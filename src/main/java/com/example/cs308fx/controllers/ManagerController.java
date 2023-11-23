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
import java.util.List;

import com.example.cs308fx.UserModel;
import com.example.cs308fx.Manager;
public class ManagerController {

    private Manager loggedInManager;

    public void setLoggedInUser(Manager manager) {
        this.loggedInManager = manager;
        updateManagerDetails();
        populateUsersComboBox();
    }

    private void updateManagerDetails() {
        if (loggedInManager != null) {
            // Use loggedInManager's details to update the UI
            managerIdLabel.setText("Manager ID: " + loggedInManager.getId());
            // Other UI updates or logic based on manager's details
        }
    }

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseDescriptionField;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private TextField moduleNameField;

    @FXML
    private TextField moduleCreditField;

    @FXML
    private ComboBox<String> usersComboBox;

    public void populateUsersComboBox() {
        try {
            List<String> unapprovedUsers = loggedInManager.getUnapprovedUsers();
            usersComboBox.getItems().clear();
            usersComboBox.getItems().addAll(unapprovedUsers);
        } catch (SQLException e) {
            // Handle SQL Exception
            e.printStackTrace();
        }
    }

    @FXML
    private Label managerIdLabel;

    @FXML
    private void handleApproveButtonAction() {
        String selectedStudent = usersComboBox.getValue();
        String[] parts = selectedStudent.split(" - ");
        String username = parts[0];
        int userId = Integer.parseInt(username.replaceAll("[^\\d]", "")); // Extracts numeric part, assuming the ID is numeric
        String role = "";

        if (username.startsWith("stu")) {
            role = "student";
        } else if (username.startsWith("lct")) {
            role = "lecturer";
        }

        try {
            loggedInManager.approveUser(userId, role);
            updateFeedback("User approved successfully.");
            populateUsersComboBox();
        } catch (SQLException e) {
            updateFeedback("Error: Unable to approve user.");
            e.printStackTrace();
        }
    }

    @FXML
    private Label feedbackLabel;

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

    @FXML
    private void handleAddCourseAction() {
        String courseCode = courseCodeField.getText();
        String courseName = courseNameField.getText();
        String courseDescription = courseDescriptionField.getText();

        try {
            loggedInManager.addCourse(courseCode, courseName, courseDescription);
            updateFeedback("Course added successfully.");
        } catch (SQLException e) {
            updateFeedback("Error adding course: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddModuleAction() {
        String moduleCode = moduleCodeField.getText();
        String moduleName = moduleNameField.getText();
        String moduleCredit = moduleCreditField.getText();

        try {
            loggedInManager.addModule(moduleCode, moduleName, moduleCredit);
            updateFeedback("Module added successfully.");
        } catch (SQLException e) {
            updateFeedback("Error adding module: " + e.getMessage());
        }
    }

    @FXML
    private void openUpdatePasswordView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/UpdatePassword.fxml"));
            Parent root = loader.load();

            UpdatePasswordController updatePasswordController = loader.getController();
            updatePasswordController.setLoggedInUser(loggedInManager); // Assuming you have a method like this in UpdatePasswordController

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
            updateFeedback("Error: Unable to open the update password view.");
        }
    }


}
