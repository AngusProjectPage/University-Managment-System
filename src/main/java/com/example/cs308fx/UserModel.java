package com.example.cs308fx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserModel {
    static Connection connection = MySqlConnect.getConnection();
    private String username;
    private String firstName;
    private String surname;
    private int id;
    private String gender;
    private String email;
    private String dateOfBirth;
    private Course course;
    private List<Module> modules;
    private String decision;
    private boolean approved;

    private boolean loginSuccessful = false;

    public boolean isLoginSuccessful() {
        return loginSuccessful;
    }


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
                        rs.getString("decision"),
                        rs.getBoolean("approved")
                );
            }
        } else if (Objects.equals("lecturer", role)) {
            // Similar approach for lecturer
            // Fetch data and create a Lecturer instance
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










    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getGender() {
        return this.gender;
    }

    public String getEmail() {
        return this.email;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public Course getCourse() { return this.course; }

    public void addUserToDB() {

    }

}