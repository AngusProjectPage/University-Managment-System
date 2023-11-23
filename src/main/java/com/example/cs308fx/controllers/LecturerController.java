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
}
