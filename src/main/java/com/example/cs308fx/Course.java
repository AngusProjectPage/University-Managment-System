package com.example.cs308fx;

import java.util.ArrayList;

/**
 * Course is a class that has a code, name description and a number of assigned {@link Module}'s.<br>
 * A course can either run semester 1, semester 2 or both semesters.<br>
 *
 * @see Module
 */

 
public class Course {
    ArrayList<Module> modules = new ArrayList<>();
    String code;
    String name;
    String description;
    boolean[] semesters = new boolean[]{true, true};

    public Course(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    /**
     * Gets the list of {@link Module}s that comprise this course
     *
     * @return {@link ArrayList} of {@link Module}s
     */
    public ArrayList<Module> getModules() {
        return modules;
    }

    public String getName() {
        return name;
    }
}
