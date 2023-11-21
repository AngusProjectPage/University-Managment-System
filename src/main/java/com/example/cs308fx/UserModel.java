package com.example.cs308fx;

public class UserModel {
    static MySqlConnect connection;

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