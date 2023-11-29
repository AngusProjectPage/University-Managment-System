package com.example.cs308fx;

import com.example.cs308fx.controllers.UserController;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    private int courseId;
    private String courseName;
    private String decision;

    public Student(int id, String username, String firstName, String surname, String gender,
                   String dateOfBirth, String email, int courseId,
                   String courseName, String decision) {
        super(id, username, firstName, surname, gender, dateOfBirth, email);
        this.courseId = courseId;
        this.courseName = courseName;
        this.decision = decision;
    }


    public String getCourseName() {
        return courseName;
    }

    public int getCourseId() {
        return courseId;
    }
    public String getDecision() { return decision; }

    @Override
    public String toString() {
        return username;
    }


    public void addLabMark(int labMark, Student student, Module module) throws SQLException {
        String query = "UPDATE studentModule SET labMark = ? WHERE studentId = ? AND moduleId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, labMark);
        ps.setInt(2, student.getId());
        ps.setInt(3, module.getModuleId());
        ps.executeUpdate();
    }

    public void addExamMark(int examMark, Student student, Module module) throws SQLException {
        String query = "UPDATE studentModule SET examMark = ? WHERE studentId = ? AND moduleId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, examMark);
        ps.setInt(2, student.getId());
        ps.setInt(3, module.getModuleId());
        ps.executeUpdate();
    }
}


//TODO: view their enrolled course and their modules with their marks
//TODO: download a lecture note
//TODO: view their decision if it is available
