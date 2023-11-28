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
    private Integer moduleId;
    private String moduleName;
    private int credits;
    private String moduleInfo;

    // Constructor
    public Module(Integer moduleId, String moduleName, String moduleInfo, int credits) {
        this.moduleId = moduleId;
        this.moduleName = moduleName;
        this.moduleInfo = moduleInfo;
        this.credits = credits;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleInfo() { return moduleInfo; }

    public int getCredits() {
        return credits;
    }

}
