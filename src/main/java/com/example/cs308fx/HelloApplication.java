package com.example.cs308fx;

import com.example.cs308fx.controllers.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create the userModel
        UserModel userModel = new UserModel();

        // Load the login.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent root = loader.load();

        // Get the controller instance from the FXMLLoader
        LoginController loginController = loader.getController();

        // Pass the userModel to the loginController
        loginController.setUserModel(userModel);

        // Set up the stage and scene
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}