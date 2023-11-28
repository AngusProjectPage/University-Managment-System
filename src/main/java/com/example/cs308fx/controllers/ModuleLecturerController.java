package com.example.cs308fx.controllers;

import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Module;
import com.example.cs308fx.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;

public class ModuleLecturerController {
    private Lecturer loggedInLecturer;
    public void setLecturer(Lecturer lecturer) {
        this.loggedInLecturer = lecturer;
    }

    private Module lecturerModule;

    public void setCurrentLecturerModule(Module lecturerModule) {
        this.lecturerModule = lecturerModule;
        updateModuleDetails();
    }

    @FXML
    private Label moduleName;

    private void updateModuleDetails() {
        moduleName.setText("Module: " + lecturerModule.getModuleName());

    }


    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/lecturer.fxml"));
        Parent root = loader.load();

        LecturerController lecturerController = loader.getController();
        lecturerController.setLoggedInUser(loggedInLecturer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
