package com.example.cs308fx;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Course is a class that has a code, name description and a number of assigned {@link Module}'s.<br>
 * A course can either run semester 1, semester 2 or both semesters.<br>
 *
 * @see Module
 */

 
public class Course {
    ArrayList<Module> modules;
    String code;
    String name;
    String description;
    boolean[] semesters;
    Integer maxModuleCompensation;

    public Course(String code, String name, String description, String sems, ArrayList<Module> modules, int maxComp) {
        this.code = code;
        this.name = name;
        this.description = description;
        this.modules = modules;
        this.maxModuleCompensation = maxComp;

        switch (sems) {
            case "first" -> this.semesters = new boolean[]{true, false};
            case "second" -> this.semesters = new boolean[]{false, true};
            case "both" -> this.semesters = new boolean[]{true, true};
        }
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

    public String getCourseId() {
        return code;
    }

    public String getSemesters() {
        String sem = "";
        if (Arrays.equals(semesters, new boolean[]{true, false})) {
            sem = "First Only";
        } else if (Arrays.equals(semesters, new boolean[]{false, true})) {
            sem = "Second Only";
        } else if (Arrays.equals(semesters, new boolean[]{true, true})) {
            sem = "Both";
        }

        return sem;
    }

    public Integer getMaxComp() {
        return maxModuleCompensation;
    }

    public void setMaxComp(Integer maxComp) {
        this.maxModuleCompensation = maxComp;
    }

    @Override
    public String toString() {
        return name;
    }

}
