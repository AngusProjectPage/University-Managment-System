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

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {

        @FXML
        private TextField username;
        @FXML
        private TextField password;
        @FXML
        private Button myButton;

        @FXML
        private Label errorLabel;

        private UserModel userModel;

        @FXML
        public void initialize() {
                username.setOnKeyPressed(e -> clearErrorMessage());
                password.setOnKeyPressed(e -> clearErrorMessage());
        }

        public void setUserModel(UserModel userModel) {
                this.userModel = userModel;
        }

        @FXML
        private void clearErrorMessage() {
                errorLabel.setText("");
        }

        @FXML
        public void login(ActionEvent event) throws IOException {
                String user = username.getText();
                String pass = password.getText();

                // Extract the role from the first three letters of the username
                String role = "";
                if (user.length() >= 3) {
                        String prefix = user.substring(0, 3).toLowerCase();
                        switch (prefix) {
                                case "mgr":
                                        role = "manager";
                                        break;
                                case "stu":
                                        role = "student";
                                        break;
                                case "lct":
                                        role = "lecturer";
                                        break;
                                default:
                                        errorLabel.setText("Invalid username. Please check your username.");
                                        return;
                        }
                } else {
                        errorLabel.setText("Invalid username. Please check your username.");
                        return;
                }

                try {
                        Person loggedInUser = userModel.login(user, pass, role);

                        if (loggedInUser != null) {
                                String fxmlFile = "";
                                if (loggedInUser instanceof Student) {
                                        fxmlFile = "/com/example/cs308fx/student.fxml";
                                } else if (loggedInUser instanceof Lecturer) {
                                        fxmlFile = "/com/example/cs308fx/lecturer.fxml";
                                } else if (loggedInUser instanceof Manager) {
                                        fxmlFile = "/com/example/cs308fx/manager.fxml";
                                }

                                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                                Parent root = loader.load();

                                if (loggedInUser instanceof Student) {
                                        StudentController studentController = loader.getController();
                                        studentController.setLoggedInUser((Student) loggedInUser);
                                } else if (loggedInUser instanceof Lecturer) {
                                        LecturerController lecturerController = loader.getController();
                                        lecturerController.setLoggedInUser((Lecturer) loggedInUser);
                                } else if (loggedInUser instanceof Manager) {
                                        ManagerController managerController = loader.getController();
                                        managerController.setLoggedInUser((Manager) loggedInUser);
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
                        // Handle SQLException
                }
        }



        @FXML
        public void signup(ActionEvent event) throws IOException {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("../signup.fxml"));
                Parent root = loader.load();

                // Get the controller instance from the FXMLLoader
                SignupController signupController = loader.getController();

                // Pass the userModel to the signupController
                signupController.setUserModel(userModel);

                // Set up the stage and scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
        }

}
