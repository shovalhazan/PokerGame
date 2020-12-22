package com.example.pokergame;

import android.app.ActionBar;
import android.app.Application;
import android.view.View;

import com.example.pokergame.utils.SP;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SP.init(this);
        MySignal.init(this);
    }

}
