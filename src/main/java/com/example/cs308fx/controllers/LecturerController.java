package com.example.cs308fx.controllers;

import com.example.cs308fx.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;

public class LecturerController {
    private Lecturer loggedInLecturer;

    public void setLoggedInUser(Lecturer lecturer) {
        this.loggedInLecturer = lecturer;
        updateLecturerDetails();
    }

    @FXML
    private Label lecturerId;

    private void updateLecturerDetails() {
        if (loggedInLecturer != null) {
            lecturerId.setText("Lecturer ID: " + loggedInLecturer.getId());
        }
    }


    private void openUpdatePasswordView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/updatePassword.fxml"));
            Parent root = loader.load();

            UpdatePasswordController updatePasswordController = loader.getController();
            updatePasswordController.setLoggedInUser(loggedInLecturer); // Assuming you have a method like this in UpdatePasswordController

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
