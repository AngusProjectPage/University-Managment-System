package com.example.cs308fx.controllers;

import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Module;
import com.example.cs308fx.Student;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ModuleLecturerController {
    private Lecturer loggedInLecturer;
    public void setLecturer(Lecturer lecturer) {
        this.loggedInLecturer = lecturer;
    }

    private Module lecturerModule;

    public void setCurrentLecturerModule(Module lecturerModule) {
        this.lecturerModule = lecturerModule;
        updateModuleDetails();
        enrolledStudents();
        setWeeks();
    }

    @FXML
    private Label moduleName;

    @FXML
    private TextArea moduleInfo;

    @FXML
    private Label feedbackLabel;

    @FXML
    private ListView<Button> enrolledStudentsList;

    @FXML
    private ComboBox<String> weeks;

    private FileChooser fileChooser = new FileChooser();

    public void setWeeks() {
        weeks.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
    }

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

    private void updateModuleDetails() {
        moduleName.setText("Module: " + lecturerModule.getModuleName());
        moduleInfo.setText(lecturerModule.getModuleInfo());
    }

    @FXML
    public void uploadNotes(ActionEvent event) {
        String week = weeks.getValue();
        Button btn = (Button) event.getSource();

        fileChooser.setTitle("Upload " + btn.getText());
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            // Now you insert the file content into the database
            loggedInLecturer.uploadNote(file, week, btn.getId().equals("uploadLabNotes") ? "lab" : "lecture", lecturerModule.getModuleId());
        } else {
            updateFeedback("No file selected!");
        }
    }


    @FXML
    private void handleUpdateInfoAction() {
        Integer moduleCode = lecturerModule.getModuleId();
        String moduleInfoTA = moduleInfo.getText();
        loggedInLecturer.updateModuleInfo(moduleCode, moduleInfoTA);
        updateFeedback("Updated!");
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

    public void goToUpdateStudent(Student student, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/updateStudent.fxml"));
        Parent moduleView = loader.load();

        UpdateStudentController updateStudentController = loader.getController();
        updateStudentController.init(loggedInLecturer, student, lecturerModule);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(moduleView);
        stage.setScene(scene);
        stage.show();
    }

    public void enrolledStudents() {
        List<Student> students = loggedInLecturer.getStudentsForModule(lecturerModule.getModuleId());

        // Clear previous items
        enrolledStudentsList.getItems().clear();

        // Convert each Module to a string and attach an event handler to it
        students.forEach(student -> {
            Button studentButton = new Button(student.getFirstName() + " " + student.getSurname());
            studentButton.setOnAction(event -> {
                try {
                    goToUpdateStudent(student, event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            enrolledStudentsList.getItems().add(studentButton);
        });
    }


}
