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
public class ManagerController {

    private UserModel userModel;
    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
        updateManagerIdLabel();
        populateUsersComboBox();
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
            List<String> unapprovedStudents = userModel.getUnapprovedStudents();
            usersComboBox.getItems().clear();
            usersComboBox.getItems().addAll(unapprovedStudents);
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
        if (selectedStudent != null) {
            int studentId = Integer.parseInt(selectedStudent.split(" - ")[0]);
            try {
                userModel.approveStudent(studentId);
                updateFeedback("User approved successfully.");
                populateUsersComboBox();
            } catch (SQLException e) {
                updateFeedback("Error: Unable to approve user.");
                e.printStackTrace();
            }
        }
    }

    @FXML
    private Label feedbackLabel;

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

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
        String courseCode = courseCodeField.getText();
        String courseName = courseNameField.getText();
        String courseDescription = courseDescriptionField.getText();

        try {
            userModel.addCourse(courseCode, courseName, courseDescription);
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
            userModel.addModule(moduleCode, moduleName, moduleCredit);
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
            updatePasswordController.setUserModel(userModel);

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
