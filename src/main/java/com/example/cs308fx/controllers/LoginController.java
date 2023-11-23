package com.example.cs308fx.controllers;

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
import java.sql.SQLException;
import com.example.cs308fx.UserModel;

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
        private Label errorLabel;

        @FXML
        public void initialize() {
                username.setOnKeyPressed(e -> clearErrorMessage());
                password.setOnKeyPressed(e -> clearErrorMessage());
        }

        @FXML
        private void clearErrorMessage() {
                errorLabel.setText("");
        }

        private UserModel userModel = new UserModel();

        @FXML
        public void login(ActionEvent event) throws IOException {
                String user = username.getText();
                String pass = password.getText();
                String role;

                // Determine role by username prefix
                if (user.startsWith("mgr")) {
                        role = "manager";
                } else if (user.startsWith("stu")) {
                        role = "student";
                } else if (user.startsWith("lct")) {
                        role = "lecturer";
                } else {
                        // Handle unknown role or show error message
                        errorLabel.setText("Unknown username prefix. Cannot determine role.");
                        return; // Stop further processing
                }

                try {
                        userModel.login(user, pass, role);

                        if (userModel.isLoginSuccessful()) {
                                String fxmlFile = switch (role) {
                                    case "manager" -> "/com/example/cs308fx/manager.fxml";
                                    case "student" -> "/com/example/cs308fx/student.fxml";
                                    case "lecturer" -> "/com/example/cs308fx/lecturer.fxml";
                                    default -> "";
                                };

                                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                                Parent root = loader.load();

                                switch (role) {
                                        case "manager":
                                                ManagerController managerController = loader.getController();
                                                managerController.setUserModel(userModel);
                                                break;
                                        case "student":
                                                StudentController studentController = loader.getController();
                                                studentController.setUserModel(userModel);
                                                break;
                                                /*
                                        case "lecturer":
                                                LecturerController lecturerController = loader.getController();
                                                lecturerController.setUserModel(userModel);
                                                break;
                                                */
                                }

                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();

                        } else {

                                errorLabel.setText("Login failed. Please check your username and password.");

                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                        // Handle SQLException (e.g., show an error dialog).
                }

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
