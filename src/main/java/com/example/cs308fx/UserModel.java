package com.example.cs308fx;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserModel {
    static Connection connection = MySqlConnect.getConnection();

    public void addUser(String firstName, String surname, String password, String gender, String email, String role, LocalDate dateOfBirth) throws SQLException {
        String query;
        if ("student".equals(role)) {
            query = "INSERT INTO student (firstname, surname, password, gender, email, dateOfBirth, approved) VALUES (?, ?, ?, ?, ?, ?, ?);";
        } else if ("lecturer".equals(role)) {
            query = "INSERT INTO lecturer (firstname, surname, password, gender, email, dateOfBirth, approved) VALUES (?, ?, ?, ?, ?, ?, ?);";
        } else {
            // Handle error or throw an exception if the role is not recognized
            throw new IllegalArgumentException("Invalid role specified.");
        }

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, firstName);
        ps.setString(2, surname);
        ps.setString(3, password);
        ps.setString(4, gender);
        ps.setString(5, email);
        ps.setDate(6, java.sql.Date.valueOf(dateOfBirth));
        ps.setBoolean(7, false);

        ps.executeUpdate();
    }



    /**
     * Uses variable connection to find correct user and matches role to return the right subclass of UserModel.
     * After logging in it uses setter methods to set private variables of the class.
     * @param username, password
     * @return userType of Student, Lecturer or Manager
     */
    public UserModel login(String username, String password, String role) throws SQLException {
        UserModel user = null;
        if (Objects.equals("student", role)) {
            String query = "SELECT student.*, course.courseName " +
                    "FROM student " +
                    "LEFT JOIN course ON student.courseId = course.courseId " +
                    "WHERE student.username = ? AND student.password = ?;";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getBoolean("approved")) {
                user = new Student(
                        rs.getInt("studentId"),
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("email"),
                        rs.getInt("courseId"),
                        rs.getString("courseName"),
                        rs.getString("decision")
                );
            }

        } else if (Objects.equals("lecturer", role)) {
            String query = "SELECT * FROM lecturer WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getBoolean("approved")) {
                user = new Lecturer(
                        rs.getInt("lecturerId"),
                        rs.getString("username"),
                        rs.getString("firstname"),
                        rs.getString("surname"),
                        rs.getString("gender"),
                        rs.getString("dateOfBirth"),
                        rs.getString("email"),
                        rs.getString("qualification")
                );
            }
        } else if (Objects.equals("manager", role)) {
            String query = "SELECT * FROM manager WHERE username = ? AND password = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new Manager(
                        rs.getInt(("managerId")),
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

    public List<Module> getModulesForStudent(String studentId) {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT m.moduleId, m.moduleName, m.credit, m.moduleInfo, m.maxAttempts, m.courseId " +
                "FROM module m " +
                "JOIN studentModule sm ON m.moduleId = sm.moduleId " +
                "WHERE sm.studentId = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer moduleId = rs.getInt("moduleId");
                    String moduleName = rs.getString("moduleName");
                    String moduleInfo = rs.getString("moduleInfo");
                    int credits = rs.getInt("credit");
                    int maxAttempts = rs.getInt("maxAttempts");
                    String courseId = rs.getString("courseId");
                    modules.add(new Module(moduleId, moduleName, moduleInfo, credits, maxAttempts, courseId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return modules;
    }

    public List<Student> getStudentsForModule(int moduleId) {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * " +
                "FROM student s " +
                "JOIN studentModule sm ON s.studentId = sm.studentId " +
                "WHERE sm.moduleId = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, moduleId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int studentId             = rs.getInt("studentId");
                    String studentUsername    = rs.getString("username");
                    String studentFirstname   = rs.getString("firstname");
                    String studentSurname     = rs.getString("surname");
                    String studentGender      = rs.getString("gender");
                    String studentEmail       = rs.getString("email");
                    String studentDateOfBirth = rs.getString("dateOfBirth");
                    int studentCourseId       = rs.getInt("courseId");
                    String studentDecision    = rs.getString("decision");
                    String studentApproved    = rs.getString("approved");
                    students.add(new Student(studentId, studentUsername, studentFirstname, studentSurname, studentGender, studentEmail, studentDateOfBirth, studentCourseId, studentDecision, studentApproved));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return students;
    }

    public List<Module> getModulesForLecturer(String lecturerId) {
        List<Module> modules = new ArrayList<>();
        String query = "SELECT m.moduleId, m.moduleName, m.credit, m.moduleInfo, m.maxAttempts, m.courseId " +
                "FROM module m " +
                "JOIN lecturerModule lm ON m.moduleId = lm.moduleId " +
                "WHERE lm.lecturerId = ?;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, lecturerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Integer moduleId = rs.getInt("moduleId");
                    String moduleName = rs.getString("moduleName");
                    String moduleInfo = rs.getString("moduleInfo");
                    int credits = rs.getInt("credit");
                    int maxAttempts = rs.getInt("maxAttempts");
                    String courseId = rs.getString("courseId");
                    modules.add(new Module(moduleId, moduleName, moduleInfo, credits, maxAttempts, courseId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }

        return modules;
    }

    public void updatePassword(Person user, String newPassword) throws SQLException {
        String username = user.getUsername();
        String table;
        if (username.startsWith("stu")) {
            table = "student";
        } else if (username.startsWith("lct")) {
            table = "lecturer";
        } else {
            throw new IllegalArgumentException("Invalid user ID prefix.");
        }

        String query = "UPDATE " + table + " SET password = ? WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, newPassword);
            ps.setString(2, username);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating password failed, no rows affected.");
            }
        }
    }
    }
