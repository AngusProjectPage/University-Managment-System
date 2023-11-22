package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

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
 * @author Connor
 */
public class Manager extends UserController {

    public Manager(String email) {
        super(email, new StringAsker(System.in, System.out));
    }
    public Manager(String email, StringAsker asker) {
        super(email, asker);
    }

    public boolean addCourse(int courseID, String courseDescription) {

        return false;
    }

    public boolean updateApproval(String userID) {
        return false;
    }

    public boolean assignLecturer(String lecturerID, int moduleID) {
        return false;
    }

    public boolean enrollStudent(String studentID, int courseID) {
        return false;
    }

    public boolean issueStudentDecision(String studentID, String decision) {
        return false;
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
