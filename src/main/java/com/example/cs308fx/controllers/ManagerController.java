package com.example.cs308fx.controllers;

import com.example.cs308fx.Course;
import com.example.cs308fx.MySqlConnect;
import com.example.cs308fx.Module;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.cs308fx.Manager;

public class ManagerController {

    private Manager loggedInManager;

    public void setLoggedInUser(Manager manager) {
        this.loggedInManager = manager;
        updateManagerDetails();
        populateUsersComboBox();
    }

    private void updateManagerDetails() {
        if (loggedInManager != null) {
            // Use loggedInManager's details to update the UI
            managerIdLabel.setText("Manager ID: " + loggedInManager.getId());
            // Other UI updates or logic based on manager's details
        }
    }

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseDescriptionField;

    @FXML
    private TextField moduleCodeField;

    @FXML
    private TextField moduleNameField;

    @FXML
    private TextField moduleCreditField;

    @FXML
    private ComboBox<String> usersComboBox;

    @FXML
    private ComboBox<Module> modulesCombo;

    @FXML
    private Label moduleCodeLabel;

    @FXML
    private Label moduleNameLabel;

    @FXML
    private Label moduleCreditsLabel;

    @FXML
    private TextField moduleMaxAttemptsField;

    @FXML
    private Label businessRuleFeedback;

    @FXML
    private ComboBox<Course> coursesCombo;

    @FXML
    private Label courseCodeLabel;

    @FXML
    private Label courseSemestersLabel;

    @FXML
    private ListView<Module> courseModuleList;

    @FXML
    private TextField courseMaxCompField;

    public void populateUsersComboBox() {
        try {
            List<String> unapprovedUsers = loggedInManager.getUnapprovedUsers();
            usersComboBox.getItems().clear();
            usersComboBox.getItems().addAll(unapprovedUsers);
        } catch (SQLException e) {
            // Handle SQL Exception
            e.printStackTrace();
        }
    }

    @FXML
    private Label managerIdLabel;

    @FXML
    private void handleApproveButtonAction() {
        String selectedStudent = usersComboBox.getValue().trim(); // Trim any leading/trailing whitespace
        System.out.println("Selected student string: " + selectedStudent);

        String[] parts = selectedStudent.split(" - ");
        System.out.println("Parts after split: " + Arrays.toString(parts)); // Print the parts for debugging

        if (parts.length < 2) {
            System.out.println("Error: Unexpected format of user string.");
            return;
        }

// Parsing userId
        int userId;
        try {
            userId = Integer.parseInt(parts[0].trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: User ID is not a valid number.");
            return;
        }

// Extracting role from the second part
        String nameAndRole = parts[1].trim();
        int startRoleIndex = nameAndRole.lastIndexOf("(");
        int endRoleIndex = nameAndRole.lastIndexOf(")");

        if (startRoleIndex == -1 || endRoleIndex == -1 || startRoleIndex >= endRoleIndex) {
            System.out.println("Error: Role does not have the expected format.");
            return;
        }

        String role = nameAndRole.substring(startRoleIndex + 1, endRoleIndex).trim();

        String roleFormatted = "";
        if ("Student".equalsIgnoreCase(role)) {
            roleFormatted = "student";
        } else if ("Lecturer".equalsIgnoreCase(role)) {
            roleFormatted = "lecturer";
        }
        try {
            loggedInManager.approveUser(userId, role);
            updateFeedback("User approved successfully.");
            populateUsersComboBox();
        } catch (SQLException e) {
            updateFeedback("Error: Unable to approve user.");
            e.printStackTrace();
        }
    }

    @FXML
    private Label feedbackLabel;

    private void updateFeedback(String message) {
        feedbackLabel.setText(message);
    }

    @FXML
    private void handleAddCourseAction() {
        String courseCode = courseCodeField.getText();
        String courseName = courseNameField.getText();
        String courseDescription = courseDescriptionField.getText();

        try {
            loggedInManager.addCourse(courseCode, courseName, courseDescription);
            updateFeedback("Course added successfully.");
        } catch (SQLException e) {
            updateFeedback("Error adding course: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddModuleAction() {
        String moduleCode = moduleCodeField.getText();
        String moduleName = moduleNameField.getText();
        String moduleCredit = moduleCreditField.getText();

        try {
            loggedInManager.addModule(moduleCode, moduleName, moduleCredit);
            updateFeedback("Module added successfully.");
        } catch (SQLException e) {
            updateFeedback("Error adding module: " + e.getMessage());
        }
    }

    @FXML
    private void openUpdatePasswordView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/cs308fx/UpdatePasswordManager.fxml"));
            Parent root = loader.load();

            UpdatePasswordManagerController updatePasswordController = loader.getController();
            updatePasswordController.setLoggedInUser(loggedInManager); // Assuming you have a method like this in UpdatePasswordController

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IOException
            updateFeedback("Error: Unable to open the update password view.");
        }
    }

    public void populateCombos() {
        try {
            Connection conn = MySqlConnect.getConnection();
            populateModuleCombo(conn);
            populateCourseCombo(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Module> getModules(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM module;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Module> mods = new ArrayList<>();

        while (rs.next()) {
            Module mod = new Module(rs.getInt("moduleId"), rs.getString("moduleName"), rs.getString("moduleInfo"), rs.getInt("credit"), rs.getInt("maxAttempts"), rs.getString("courseId"));
            mods.add(mod);
        }

        return mods;
    }

    private void populateCourseCombo(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM course;");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ArrayList<Module> modsInCourse = new ArrayList<>();
            for (Module m: modulesCombo.getItems()) {
                if (m.getCourseId() != null && m.getCourseId().equals(rs.getString("courseId"))) {
                    modsInCourse.add(m);
                }
            }
            Course course = new Course(rs.getString("courseId"), rs.getString("courseName"), rs.getString("courseDescription"), rs.getString("semesters"), modsInCourse, rs.getInt("maxCompensation"));
            coursesCombo.getItems().add(course);
        }
    }

    private void populateModuleCombo(Connection conn) throws SQLException {
        for (Module m: getModules(conn)) {
            modulesCombo.getItems().add(m);
        }
    }

    @FXML
    public void loadModuleDetails(ActionEvent event) {
        Module mod = modulesCombo.getValue();

        moduleCodeLabel.setText(mod.getModuleId().toString());
        moduleNameLabel.setText(mod.getModuleName());
        moduleCreditsLabel.setText(mod.getCredits().toString());
        moduleMaxAttemptsField.setText(mod.getMaxAttempts().toString());
    }

    @FXML
    public void editMaxAttempts(ActionEvent event) {
        if (modulesCombo.getValue() == null) {
            businessRuleFeedback.setText("Select a module before setting max attempts");
            return;
        }

        try {
            Connection conn = MySqlConnect.getConnection();

            try {
                int maxAttempts = Integer.parseInt(moduleMaxAttemptsField.getText());
                Module mod = modulesCombo.getValue();
                mod.setMaxAttempts(maxAttempts);;

                PreparedStatement ps = conn.prepareStatement("UPDATE module SET maxAttempts = ? WHERE moduleId = ?");
                ps.setInt(1, maxAttempts);
                ps.setInt(2, mod.getModuleId());
                ps.executeUpdate();

                businessRuleFeedback.setText("Successfully set new max attempts");
            } catch (NumberFormatException ignored) {
                businessRuleFeedback.setText("Module max attempts not a valid number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void loadCourseDetails(ActionEvent event) {
        Course course = coursesCombo.getValue();

        courseCodeLabel.setText(course.getCourseId());
        courseSemestersLabel.setText(course.getSemesters());
        courseModuleList.getItems().setAll(course.getModules());
        courseMaxCompField.setText(course.getMaxComp().toString());
    }

    @FXML
    public void editMaxComp(ActionEvent event) {
        if (coursesCombo.getValue() == null) {
            businessRuleFeedback.setText("Select a course before setting max modules to compensate");
            return;
        }

        try {
            Connection conn = MySqlConnect.getConnection();

            try {
                int maxComp = Integer.parseInt(courseMaxCompField.getText());
                Course course = coursesCombo.getValue();
                course.setMaxComp(maxComp);

                PreparedStatement ps = conn.prepareStatement("UPDATE course SET maxCompensation = ? WHERE courseId = ?");
                ps.setInt(1, maxComp);
                ps.setString(2, course.getCourseId());
                ps.executeUpdate();

                businessRuleFeedback.setText("Successfully set new max module compensation");
            } catch (NumberFormatException ignored) {
                businessRuleFeedback.setText("Course max module compensation not a valid number");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
