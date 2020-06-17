package com.cs246.superiorscheduling.presenter;

import java.util.ArrayList;

public class MainPresenter {

    private ArrayList<MainPresenter.Listener> registeredDataListeners = new ArrayList<>();

    public void registerUsers(MainPresenter.Listener listener){
        registeredDataListeners.add(listener);
    }
    
    public void notifyUsersChangeOnCloud(){
        for (MainPresenter.Listener listener: this.registeredDataListeners
             ) {
            listener.notifyChangeOnCloud();
        }
    }

    public void notifyCloudNewDataToSave(){
        for (MainPresenter.Listener listener: this.registeredDataListeners
             ) {
            listener.notifyNewDataToSave();
        }
    }

    public interface Listener{
        public void notifyChangeOnCloud();
        public void notifyNewDataToSave();
    }
}
