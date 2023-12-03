package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import com.example.cs308fx.Student;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModuleController {

    private Student student;

    private Module currentModule;

    @FXML
    private Label moduleName;

    @FXML
    private TextArea description;
    @FXML
    private ComboBox<String> weeks;

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCurrentModule(Module module) {
        this.currentModule = module;
        setLabels();
    }

    public void setWeeks() {
        weeks.setItems(FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"));
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

    @FXML
    public void downloadLabNote() throws SQLException, IOException {
        int week = Integer.parseInt(weeks.getValue());
        Blob labNote = student.downloadLabNote(week, currentModule.getModuleId());
        InputStream inputStream = labNote.getBinaryStream();
        String home = System.getProperty("user.home");
        OutputStream outputStream = new FileOutputStream(home + "/Downloads/labnote.pdf");

        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }

    @FXML
    public void downloadLectureNote() throws SQLException, IOException {
        int week = Integer.parseInt(weeks.getValue());
        Blob lectureNote = student.downloadLectureNote(week, currentModule.getModuleId());
        InputStream inputStream = lectureNote.getBinaryStream();
        String home = System.getProperty("user.home");
        OutputStream outputStream = new FileOutputStream(home + "/Downloads/lecturenote.pdf");

        int bytesRead = -1;
        byte[] buffer = new byte[4096];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }
}
