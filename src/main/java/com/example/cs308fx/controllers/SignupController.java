package com.example.cs308fx.controllers;


import com.example.cs308fx.UserModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    private UserModel userModel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private ComboBox<String> genderField;
    @FXML
    private DatePicker dateOfBirthField;
    @FXML
    private ComboBox<String> roleField;
    @FXML
    private Label errorLabel;
    @FXML
    private void clearErrorMessage() {
        errorLabel.setText("");
    }
    private void clearForm() {
        firstNameField.clear();
        surnameField.clear();
        emailField.clear();
        passwordField.clear();
        genderField.getSelectionModel().clearSelection();
        roleField.getSelectionModel().clearSelection();
        dateOfBirthField.setValue(null);
        // reset the focus to the first field
        firstNameField.requestFocus();
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleField.setItems(FXCollections.observableArrayList("student", "lecturer"));
        genderField.setItems(FXCollections.observableArrayList("M", "F"));
    }

    @FXML
    public void signUp(ActionEvent event) {
        try {
            String firstName = firstNameField.getText();
            String surname = surnameField.getText();
            String email = emailField.getText();
            String gender = genderField.getValue();
            String password = passwordField.getText();
            String userRole = roleField.getValue();
            LocalDate date = dateOfBirthField.getValue();

            if (date != null && !firstName.isEmpty() && !surname.isEmpty() && !email.isEmpty() && gender != null && !password.isEmpty() && userRole != null) {
                userModel.addUser(firstName, surname, password, gender, email, userRole, date);
                clearForm();
                errorLabel.setText("User added successfully!");
            } else {
                errorLabel.setText("Please fill in all fields.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorLabel.setText("Error adding user: " + e.getMessage());
        }
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/login.fxml"));
        root = loader.load();
        LoginController loginController = loader.getController();
        loginController.setUserModel(userModel);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
