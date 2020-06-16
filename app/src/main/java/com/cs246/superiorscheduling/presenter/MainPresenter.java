package com.cs246.superiorscheduling.presenter;

import java.util.ArrayList;

public class MainPresenter {

    private ArrayList<MainPresenter.Listener> registeredDataListeners = new ArrayList<>();

    public void registerUsers(MainPresenter.Listener listener){
        registeredDataListeners.add(listener);
    }
    
    public void notifyUsersDataReady(){
        for (MainPresenter.Listener listener: this.registeredDataListeners
             ) {
            listener.notifyDataReady();
        }
    }

    public void notifyUsersConfigChanged(){
        for (MainPresenter.Listener listener: this.registeredDataListeners
             ) {
            listener.notifyConfigChanged();
        }
    }

    public interface Listener{
        public void notifyDataReady();
        public void notifyConfigChanged();
    }
}
