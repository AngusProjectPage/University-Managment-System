package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import com.example.cs308fx.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import com.example.cs308fx.UserModel;
import com.example.cs308fx.Manager;

public class ModuleController {

    private Student student;

    private Module currentModule;

    @FXML
    private Label moduleName;

    @FXML
    private TextArea description;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCurrentModule(Module module) {
        this.currentModule = module;
        setLabels();
    }

    private void setLabels() {
        moduleName.setText("Module: " + currentModule.getModuleName());
        description.setText(currentModule.getModuleInfo());
    }

    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/student.fxml"));
        Parent root = loader.load();

        StudentController stuController = loader.getController();
        stuController.setLoggedInUser(student);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
