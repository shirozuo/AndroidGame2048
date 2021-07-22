package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {
    private static final String ONESIGNAL_APP_ID = "05401944-d660-4d54-b1d5-336f7df97b13";
    ProgressBar progressBar;
    Handler handler = new Handler();
    int progress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // OneSignal Initialization
        OneSignal.initWithContext(MainActivity.this.getBaseContext());
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        Log.i("TST", "OneSignal init");

        progressBar = findViewById(R.id.ProgressBar);
        new Thread(() -> {
            while (progress < 100) {
                handler.post(() -> progressBar.setProgress(progress));

                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                progress++;
            }

            OneSignal.setNotificationOpenedHandler(new NotificationOpenedHandler());
            startGame();
        }).start();
    }

    public void startGame() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    private class NotificationOpenedHandler implements OneSignal.OSNotificationOpenedHandler {

        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            Intent intent = new Intent(getApplication(), com.mds.androidgame2048.URLActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.i("ACT", "Move to WebActivity");
            startActivity(intent);
        }
    }
}

