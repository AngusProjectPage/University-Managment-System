package com.example.cs308fx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class UserModel {
    static Connection connection = MySqlConnect.getConnection();

    public boolean addUser(String firstName, String surname, String email, String gender, String password, String userRole, String dateOfBirth, boolean approved) throws SQLException {
        if(Objects.equals(userRole, "student")) {
            String query = "INSERT INTO student(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES (firstName=?, surname=?, password=?, gender=?, email=?, dateOfBirth=?, approved=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, surname);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setBoolean(6, approved);
        }
        else if(Objects.equals(userRole, "lecturer")) {
            String query = "INSERT INTO student(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES (firstName=?, surname=?, password=?, gender=?, email=?, dateOfBirth=?, approved=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, surname);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setBoolean(6, approved);
        }
        else {
            String query = "INSERT INTO student(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES (firstName=?, surname=?, password=?, gender=?, email=?, dateOfBirth=?, approved=?;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

        }
        return true;
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
            String query = "SELECT * FROM student, course WHERE student.username = ? AND student.password = ? AND student.courseId = course.courseId;";
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
