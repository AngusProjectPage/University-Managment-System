package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A Lecturer is a subclass of {@link Person} and a sub-subclass of a {@link UserController} <br>
 * A Lecturer has different permissions to a {@link Student}
 *
 * <ul>
 *     <li>update module information</li>
 *     <li>upload or update module materials in each week - <em>lecture notes and lab notes</em></li>
 *     <li>display enrolled students in their modules</li>
 *     <li>update the exam record of every enrolled student</li>
 * </ul>
 *
 * @see Person
 * @see UserController
 * @see Student
 */
public class Lecturer extends Person {
    private String qualification;


    public Lecturer(String username, String firstName, String surname, String gender, String dateOfBirth, String email, String qualification) {
        super(username, firstName, surname, gender, dateOfBirth, email);
        this.qualification = qualification;
    }

    public void updateModuleInfo(Integer moduleId, String moduleInfo){
        String query = "UPDATE module SET moduleInfo = ? WHERE moduleId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, moduleInfo);
            ps.setInt(2, moduleId);
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handling any SQL Exceptions
            e.printStackTrace();
        }
    }
    @Override
    public String toString() {
        return username;
    }

}



    // Getters and setters for lecturer-specific fields


//TODO: update module information
//TODO: upload or update module materials in each week - <em>lecture notes and lab notes</em>
//TODO: display enrolled students in their modules
//TODO: update the exam record of every enrolled student
