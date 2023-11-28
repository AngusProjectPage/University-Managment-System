package com.example.cs308fx.controllers;

import com.example.cs308fx.Lecturer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class ModuleLecturerController {
    private Lecturer lecturer;

    @FXML
    public void backToMain(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/lecturer.fxml"));
        Parent root = loader.load();

        LecturerController lecController = loader.getController();
        lecController.setLoggedInUser(lecturer);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
