package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        int result = getIntent().getExtras().getInt("result");
        TextView textView = findViewById(R.id.resultTextView);
        Button button = findViewById(R.id.resultButton);
        if(result == 0){
            textView.setText("YOU WON!");
            button.setText("Go to main");
        } else {
            textView.setText("YOU LOSE!");
            button.setText("Try again");
        }


    }

    public void retry(View view) {
        Intent intent = new Intent(ResultActivity.this, GameActivity.class);
        startActivity(intent);
    }
}