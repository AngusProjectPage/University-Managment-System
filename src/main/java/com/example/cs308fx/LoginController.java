package com.example.cs308fx;

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

import java.io.IOException;

public class LoginController {

        private Stage stage;
        private Scene scene;
        private Parent root;

        @FXML
        private TextField username;
        @FXML
        private TextField password;
        @FXML
        private Button myButton;

        @FXML
        public void login(ActionEvent event) throws IOException {
                try {
                        String user = username.getText();
                        String pass = password.getText();
                        System.out.println(user);
                        System.out.println(pass);
                }
                catch (Exception e) {
                        System.out.println(e);
                }

                root = FXMLLoader.load(getClass().getResource("student.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

        @FXML
        public void signup(ActionEvent event) throws IOException {
                root = FXMLLoader.load(getClass().getResource("signup.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }
}
