package com.cs246.superiorscheduling.presenter;

public class MainPresenter {

    interface Listener{
        void notifyDataReady();

        void notifyConfigChanged();
    }
}
