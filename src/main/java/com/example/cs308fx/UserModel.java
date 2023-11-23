package com.example.cs308fx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class UserModel {
    static Connection connection = MySqlConnect.getConnection();

    public void addUser(String firstName, String surname, String password, String gender, String email, String role, LocalDate dateOfBirth) throws SQLException {
        String query;
        if ("student".equals(role)) {
            query = "INSERT INTO student (firstname, surname, password, gender, email, dateOfBirth, approved) VALUES (?, ?, ?, ?, ?, ?, ?);";
        } else if ("lecturer".equals(role)) {
            query = "INSERT INTO lecturer (firstname, surname, password, gender, email, dateOfBirth, approved) VALUES (?, ?, ?, ?, ?, ?, ?);";
        } else {
            // Handle error or throw an exception if the role is not recognized
            throw new IllegalArgumentException("Invalid role specified.");
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, firstName);
        ps.setString(2, surname);
        ps.setString(3, password);
        ps.setString(4, gender);
        ps.setString(5, email);
        ps.setDate(6, java.sql.Date.valueOf(dateOfBirth));
        ps.setBoolean(7, false);

        ps.executeUpdate();
    }



    /**
     * Uses variable connection to find correct user and matches role to return the right subclass of UserModel.
     * After logging in it uses setter methods to set private variables of the class.
     * @param username, password
     * @return userType of Student, Lecturer or Manager
     */
    public Person login(String username, String password, String role) throws SQLException {
        Person user = null;
        if (Objects.equals("student", role)) {
            String query = "SELECT student.*, course.courseName " +
                    "FROM student " +
                    "LEFT JOIN course ON student.courseId = course.courseId " +
                    "WHERE student.username = ? AND student.password = ?;";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getBoolean("approved")) {
                user = new Student(
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("email"),
                        rs.getString("courseId"),
                        rs.getString("courseName"),
                        rs.getString("decision")
                );
            }

        } else if (Objects.equals("lecturer", role)) {
            String query = "SELECT * FROM lecturer WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getBoolean("approved")) {
                user = new Lecturer(
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("email"),
                        rs.getString("qualification"),
                        rs.getBoolean("approved")

                );
            }
        } else if (Objects.equals("manager", role)) {
            String query = "SELECT * FROM manager WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Manager(
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("email")
                );
            }
        }

        return user;
    }
}
