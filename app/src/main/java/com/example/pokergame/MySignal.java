package com.example.pokergame;

import android.content.Context;
import android.media.MediaParser;
import android.media.MediaPlayer;

public class MySignal {
    private static MySignal instance;
    private Context context;

    public MySignal(Context context){
        this.context=context.getApplicationContext();
    }

    public static void init(Context context){
        if(instance==null)
            instance=new MySignal(context);
    }

    public void play(int id){
        MediaPlayer mp=MediaPlayer.create(context,id);
        mp.start();
    }

    public static MySignal getInstance() {
        return instance;
    }
}
