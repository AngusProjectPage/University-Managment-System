package com.example.cs308fx.controllers;

import com.example.cs308fx.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class UpdatePasswordManagerController {

    private Manager loggedInManager;
    @FXML
    private TextField userIdField;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;
    @FXML
    private Label feedbackLabel;

    public void setLoggedInUser(Manager manager) {
        this.loggedInManager = manager;
    }

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

    @FXML
    private void handleUpdatePassword() {
        String userIdText = userIdField.getText();
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (!newPassword.equals(confirmNewPassword)) {
            updateFeedback("Passwords do not match.");
            return;
        }

        try {
            loggedInManager.updatePassword(userIdText, newPassword);
            updateFeedback("Password updated successfully.");
        } catch (IllegalArgumentException e) {
            updateFeedback("Invalid user ID.");
            e.printStackTrace();
        } catch (SQLException e) {
            updateFeedback("Failed to update password: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    private void handleReturnToManager(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/manager.fxml"));
            Parent root = loader.load();


            ManagerController managerController = loader.getController();
            managerController.setLoggedInUser(loggedInManager);
            managerController.populateCombos();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
        }
    }

}

