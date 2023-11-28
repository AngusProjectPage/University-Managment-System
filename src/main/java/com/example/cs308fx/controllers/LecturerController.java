package com.example.cs308fx.controllers;

import com.example.cs308fx.*;
import com.example.cs308fx.Module;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public class LecturerController {
    private Lecturer loggedInLecturer;

    public void setLoggedInUser(Lecturer lecturer) {
        this.loggedInLecturer = lecturer;
        updateLecturerDetails();
        populateModules();
    }

    @FXML
    private Label lecturerId;
    @FXML
    private ListView<Button> moduleListView;

    private void updateLecturerDetails() {
        if (loggedInLecturer != null) {
            lecturerId.setText("Lecturer ID: " + loggedInLecturer.getId());
        }
    }


    public void updatePassword(ActionEvent event) {
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

    private void populateModules() {
        String lecturerId = loggedInLecturer.getId();
        lecturerId = lecturerId.replaceAll("\\D+", "");
        lecturerId = lecturerId.replaceFirst("^0+(?!$)", "");
        List<Module> modules = loggedInLecturer.getModulesForLecturer(lecturerId);

        // Clear previous items
        moduleListView.getItems().clear();

        // Convert each Module to a string and attach an event handler to it
        modules.forEach(module -> {
            Button moduleButton = new Button(module.getModuleName());
            moduleButton.setOnAction(event -> {
                try {
                    goToModule(module, event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            moduleListView.getItems().add(moduleButton);
        });
    }


    public void goToModule(Module module, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/moduleLecturer.fxml"));
        Parent moduleView = loader.load();

        ModuleLecturerController moduleLecturerController = loader.getController();
        moduleLecturerController.setCurrentLecturerModule(module);
        moduleLecturerController.setLecturer(loggedInLecturer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(moduleView);
        stage.setScene(scene);
        stage.show();
    }

}
