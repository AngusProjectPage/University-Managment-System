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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    private UserModel user;
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

    public SignupController(UserModel user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleField.setItems(FXCollections.observableArrayList("student", "lecturer", "manager"));
        genderField.setItems(FXCollections.observableArrayList("Male", "Female"));
    }

    public void signUp(ActionEvent event) throws SQLException {
        String firstName = firstNameField.getText();
        String surname   = surnameField.getText();
        String email     = emailField.getText();
        String gender    = genderField.getValue();
        String password  = passwordField.getText();
        String userRole  = roleField.getValue();

        LocalDate date = dateOfBirthField.getValue();
        String dateOfBirth = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        boolean approved = false;

        // Add User to database
        user.addUser(firstName, surname, email, gender, password, userRole, dateOfBirth, approved);
    }

    public void login(ActionEvent event) throws IOException {
        root  = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
