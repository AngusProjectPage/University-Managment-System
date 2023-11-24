package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.util.Date;
/**
 * The Person is the subclass of {@link UserController} that is used to describe active users <br>
 * A Person can branch to a {@link Lecturer}, or a {@link Student} <br>
 * A Person has a:
 * <ul><ul>
 *     <li>email - {@link String}</li>
 *     <li>password - String</li>
 *     <li>first name - String</li>
 *     <li>second name - String</li>
 *     <li>gender - String</li>
 *     <li>Date of Birth - {@link Date}</li>
 * </ul></ul>
 *  
 * @see UserModel
 * @see Lecturer
 * @see Student
 */


public class Person extends UserModel {
    protected String username;
    protected String firstName;
    protected String surname;
    protected String gender;
    protected String dateOfBirth;
    protected String email;

    public Person(String username, String firstName, String surname, String gender, String dateOfBirth, String email) {
        this.username = username;
        this.firstName = firstName;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }
    public String getId() {
        return username;
    }
    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getDateOfBirth() { return dateOfBirth; }

}

