package com.example.cs308fx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * A Module is a class that is taught by a lecturer and attended by students.<br>
 * Each module is worth a certain number of credits and has a name and code.<br>
 * Each module will have a certain number of attempts allowed.<br>
 * Each module will store their lecture and lab notes.<br>
 * A module is assigned to a {@link Course}<br>
 * @see Course
 */
public class Module {
    private Integer moduleId;
    private String moduleName;
    private Integer credits;
    private String moduleInfo;
    private Integer maxAttempts;
    private String courseId;

    // Constructor
    public Module(Integer moduleId, String moduleName, String moduleInfo, int credits, int maxAttempts, String courseId) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleInfo = moduleInfo;
        this.credits = credits;
        this.maxAttempts = maxAttempts;
        this.courseId = courseId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleInfo() { return moduleInfo; }

    public Integer getCredits() {
        return credits;
    }
    public Integer getMaxAttempts() {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    // WARNING: This one bites (returns NULL sometimes...)
    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return this.moduleName;
    }






}
