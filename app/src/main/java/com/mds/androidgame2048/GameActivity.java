package com.mds.androidgame2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    HashMap<String, Integer> map;
    HashMap<String, Integer> preMap = null;
    GridView gridView = null;
    TextView textView = null;
    int score = 0;

    public GameActivity(HashMap map) {
        this.map = map;
    }

    public GameActivity() {
        this.map = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        textView = findViewById(R.id.textScore);
        gridView = findViewById(R.id.gameBackground);
        Random random1 = new Random();
        int r1 = random1.nextInt(16 - 1 + 1) + 1;
        Random random2 = new Random();
        int r2 = random2.nextInt(16 - 1 + 1) + 1;
        if (r1 == r2 && r2 != 16) {
            r2++;
        } else {
            if (r1 == r2) {
                r2--;
            }
        }
        Log.i("R1+R2", r1 + " " + r2);

        String[] numStrArray = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
        Random random3 = new Random();
        Random random4 = new Random();
        int[] choice = {2, 4};
        int r3 = random3.nextInt(choice.length);
        int r4 = random4.nextInt(choice.length);

        Log.i("CH1+CH2", choice[r3] + " " + choice[r4]);
        for (int i = 0; i < numStrArray.length; i++) {
            if (Integer.parseInt(numStrArray[i]) == r1) {
                map.put(numStrArray[i], choice[r3]);
            }
            if (Integer.parseInt(numStrArray[i]) == r2) {
                map.put(numStrArray[i], choice[r4]);
            }
            if (Integer.parseInt(numStrArray[i]) != r1 && Integer.parseInt(numStrArray[i]) != r2) {
                map.put(numStrArray[i], 0);
            }
        }
        Log.i("MAP", map.toString());

        final List<String> tileList = new ArrayList<>(Arrays.asList(numStrArray));
        gridChanger(tileList);

        gridView.setOnTouchListener(new OnSwipeTouchListener(GameActivity.this) {
            @SuppressLint("SetTextI18n")
            public void onSwipeTop() {
                preMap = new HashMap<>();
                Log.i("MOV", "TOP");
                Log.i("MAP", map.toString());
                Log.i("BLN", isTop(1) + " " + isTop(2) + " " + isTop(3) + " " + isTop(4));
                int k = 0;
                while (!(isTop(1) && isTop(2) && isTop(3) && isTop(4))) {
                    k = 1;
                    for (int i = 16; i > 4; i--) {
                        int j = i;
                        if (j > 4 && map.get(j + "") == map.get((j - 4) + "")) {
                            map.put((j - 4) + "", map.get(j + "") + map.get((j - 4) + ""));
                            map.put(j + "", 0);
                            String score = textView.getText().toString();
                            int scorePoints = Integer.parseInt(score);
                            scorePoints += map.get((j - 4) + "");
                            textView.setText(scorePoints + "");
                        } else if (j > 4 && map.get((j - 4) + "") == 0) {
                            map.put((j - 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }
                Log.i("MAP", map.toString());

                gridChanger(tileList);
                generateRandomNumber();
                checkSwipe();
            }

            @SuppressLint("SetTextI18n")
            public void onSwipeRight() {
                preMap = new HashMap<>();
                Log.i("MOV", "RIGHT");
                Log.i("MAP", map.toString());
                Log.i("BLN", isRight(4) + " " + isRight(8) + " " + isRight(12) + " " + isRight(16));
                int k = 0;
                int l = 0;
                while (l == 0 || !(isRight(4) && isRight(8) && isRight(12) && isRight(16))) {
                    l = 1;
                    for (int i = 1; i < 17; i++) {
                        int j = i;
                        if (k == 0 && map.get(j + "") == map.get((j + 1) + "")) {
                            map.put((j + 1) + "", map.get(j + "") + map.get((j + 1) + ""));
                            map.put(j + "", 0);
                            String score = textView.getText().toString();
                            int scorePoints = Integer.parseInt(score);
                            scorePoints += map.get(j + "") + map.get((j + 1) + "");
                            textView.setText(scorePoints + "");
                        } else if (k == 0 && map.get((j + 1) + "") == 0) {
                            map.put((j + 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                        if ((j / 4) != (j + 1) / 4) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                    }
                }
                Log.i("MAP", map.toString());
                gridChanger(tileList);
                generateRandomNumber();
                checkSwipe();
            }

            @SuppressLint("SetTextI18n")
            public void onSwipeBottom() {
                preMap = new HashMap<>(map);
                Log.i("MOV", "BOTTOM");
                Log.i("MAP", map.toString());
                Log.i("BLN", isBottom(13) + " " + isBottom(14) + " " + isBottom(15) + " " + isBottom(16));
                int k = 0;
                while (k == 0 || !(isBottom(13) && isBottom(14) && isBottom(15) && isBottom(16))) {
                    k = 1;
                    for (int i = 1; i < 13; i++) {
                        int j = i;

                        if (j < 13 && map.get(j + "") == map.get((j + 4) + "")) {
                            map.put((j + 4) + "", map.get(j + "") + map.get((j + 4) + ""));
                            map.put(j + "", 0);
                            String score = textView.getText().toString();
                            int scorePoints = Integer.parseInt(score);
                            scorePoints += map.get((j + 4) + "");
                            textView.setText(scorePoints + "");
                        } else if (j < 13 && map.get((j + 4) + "") == 0) {
                            map.put((j + 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }
                gridChanger(tileList);
                generateRandomNumber();
                checkSwipe();
            }

            @SuppressLint("SetTextI18n")
            public void onSwipeLeft() {
                preMap = new HashMap<>(map);
                Log.i("MOV", "LEFT");
                Log.i("MAP", map.toString());
                Log.i("BLN", isLeft(1) + " " + isLeft(5) + " " + isLeft(9) + " " + isLeft(13));
                int k = 0;
                int l = 0;
                while (l == 0 || !(isLeft(1) && isLeft(5) && isLeft(9) && isLeft(13))) {
                    l = 1;
                    for (int i = 16; i > 1; i--) {
                        int j = i;
                        if (j == 13 || j == 9 || j == 5) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                        if (k == 0 && map.get(j + "") == map.get((j - 1) + "")) {
                            map.put((j - 1) + "", map.get(j + "") + map.get((j - 1) + ""));
                            map.put(j + "", 0);
                            String score = textView.getText().toString();
                            int scorePoints = Integer.parseInt(score);
                            scorePoints += map.get((j - 1) + "");
                            textView.setText(scorePoints + "");
                        } else if (k == 0 && map.get((j - 1) + "") == 0) {
                            map.put((j - 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }
                Log.i("MAP", map.toString());
                gridChanger(tileList);
                generateRandomNumber();
                checkSwipe();
            }
        });

    }

    public boolean isTop(int loc) {
        if (map.get(loc + "") == 0 && map.get((loc + 4) + "") == 0 && map.get("" + (loc + 8)) == 0 && map.get("" + (loc + 12)) == 0) {
            return true;
        } else if (map.get((loc + 4) + "") == 0 && map.get("" + (loc + 8)) == 0 && map.get("" + (loc + 12)) == 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc + 8)) == 0 && map.get("" + (loc + 12)) == 0 && map.get("" + (loc + 4)) != 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc + 12)) == 0 && map.get("" + (loc + 4)) != 0 && map.get("" + loc) != 0 && map.get("" + (loc + 8)) != 0) {
            return true;
        } else if (map.get(loc + "") != 0 && map.get((loc + 4) + "") != 0 && map.get("" + (loc + 8)) != 0 && map.get("" + (loc + 12)) != 0) {
            return true;
        }
        return false;
    }

    public boolean isRight(int loc) {
        if (map.get(loc + "") == 0 && map.get((loc - 1) + "") == 0 && map.get("" + (loc - 2)) == 0 && map.get("" + (loc - 3)) == 0) {
            return true;
        } else if (map.get((loc - 1) + "") == 0 && map.get("" + (loc - 2)) == 0 && map.get("" + (loc - 3)) == 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc - 2)) == 0 && map.get("" + (loc - 3)) == 0 && map.get("" + (loc - 1)) != 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc - 3)) == 0 && map.get("" + (loc - 1)) != 0 && map.get("" + loc) != 0 && map.get("" + (loc - 2)) != 0) {
            return true;
        } else if (map.get(loc + "") != 0 && map.get((loc - 1) + "") != 0 && map.get("" + (loc - 2)) != 0 && map.get("" + (loc - 3)) != 0) {
            return true;
        }
        return false;
    }

    public boolean isBottom(int loc) {
        if (map.get(loc + "") == 0 && map.get((loc - 4) + "") == 0 && map.get("" + (loc - 8)) == 0 && map.get("" + (loc - 12)) == 0) {
            return true;
        } else if (map.get((loc - 4) + "") == 0 && map.get("" + (loc - 8)) == 0 && map.get("" + (loc - 12)) == 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc - 8)) == 0 && map.get("" + (loc - 12)) == 0 && map.get("" + (loc - 4)) != 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc - 12)) == 0 && map.get("" + (loc - 4)) != 0 && map.get("" + loc) != 0 && map.get("" + (loc - 8)) != 0) {
            return true;
        } else if (map.get(loc + "") != 0 && map.get((loc - 4) + "") != 0 && map.get("" + (loc - 8)) != 0 && map.get("" + (loc - 12)) != 0) {
            return true;
        }
        return false;
    }

    public boolean isLeft(int loc) {
        if (map.get(loc + "") == 0 && map.get((loc + 1) + "") == 0 && map.get("" + (loc + 2)) == 0 && map.get("" + (loc + 3)) == 0) {
            return true;
        } else if (map.get((loc + 1) + "") == 0 && map.get("" + (loc + 2)) == 0 && map.get("" + (loc + 3)) == 0 && map.get("" + loc) != 0) {
            return true;
        } else if (map.get("" + (loc + 2)) == 0 && map.get("" + (loc + 3)) == 0 && map.get("" + (loc + 1)) != 0 && map.get("" + (loc)) != 0) {
            return true;
        } else if (map.get("" + (loc + 3)) == 0 && map.get("" + (loc + 1)) != 0 && map.get("" + (loc)) != 0 && map.get("" + (loc + 2)) != 0) {
            return true;
        } else if (map.get(loc + "") != 0 && map.get((loc + 1) + "") != 0 && map.get("" + (loc + 2)) != 0 && map.get("" + (loc + 3)) != 0) {
            return true;
        }
        return false;
    }

    @SuppressLint("SetTextI18n")
    public void checkSwipe() {
        ConstraintLayout constraintLayout = findViewById(R.id.gameFieldLayout);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linearLayout.setBackgroundColor(getResources().getColor(R.color.Aqua)); //? https://encycolorpedia.ru/#00FFFF
        linearLayout.setGravity(Gravity.CENTER);
        TextView result = new TextView(this);
        for (String key : map.keySet()) {
            if (map.get(key) == 2048) {
                result.setText("YOU WON!");
                result.setGravity(Gravity.CENTER);
                linearLayout.addView(result);
                constraintLayout.addView(linearLayout);
                Intent intent = new Intent(GameActivity.this, ResultActivity.class);
                intent.putExtra("result", 0);
                startActivity(intent);
                return;
            }
        }

        Log.i("MAP", map.toString());
        Log.i("PRE", preMap.toString());
        for (String key : map.keySet()) {
            if (map.get(key) != preMap.get(key)) {
                return;
            }
        }

        result.setText("YOU LOSE");
        result.setGravity(Gravity.CENTER);
        linearLayout.addView(result);
        constraintLayout.addView(linearLayout);
        Intent intent = new Intent(GameActivity.this, ResultActivity.class);
        intent.putExtra("result", 1);
        startActivity(intent);
    }

    public void generateRandomNumber() {
        Random random = new Random();
        int i = random.nextInt(16) + 1;
        Log.i("GEN", map.toString());
        int flag = 0;
        for (String key : map.keySet()) {
            if (map.get(key) != 0) {
                flag++;
            }
        }
        if (flag != 16) {
            while (map.get(i + "") != 0) {
                i = random.nextInt(16) + 1;
            }
            int[] choice = {2, 4};
            int r1 = random.nextInt(choice.length);
            Log.i("GEN", choice[r1] + " " + i);
            map.put(i + "", choice[r1]);
            String[] num = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
            Log.i("GEN", map.toString());
            List<String> tileList = Arrays.asList(num);

            gridChanger(tileList);
        }
    }

    public void gridChanger(List tileList) {
        Log.i("TST", "GridChanger started");
        final List<String> list = tileList;
        gridView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list) {
            @SuppressLint("SetTextI18n")
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = (TextView) view;
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                textView.setLayoutParams(layoutParams);

                //Our TextView display options
                textView.setWidth(100);
                textView.setHeight(210);
                textView.setGravity(Gravity.CENTER_HORIZONTAL);
                textView.setTextSize(20);

                if (map.get(list.get(position)) != 0) {
                    Log.i("POS", position + "");
                    Log.i("POS", list.get(position));
                    textView.setText(map.get(list.get(position)) + "");
                } else {
                    textView.setText("");
                }

                textView.setId(position);
                textView.setBackgroundColor(getResources().getColor(R.color.BurnishedSilver)); //? https://encycolorpedia.ru/c8c8c8

                Log.i("TST", "GridChanger ends");
                return textView;
            }
        });
    }
}