package com.ajceliano.ajworkorderapp;

import android.app.Activity;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ajceliano.ajworkorderapp.GlobalVars;
import com.ajceliano.ajworkorderapp.R;


public class aLoadingPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);
        GlobalVars.android_ID = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        //Get User info based on device ID
        new GetDeviceUser(this).execute("Users");
    }
}
