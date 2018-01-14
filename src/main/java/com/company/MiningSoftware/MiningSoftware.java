package com.company.MiningSoftware;

public class MiningSoftware {
    private static final MiningSoftware instance = new MiningSoftware();
    private MiningSoftwareInstallation softwareInstallation;
    private MiningSoftwareUpdate softwareUpdate;


    //private constructor to avoid client applications to use constructor
    private MiningSoftware(){
        softwareInstallation = new MiningSoftwareInstallation();
        softwareUpdate = new MiningSoftwareUpdate();
    }

    public static MiningSoftware getInstance(){
        return instance;
    }

    public boolean installAll() {
        softwareInstallation.installAll();
        return true;
    }

    public boolean updateAll() {
        softwareUpdate.UpdateAll();
        return true;
    }
}
