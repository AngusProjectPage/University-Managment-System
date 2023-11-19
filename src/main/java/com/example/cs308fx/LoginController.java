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
        private Label myLabel;
        @FXML
        private TextField myTextField;
        @FXML
        private Button myButton;

        @FXML
        public void login(ActionEvent event) throws IOException {
                try {
                        String username = myTextField.getText();
                        String password = myTextField.getText();
                }
                catch (Exception e) {
                        System.out.println(e);
                }
             /*
                root = FXMLLoader.load(getClass().getResource("student.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

              */
        }
}
