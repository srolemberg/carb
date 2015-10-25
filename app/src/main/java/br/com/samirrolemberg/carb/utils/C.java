package br.com.samirrolemberg.carb.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by samir on 05/02/2015.
 */
public class C extends Application{
    private static Context context;

    public static Context getContext(){
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
    }

}
