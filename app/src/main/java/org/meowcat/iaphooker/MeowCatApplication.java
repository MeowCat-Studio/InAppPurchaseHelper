package org.meowcat.iaphooker;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;

public class MeowCatApplication extends AppCompatActivity {
    private static Context context;

    public static Context getContext()
    {
        return context;
    }

    public void onCreate()
    {
        context = getApplicationContext();
    }
}
