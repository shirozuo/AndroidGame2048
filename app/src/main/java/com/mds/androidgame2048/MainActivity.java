package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ProgressBar;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "05401944-d660-4d54-b1d5-336f7df97b13";
    ProgressBar progressBar;
    Handler handler = new Handler();
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        OneSignal.initWithContext(MainActivity.this.getBaseContext());
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.setNotificationOpenedHandler(new NotificationOpenedHandler());


//        progressBar = findViewById(R.id.progressBar);
//        new Thread(() -> {
//            while (progress < 100) {
//                handler.post(() -> {
//                    // OneSignal Initialization
//                    progressBar.setProgress(progress);
//                });
//                progress++;
//            }
//        }).start();
    }

    private class NotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            Intent intent = new Intent(getApplication(), URLActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}

