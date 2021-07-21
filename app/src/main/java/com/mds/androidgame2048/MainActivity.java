package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.onesignal.OneSignal;


public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "05401944-d660-4d54-b1d5-336f7df97b13";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}