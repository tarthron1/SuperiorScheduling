package com.cs246.superiorscheduling.presenter;

public class MainPresenter {


    public interface Listener{
        public void notifyDataReady();
        public void notifyConfigChanged();
    }
}
