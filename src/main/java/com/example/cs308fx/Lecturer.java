package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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


    public Lecturer(int id, String username, String firstName, String surname, String gender, String dateOfBirth, String email, String qualification) {
        super(id, username, firstName, surname, gender, dateOfBirth, email);
        this.qualification = qualification;
    }

    public void updateModuleInfo(Integer moduleId, String moduleInfo) {
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

    public void uploadNote(File file, String week, String noteType, Integer moduleId) {
        // Determine the column to update based on the note type
        String columnToUpdate = noteType.equals("lab") ? "labNote" : "lectureNote";

        String insertQuery = "INSERT INTO week (weekId, moduleId, " + columnToUpdate + ") VALUES (?, ?, ?)";
        String updateQuery = "UPDATE week SET " + columnToUpdate + " = ? WHERE weekId = ? AND moduleId = ?";

        // Convert file to byte array
        byte[] fileContent = readFileToByteArray(file);

        if (fileContent != null) {
            try {
                // Check if an entry exists for the week and module
                boolean exists = entryExists(week, moduleId);

                try (PreparedStatement ps = connection.prepareStatement(exists ? updateQuery : insertQuery)) {
                    if (exists) {
                        ps.setBytes(1, fileContent);
                        ps.setInt(2, Integer.parseInt(week));
                        ps.setInt(3, moduleId);
                    } else {
                        ps.setInt(1, Integer.parseInt(week));
                        ps.setInt(2, moduleId);
                        ps.setBytes(3, fileContent);
                    }
                    ps.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private byte[] readFileToByteArray(File file) {
        byte[] fileContent = null;
        try (FileInputStream fis = new FileInputStream(file)) {
            fileContent = new byte[(int) file.length()];
            fis.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    private boolean entryExists(String week, int moduleId) throws SQLException {
        String checkQuery = "SELECT COUNT(*) FROM week WHERE weekId = ? AND moduleId = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, Integer.parseInt(week));
            checkStmt.setInt(2, moduleId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
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
