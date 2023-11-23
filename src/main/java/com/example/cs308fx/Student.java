package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

/**
 * A Student is a subclass of {@link Person} and a sub-subclass of a {@link UserController} <br>
 * A Student has different permissions to a {@link Lecturer}
 *
 * <ul><ul>
 *     <li>view their enrolled course and their modules with their marks</li>
 *     <li>download a lecture note</li>
 *     <li>view their decision if it is available <ul>
 *         <li><strong>award </strong>- decision when all module are passed</li>
 *         <li><strong>resit </strong>- decision is given otherwise</li>
 *         <li><strong>withdraw </strong>- decision is given when all attempts allowed have been attempted</li>
 *     </ul></li>
 * </ul></ul>
 *
 * @see Person
 * @see UserModel
 * @see Lecturer
 */
public class Student extends Person {
    private String courseId;
    private String courseName;

    private String decision;

    public Student(String username, String firstName, String surname, String gender,
                   String dateOfBirth, String email, String courseId,
                   String courseName, String decision) {
        super(username, firstName, surname, gender, dateOfBirth, email);
        this.courseId = courseId;
        this.courseName = courseName;
        this.decision = decision;
    }


    public String getCourseName() {
        return courseName;
    }

    public String getCourseId() {
        return courseId;
    }
    public String getDecision() { return decision; }
}


//TODO: view their enrolled course and their modules with their marks
//TODO: download a lecture note
//TODO: view their decision if it is available
