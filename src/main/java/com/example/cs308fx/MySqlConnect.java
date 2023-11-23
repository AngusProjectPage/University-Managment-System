package com.example.cs308fx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.util.Scanner;

/**
 * Singleton Class.
 * Class must be static and create only one object that is referenced through its public method.
 */
public class MySqlConnect {

    /**
     * Static means that the block will only run one time.
     * It is run when the program is loaded into memory and holds the same data until program completion.
     */
    private static Connection connection = null;

    static {
        // Use try-with-resources to ensure the scanner is closed after use
        try (InputStream dbConfigStream = MySqlConnect.class.getClassLoader().getResourceAsStream("database_connection/dbConnect.txt");
             Scanner scanner = new Scanner(dbConfigStream)) {

            String url = "jdbc:mysql://localhost:3306/";
            String dbName = scanner.nextLine();
            String user = scanner.nextLine();
            String password = scanner.nextLine();

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url + dbName, user, password);
            System.out.println("Successfully connected to MySQL db");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read database connection details", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}

