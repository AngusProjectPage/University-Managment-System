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
 * @author Connor, Callum
 */
public class Manager extends Person {


    public Manager(int id, String username, String firstName, String surname, String gender, String dateOfBirth, String email) {
        super(id, username, firstName, surname, gender, dateOfBirth, email);
    }
    public List<String> getUnapprovedUsers() throws SQLException {
        List<String> users = new ArrayList<>();
        String query = "SELECT studentId AS userId, firstname, surname, 'Student' AS userType FROM student WHERE approved = false " +
                "UNION " +
                "SELECT lecturerId AS userId, firstname, surname, 'Lecturer' AS userType FROM lecturer WHERE approved = false";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String userInfo = rs.getInt("userId") + " - " + rs.getString("firstname") + " " + rs.getString("surname") + " (" + rs.getString("userType") + ")";
            users.add(userInfo);
        }

        return users;
    }

    public List<Person> getApprovedUsers() throws SQLException {
        List<Person> users = new ArrayList<>();

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM student, course WHERE student.courseId = course.courseId AND approved=true");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int studentId = rs.getInt("studentId");
            String username = rs.getString("username");
            String firstName = rs.getString("firstname");
            String surname = rs.getString("surname");
            String gender = rs.getString("gender");
            String dob = rs.getString("dateOfBirth");
            String email = rs.getString("email");
            int courseId = rs.getInt("courseId");
            String courseName = rs.getString("courseName");
            String decision = rs.getString("decision");

            users.add(new Student(studentId, username, firstName, surname, gender, dob, email, courseId, courseName, decision));
        }

        ps = connection.prepareStatement("SELECT * FROM lecturer WHERE approved=true");
        rs = ps.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("lecturerId");
            String username = rs.getString("username");
            String firstName = rs.getString("firstname");
            String surname = rs.getString("surname");
            String gender = rs.getString("gender");
            String dob = rs.getString("dateOfBirth");
            String email = rs.getString("email");
            String qualification = rs.getString("qualification");

            users.add(new Lecturer(id, username, firstName, surname, gender, dob, email, qualification));
        }

        return users;
    }

    public void approveUser(int userId, String userType) throws SQLException {
        System.out.println("User Type: " + userType); // For debugging purposes

        String tableName = userType.trim().equalsIgnoreCase("student") ? "student" : "lecturer";
        String prefix = tableName.equals("student") ? "stu" : "lct";
        String newUsername = prefix + userId;

        System.out.println("Table Name: " + tableName); // For debugging purposes
        String query = "UPDATE " + tableName + " SET approved = true, username = ? WHERE " + tableName + "Id = ?";
        System.out.println(query);
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, newUsername);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    public void addOrUpdateCourse(String id, String name, String description, String semesters, String compensation) throws SQLException {
        String query = "INSERT INTO course (courseId, courseName, courseDescription, semesters, maxCompensation) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE courseName = ?, courseDescription = ?, semesters = ?, maxCompensation = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, description);
        ps.setString(4, semesters);
        ps.setString(5, compensation);
        ps.setString(6, name);
        ps.setString(7, description);
        ps.setString(8, semesters);
        ps.setString(9, compensation);
        ps.executeUpdate();
    }

    public void addOrUpdateModule(Integer id, String name, Integer credit, String moduleInfo, Integer maxAttempts) throws SQLException {
        String query = "INSERT INTO module (moduleId, moduleName, credit, moduleInfo, maxAttempts) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE moduleName = ?, credit = ?, moduleInfo = ?, maxAttempts = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, credit);
        ps.setString(4, moduleInfo);
        ps.setInt(5, maxAttempts);
        ps.setString(6, name);
        ps.setInt(7, credit);
        ps.setString(8, moduleInfo);
        ps.setInt(9, maxAttempts);
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

    public void deactivateUser(Person user) throws SQLException {
        String table = (user instanceof Student) ? "student" : "lecturer";
        String idField = (user instanceof Student) ? "studentId" : "lecturerId";
        String id = user.getUsername().substring(3);

        PreparedStatement ps = connection.prepareStatement("UPDATE " + table + " SET approved=false WHERE " + idField + "=?;");
        ps.setInt(1, Integer.parseInt(id));

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Deactivating user failed, no rows affected.");
        }
    }

    public void updateCourseInfo(Integer moduleId, String courseInfo) {
        String query = "UPDATE course SET courseDescription = ? WHERE courseId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, courseInfo);
            ps.setInt(2, moduleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handling any SQL Exceptions
            e.printStackTrace();
        }
    }

    public void updateModuleLecturer(int lecturerId, int moduleId) throws SQLException {
        String query = "INSERT INTO lecturerModule (moduleId, lecturerId) VALUES (?, ?) ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, moduleId);
        ps.setInt(2, lecturerId);
        ps.executeUpdate();
    }

    public void updateModuleCourse(int courseId, int moduleId) throws SQLException {
        String query = "UPDATE module SET courseId = ? WHERE moduleId = ? ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, courseId);
        ps.setInt(2, moduleId);
        ps.executeUpdate();
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
