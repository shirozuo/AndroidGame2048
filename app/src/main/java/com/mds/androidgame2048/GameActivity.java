package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private HashMap<String, Integer> map = null;
    private HashMap<String, Integer> preMap = null;
    private GridView gridView = null;
    private TextView textView;
    private int score = 0;

    public GameActivity(HashMap map){
        this.map = map;
    }
    public GameActivity(){
        this.map = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = findViewById(R.id.text_score);
        gridView = findViewById(R.id.gamebackground);
        Random random1 = new Random();
        int r1 = random1.nextInt(16-1+1)+1;
        Random random2 = new Random();
        int r2 = random2.nextInt(16-1+1)+1;
        if (r1 == r2 && r2!=16){
            r2++;
        } else {
            if (r1==r2){
                r2--;
            }
        }
        Log.i("R1+R2", r1 + " " + r2);

        String[] numStrArray =new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        Random random3 = new Random();
        Random random4 = new Random();
        int[] choice = {2,4};
        int r3 = random3.nextInt(choice.length);
        int r4 = random4.nextInt(choice.length);

        Log.i("CH1+CH2", choice[r3] + " " + choice[r4]);
        for (int i=0; i<numStrArray.length; i++){
            if (Integer.parseInt(numStrArray[i]) == r1){
                map.put(numStrArray[i], choice[r3]);
            }
            if (Integer.parseInt(numStrArray[i]) == r2){
                map.put(numStrArray[i], choice[r4]);
            }
            if (Integer.parseInt(numStrArray[i]) != r1 && Integer.parseInt(numStrArray[i]) != r2){
                map.put(numStrArray[i], 0);
            }
        }
        Log.i("MAP", map.toString());
        final List<String> tileList = new ArrayList<String>(Arrays.asList(numStrArray));

        //TODO grid here
    }
}