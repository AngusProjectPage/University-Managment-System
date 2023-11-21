package com.example.cs308fx;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class UserModel {
    static Connection connection = MySqlConnect.getConnection();
    private String firstName;
    private String surname;
    private int Id;
    private char gender;
    private String email;
    private String dateOfBirth;

    /**
     * Uses variable connection to find correct user and matches role to return the right subclass of UserModel.
     * @param userId
     * @return userType of Student, Lecturer or Manager
     */
    public UserModel getUser(int userId) {

        return null;
    }

    public boolean addUser(String firstName, String surname, String email, String gender, String password, String userRole, String dateOfBirth, boolean approved) throws SQLException {
        if(Objects.equals(userRole, "student")) {
            String query = "INSERT INTO student(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES ("+ firstName + ", "+ surname + ", "+ password + ", "+ gender + ", "+ email + ",  "+ dateOfBirth + ", "+ approved + ";";
            PreparedStatement ps = connection.prepareStatement(query);
        }
        else if(Objects.equals(userRole, "lecturer")) {
            String query = "INSERT INTO lecturer(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES ("+ firstName + ", "+ surname + ", "+ password + ", "+ gender + ", "+ email + ",  "+ dateOfBirth + ", "+ approved + ";";
            PreparedStatement ps = connection.prepareStatement(query);
        }
        else {
            String query = "INSERT INTO manager(firstname, surname, password, gender, email, dateOfBirth, approved) " +
                    "VALUES ("+ firstName + ", "+ surname + ", "+ password + ", "+ gender + ", "+ email + ",  "+ dateOfBirth + ", "+ approved + ";";
            PreparedStatement ps = connection.prepareStatement(query);
        }
        return true;
    }

    public int getId() {
        return this.Id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getSurname() {
        return this.surname;
    }

    public char getGender() {
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