package com.example.cs308fx.controllers;

import com.example.cs308fx.*;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public boolean validatePassword(String password) {
        String passwordRegex = "^.{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
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
            if(!validatePassword(newPassword)) {
                updateFeedback("Password must be at least 8 characters in length");
                return;
            }
            loggedInUser.updatePassword(loggedInUser, newPassword);
            updateFeedback("Password updated successfully.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();

            loginController.setUserModel(new UserModel());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IllegalArgumentException e) {
            updateFeedback("Invalid user ID.");
            e.printStackTrace();
        } catch (SQLException e) {
            updateFeedback("Failed to update password: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void back(ActionEvent event) throws IOException {
        if(loggedInUser.getUsername().startsWith("stu")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/student.fxml"));
            Parent root = loader.load();

            StudentController studentController = loader.getController();

            studentController.setLoggedInUser((Student) loggedInUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/lecturer.fxml"));
            Parent root = loader.load();

            LecturerController lecturerController = loader.getController();

            lecturerController.setLoggedInUser((Lecturer) loggedInUser);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
