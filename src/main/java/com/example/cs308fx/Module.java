package com.example.cs308fx;

/**
 * A Module is a class that is taught by a lecturer and attended by students.<br>
 * Each module is worth a certain number of credits and has a name and code.<br>
 * Each module will have a certain number of attempts allowed.<br>
 * Each module will store their lecture and lab notes.<br>
 * A module is assigned to a {@link Course}<br>
 * @see Course
 */
public class Module {
    private String moduleId;
    private String moduleName;
    private int credits;

    // Constructor
    public Module(String moduleId, String moduleName, int credits) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.credits = credits;
    }
}
