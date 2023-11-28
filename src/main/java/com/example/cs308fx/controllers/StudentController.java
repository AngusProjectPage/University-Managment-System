package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import com.example.cs308fx.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class StudentController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label studentIdLabel;
    @FXML
    private Label courseLabel;
    @FXML
    private Label decision;
    @FXML
    private Label name;
    @FXML
    private ListView<Button> moduleListView;
    private Student loggedInStudent;


    public void setLoggedInUser(Student student) {
        this.loggedInStudent = student;
        updateLabels();
        populateModules();
    }

    private void updateLabels() {
        studentIdLabel.setText("Student ID: " + loggedInStudent.getId());
        courseLabel.setText("Course: " + loggedInStudent.getCourseName());
        decision.setText("Decision: " + loggedInStudent.getDecision());
        name.setText("Name: " + loggedInStudent.getFirstName() + " " + loggedInStudent.getSurname());
    }

    private void populateModules() {
        String studentId = loggedInStudent.getId();
        studentId = studentId.replaceAll("\\D+", "");
        studentId = studentId.replaceFirst("^0+(?!$)", "");
        List<Module> modules = loggedInStudent.getModulesForStudent(studentId);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/module.fxml"));
        Parent moduleView = loader.load();

        ModuleController moduleController = loader.getController();
        moduleController.setCurrentModule(module);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(moduleView);
        stage.setScene(scene);
        stage.show();
    }


    public void updatePassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/updatePassword.fxml"));
            Parent root = loader.load();

            UpdatePasswordController updatePasswordController = loader.getController();
            updatePasswordController.setLoggedInUser(loggedInStudent); // Assuming you have a method like this in UpdatePasswordController

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
