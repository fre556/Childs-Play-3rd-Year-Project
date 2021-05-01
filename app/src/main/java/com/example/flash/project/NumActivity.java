package com.example.flash.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class NumActivity extends AppCompatActivity implements View.OnClickListener {

    Button num1, num2, num3, num4, mm;
    int correct, c1, c2,c3,c4, score=1, level=1;
    TextView question, intScore, lev, highScoreVar;

    String[] nums = {"One", "Two","Three", "Four","Five", "Six","Seven", "Eight","Nine", "Ten"};
    String[] numbers = {"1","2","3","4","5","6","7","8","9","10"};

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            /*mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);*/
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_num);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);



        num1 = (Button) findViewById(R.id.num1);
        num2 = (Button) findViewById(R.id.num2);
        num3 = (Button) findViewById(R.id.num3);
        num4 = (Button) findViewById(R.id.num4);
        mm = (Button) findViewById(R.id.mainMenuBut);
        question = (TextView) findViewById(R.id.question);
        intScore = (TextView) findViewById(R.id.scoreText);
        lev = (TextView) findViewById(R.id.level);
        highScoreVar = (TextView)findViewById(R.id.highScoreVar);

        SharedPreferences prefs = this.getSharedPreferences("myHighScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("Numbers", 0); //0 is the default value

        highScoreVar.setText("" + score);

        getNums();

        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        mm.setOnClickListener(this);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    public void getNums(){
        int ran;
        c1 = (int)(Math.random()*9);
        c2 = (int)(Math.random()*9);
        while(c2==c1){
            c2 = (int)(Math.random()*9);
        }
        c3 = (int)(Math.random()*9);
        while(c3==c2||c3==c1){
            c3 = (int)(Math.random()*9);
        }
        c4 = 1 + (int)(Math.random()*9);
        while(c4==c3||c4==c2||c4==c1){
            c4 = (int)(Math.random()*9);
        }


        num1.setText(numbers[c1]);
        num2.setText(numbers[c2]);
        num3.setText(numbers[c3]);
        num4.setText(numbers[c4]);

        ran = 1 + (int)(Math.random()*4);
        System.out.println("Numbers are : " + nums[c1] + " " + nums[c2] + " " + nums[c3] + " " + nums[c4]);
        System.out.println("Correct check: " + ran);

        switch(ran){
            case 1:
                correct = c1 +1;
                System.out.println(correct + " 1 + " + (c1+1));
                question.setText(nums[c1]);
                break;
            case 2:
                correct = c2 +1;
                System.out.println(correct + " 2+ " + (c2+1));
                question.setText(nums[c2]);
                break;
            case 3:
                correct = c3 +1;
                System.out.println(correct + " 3+ " + (c3+1));
                question.setText(nums[c3]);
                break;
            case 4:
                correct = c4 +1;
                System.out.println(correct + " 4+ " + (c4+1));
                question.setText(nums[c4]);
                break;
        }



    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.mainMenuBut){
            startActivity(new Intent(this, main_Activity.class));
            this.finish();
        }

        if(level<8){
            String val, txt, sScore;
            int value;

            if(v.getId()==R.id.num1){
                val = (String) num1.getText();
                value = Integer.parseInt(val);
                if(value==correct){
                    score++;
                    System.out.println("correct " + 1);
                    sScore = String.valueOf(score);
                    intScore.setText(sScore);
                    level++;
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }//end if
                else {
                    level++;
                    System.out.println("incorrect " + 1);
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }
            }
            else if(v.getId()==R.id.num2){
                val = (String) num2.getText();
                value = Integer.parseInt(val);
                if(value==correct){
                    score++;
                    System.out.println("correct " + 2);
                    sScore = String.valueOf(score);
                    intScore.setText(sScore);
                    level++;
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }//end if
                else{
                    level++;
                    System.out.println("incorrect " + 2);
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }
            }
            else if(v.getId()==R.id.num3){
                val = (String) num3.getText();
                value = Integer.parseInt(val);
                if(value==correct){
                    score++;
                    System.out.println("correct " + 3);
                    sScore = String.valueOf(score);
                    intScore.setText(sScore);
                    level++;
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }//end if
                else{
                    level++;
                    System.out.println("incorrect " + 3);
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }
            }
            else if(v.getId()==R.id.num4){
                val = (String) num2.getText();
                value = Integer.parseInt(val);
                if(value==correct){
                    score++;
                    System.out.println("Correct " + 4);
                    sScore = String.valueOf(score);
                    intScore.setText(sScore);
                    level++;
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }//end if
                else{
                    level++;
                    System.out.println("incorrect " + 4);
                    txt = String.valueOf(level);
                    lev.setText(txt);
                    getNums();
                }
            }

        }//end if
        else{
            Intent intent = new Intent(this, gameOverActivity.class);
            intent.putExtra("Score", score);
            intent.putExtra("Game", "Numbers");
            startActivity(intent);
            this.finish();
        }

    }//end onClick

    public int num(String num){
        int ret=0;
        switch (num){
            case "One":
                ret= 1;
                break;
            case "Two":
                ret= 2;
                break;
            case "Three":
                ret= 3;
                break;
            case "Four":
                ret= 4;
                break;
            case "Five":
                ret= 5;
                break;
            case "Six":
                ret= 6;
                break;
            case "Seven":
                ret= 7;
                break;
            case "Eight":
                ret= 8;
                break;
            case "Nine":
                ret= 9;
                break;
            case "Ten":
                ret=10;
                break;
        }
        return ret;
    }
}//end class
