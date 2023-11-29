package com.example.cs308fx.controllers;

import com.example.cs308fx.Module;
import com.example.cs308fx.Lecturer;
import com.example.cs308fx.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

import java.sql.SQLException;

public class UpdateStudentController {

    Lecturer loggedInLecturer;
    Student student;

    Module module;

    @FXML
    Label studentNameLabel;

    @FXML
    TextField labMarkField;

    @FXML
    TextField examMarkField;

    String errorMsg;

    String success;

    @FXML
    Label errMsgExamLabel;

    @FXML
    Label errMsgLabLabel;



    public void init(Lecturer loggedInLecturer, Student student, Module module) {
        this.loggedInLecturer = loggedInLecturer;
        this.student = student;
        this.module = module;
        errorMsg = "Please only enter integer numbers";
        success  = "Field sucessfully updated";
        setStudentNameLabel();
    }

    public void setStudentNameLabel() {
        studentNameLabel.setText("Altering " + student.getFirstName() + " "  + student.getSurname() + " grades for module " + module.getModuleName());
    }

    public void updateExamMark(ActionEvent event) {
    errMsgExamLabel.setText("");
    String examMarkString = examMarkField.getText();
        try {
            int examMark = Integer.parseInt(examMarkString);
            student.addExamMark(examMark, student, module);
            errMsgExamLabel.setTextFill(Color.GREEN);
            errMsgExamLabel.setText(success);
        } catch(NumberFormatException | SQLException e) {
            errMsgExamLabel.setTextFill(Color.RED);
            errMsgExamLabel.setText(errorMsg);
        }
    }

    public void updateLabMark(ActionEvent event) {
        errMsgLabLabel.setText("");
        String labMarkString = labMarkField.getText();
        try {
            int labMark = Integer.parseInt(labMarkString);
            student.addLabMark(labMark, student, module);
            errMsgLabLabel.setTextFill(Color.GREEN);
            errMsgLabLabel.setText(success);
        } catch(NumberFormatException | SQLException e) {
            errMsgLabLabel.setTextFill(Color.RED);
            errMsgLabLabel.setText(errorMsg);
        }
    }


}
