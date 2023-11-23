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
    public void login(String username, String password, String role) throws SQLException {
        loginSuccessful = false;
        if(Objects.equals("student", role)) {
            String query = "SELECT * FROM student, course WHERE " +
                    "student.username = ? AND student.password = ?" +
                    " AND student.courseId = course.courseId;";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                // Get fields from database
                this.id          = rs.getInt("studentId");
                this.username    = rs.getString("username");
                this.firstName   = rs.getString("firstname");
                this.surname     = rs.getString("surname");
                this.gender       = rs.getString("gender");
                this.email = rs.getString("email");
                this.dateOfBirth    = rs.getString("dateOfBirth");
                this.course = new Course(rs.getString("courseId"), rs.getString("courseName"), rs.getString("courseDescription"));
                this.decision    = rs.getString("decision");
                this.approved    = rs.getBoolean("approved");

                //only allow login if approved!
                if (!this.approved){
                    System.out.println("Not approved!");
                } else {
                    loginSuccessful = true;
                }
            }
            else {
                System.out.println("No matching record found");
            }

        }
        else if(Objects.equals("lecturer", role)) {
            String query = "SELECT * FROM lecturer WHERE username = ? AND password = ?";
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
                this.course = new Course(rs.getString("courseId"), rs.getString("courseName"), rs.getString("courseDescription"));
                this.decision    = rs.getString("decision");
                this.approved    = rs.getBoolean("approved");
                loginSuccessful = true;
            }
            else {
                System.out.println("No matching record found");
            }

        }
        else {
            String query = "SELECT * FROM manager WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                // Get fields from database
                this.id = rs.getInt("managerId"); // assuming 'id' corresponds to 'managerId'
                this.username = rs.getString("username");
                this.firstName = rs.getString("firstname"); // make sure the case matches the database
                this.surname = rs.getString("surname"); // make sure the case matches the database
                this.gender = rs.getString("gender"); // the case of 'gender' must match the database
                this.email = rs.getString("email");
                this.dateOfBirth = rs.getString("dateOfBirth");
                loginSuccessful = true;
            }
            else {
                System.out.println("No matching record found");
            }
        }
    }

    public List<String> getUnapprovedStudents() throws SQLException {
        List<String> students = new ArrayList<>();
        String query = "SELECT studentId, firstname, surname FROM student WHERE approved = false";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String studentInfo = rs.getInt("studentId") + " - " + rs.getString("firstname") + " " + rs.getString("surname");
            students.add(studentInfo);
        }

        return students;
    }

    public void approveStudent(int studentId) throws SQLException {
        String query = "UPDATE student SET approved = true WHERE studentId = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, studentId);
        ps.executeUpdate();
    }

    public void addCourse(String id, String name, String description) throws SQLException {
        String query = "INSERT INTO course (courseId, courseName, courseDescription) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, description);
        ps.executeUpdate();
    }

    public void addModule(String id, String name, String credit) throws SQLException {
        String query = "INSERT INTO module (moduleId, moduleName, credit) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, name);
        ps.setString(3, credit);
        ps.executeUpdate();
    }

    public void updatePassword(String userId, String newPassword) throws SQLException {
        String table;
        if (userId.startsWith("stu")) {
            table = "student";
        } else if (userId.startsWith("mgr")) {
            table = "manager";
        } else if (userId.startsWith("lct")) {
            table = "lecturer";
        } else {
            throw new IllegalArgumentException("Invalid user ID prefix.");
        }

        String query = "UPDATE " + table + " SET password = ? WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, userId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating password failed, no rows affected.");
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

    public Course getCourse() { return this.course; }

    public void addUserToDB() {

    }

}