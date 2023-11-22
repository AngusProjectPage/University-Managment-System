package com.example.cs308fx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private int courseId;
    private String decision;
    private boolean approved;


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
            ps.setString(1, firstName);
            ps.setString(2, surname);
            ps.setString(3, password);
            ps.setString(4, gender);
            ps.setString(5, email);
            ps.setBoolean(6, approved);
        }
        return true;
    }


    /**
     * Uses variable connection to find correct user and matches role to return the right subclass of UserModel.
     * After logging in it uses setter methods to set private variables of the class.
     * @param username, password
     * @return userType of Student, Lecturer or Manager
     */
    public void login(String username, String password, String role) throws SQLException {
        if(Objects.equals("student", role)) {
            String query = "SELECT * FROM student WHERE username = ?, password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Get fields from database
                this.id          = rs.getInt("id");
                this.username    = rs.getString("username");
                this.firstName   = rs.getString("firstname");
                this.surname     = rs.getString("surname");
                this.gender      = rs.getString("gender");
                this.email       = rs.getString("email");
                this.dateOfBirth = rs.getString("dateOfBirth");
                this.courseId    = rs.getInt("courseId");
                this.decision    = rs.getString("decision");
                this.approved    = rs.getBoolean("approved");
            }
            else {
                System.out.println("No matching record found");
            }

        }
        else if(Objects.equals("lecturer", role)) {
            String query = "SELECT * FROM student WHERE username = ?, password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Get fields from database
                this.id          = rs.getInt("id");
                this.username    = rs.getString("username");
                this.firstName   = rs.getString("firstname");
                this.surname     = rs.getString("surname");
                this.gender      = rs.getString("gender");
                this.email       = rs.getString("email");
                this.dateOfBirth = rs.getString("dateOfBirth");
                this.courseId    = rs.getInt("courseId");
                this.decision    = rs.getString("decision");
                this.approved    = rs.getBoolean("approved");
            }
            else {
                System.out.println("No matching record found");
            }
        }
        else {
            String query = "SELECT * FROM student WHERE username = ?, password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                // Get fields from database
                this.id          = rs.getInt("id");
                this.username    = rs.getString("username");
                this.firstName   = rs.getString("firstname");
                this.surname     = rs.getString("surname");
                this.gender      = rs.getString("gender");
                this.email       = rs.getString("email");
                this.dateOfBirth = rs.getString("dateOfBirth");
                this.courseId    = rs.getInt("courseId");
                this.decision    = rs.getString("decision");
                this.approved    = rs.getBoolean("approved");
            }
            else {
                System.out.println("No matching record found");
            }
        }
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

    public void addUserToDB() {

    }

}