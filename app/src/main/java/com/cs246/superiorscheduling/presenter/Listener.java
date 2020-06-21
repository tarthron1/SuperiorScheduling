package com.cs246.superiorscheduling.presenter;
// Interfaces to listen for new data and or changes
public interface Listener {
    void notifyChangeOnCloud();
    void notifyNewDataToSave();
}
