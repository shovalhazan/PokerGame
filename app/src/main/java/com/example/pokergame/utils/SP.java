package com.example.pokergame.utils;
import android.content.Context;
import android.content.SharedPreferences;

public class SP {


    private static SP instance;
    private SharedPreferences prefs;

    private SP(Context context){
        prefs=context.getSharedPreferences("MY_SP",Context.MODE_PRIVATE);

    }

    public static void init(Context context){
        //singleton design pattern
        if(instance==null)
            instance=new SP(context.getApplicationContext());
    }

    public static SP getInstance() {
        return instance;
    }

    public void putString(String key, String val) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.apply();
    }


    public String getString(String key,String def){
        return  prefs.getString(key,def);
    }

    public int getCount() {
        return  prefs.getAll().size();
    }

    public void removeKey(String key) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(key).apply();
    }

    public void clearChach(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
    }

}
