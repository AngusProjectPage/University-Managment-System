package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A Manager is a subclass of {@link UserController} and has the most permissions over the app</li>
 * <ul><ul>
 *     <li>A manager can view sign-up workflow</li>
 *     <li>Approve Users who signed up to create their accounts. </li>
 *     <li>Approve {@link Lecturer}s who signed up to create their account.</li>
 *     <li>Manage other Users accounts </li>
 *     <li>Assign a module to a given Lecturer.</li>
 *     <li>Enrol {@link Student} in a given course. </li>
 *     <li>Issue Student decision</li>
 *     <li>Add a business rule</li>
 *     <li>Add new course</li>
 *     <li>Add module</li>
 *     <li>Assign module to course</li>
 *     <li>Display course details</li>
 *     <li>Display module details</li>
 *     <li>Update course information</li>
 * </ul></ul>
 *
 * @see UserModel
 * @see Lecturer
 * @see Student
 * @author Connor
 */
public class Manager extends Person {

    static Connection connection = MySqlConnect.getConnection();

    public Manager(String username, String firstName, String surname, String gender, String dateOfBirth, String email) {
        super(username, firstName, surname, gender, dateOfBirth, email);
    }
    public List<String> getUnapprovedStudents() throws SQLException {
        List<String> students = new ArrayList<>();
        String query = "SELECT studentId, firstname, surname FROM student WHERE approved = false";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String studentInfo = rs.getInt("studentId") + " - " + rs.getString("firstname") + " " + rs.getString("surname");
            students.add(studentInfo);
        }

        return students;
    }

    public void approveStudent(int studentId) throws SQLException {
        String query = "UPDATE student SET approved = true WHERE studentId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, studentId);
        ps.executeUpdate();
    }

    public void addCourse(String id, String name, String description) throws SQLException {
        String query = "INSERT INTO course (courseId, courseName, courseDescription) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, description);
        ps.executeUpdate();
    }

    public void addModule(String id, String name, String credit) throws SQLException {
        String query = "INSERT INTO module (moduleId, moduleName, credit) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, credit);
        ps.executeUpdate();
    }

    public void updatePassword(String userId, String newPassword) throws SQLException {
        String table;
        if (userId.startsWith("stu")) {
            table = "student";
        } else if (userId.startsWith("mgr")) {
            table = "manager";
        } else if (userId.startsWith("lct")) {
            table = "lecturer";
        } else {
            throw new IllegalArgumentException("Invalid user ID prefix.");
        }

        String query = "UPDATE " + table + " SET password = ? WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, userId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating password failed, no rows affected.");
            }
        }
    }

}


//TODO: A manager can view sign-up workflow
//TODO: Approve Users who signed up to create their accounts.
//TODO: Approve Lecturers who signed up to create their account.
//TODO: Manage other Users accounts
//TODO: Assign a module to a given Lecturer.
//TODO: Enrol Student in a given course.
//TODO: Issue Student decision
//TODO: Add a business rule
//TODO: Add new course
//TODO: Add module
//TODO: Assign module to course
//TODO: Display course details
//TODO: Display module details
//TODO: Update course information
