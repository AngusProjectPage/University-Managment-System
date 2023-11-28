package com.example.cs308fx.controllers;

import com.example.cs308fx.Manager;
import com.example.cs308fx.Person;
import com.example.cs308fx.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class UpdatePasswordController {

    private Person loggedInUser;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private Label feedbackLabel;

    public void setLoggedInUser(Person user) {
        this.loggedInUser = user;
    }

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

    @FXML
    public void updatePassword(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (!newPassword.equals(confirmNewPassword)) {
            updateFeedback("Passwords do not match.");
            return;
        }

        try {
            loggedInUser.updatePassword(loggedInUser, newPassword);
            updateFeedback("Password updated successfully.");
        } catch (IllegalArgumentException e) {
            updateFeedback("Invalid user ID.");
            e.printStackTrace();
        } catch (SQLException e) {
            updateFeedback("Failed to update password: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
