package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.util.Date;
import java.util.List;

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
    int labMark;
    int examMark;
    public Student(String email, String firstName, String secondName, String gender, Date DOB, List<Module> modules, Course course) {
        super(email,firstName, secondName, gender, DOB);
    }
}

//TODO: view their enrolled course and their modules with their marks
//TODO: download a lecture note
//TODO: view their decision if it is available
