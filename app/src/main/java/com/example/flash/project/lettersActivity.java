package com.example.flash.project;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class lettersActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, mm;
    int c1, c2,c3,c4, score=1, level=1;
    TextView question, intScore, lev, highScoreVar;
    String correct;

    String[] lets = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        setContentView(R.layout.activity_letters);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        mm = (Button) findViewById(R.id.backButton);
        btn1 = (Button) findViewById(R.id.a1);
        btn2 = (Button) findViewById(R.id.a2);
        btn3 = (Button) findViewById(R.id.a3);
        btn4 = (Button) findViewById(R.id.a4);

        question = (TextView) findViewById(R.id.textQuestionVariable);
        intScore = (TextView) findViewById(R.id.txtScoreVariable);
        lev = (TextView) findViewById(R.id.txtLevelVariable);
        highScoreVar = (TextView)findViewById(R.id.highScoreVar);

        SharedPreferences prefs = this.getSharedPreferences("myHighScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("Letters", 0); //0 is the default value

        highScoreVar.setText("" + score);

        getLetters();

        mm.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);


    }

    //@TargetApi(Build.VERSION_CODES.M)
    public void getLetters(){
        int ran;
        c1 = 1 + (int)(Math.random()*26);
        c2 = 1 + (int)(Math.random()*26);
        while(c2==c1){
            c2 = 1 + (int)(Math.random()*26);
        }
        c3 = 1 + (int)(Math.random()*26);
        while(c3==c2||c3==c1){
            c3 = 1 + (int)(Math.random()*26);
        }
        c4 = 1 + (int)(Math.random()*26);
        while(c4==c3||c4==c2||c4==c1){
            c4 = 1 + (int)(Math.random()*26);
        }

        btn1.setText(lets[c1 - 1].toUpperCase());
        btn1.setTextSize(40);
        btn2.setText(lets[c2 - 1].toUpperCase());
        btn2.setTextSize(40);
        btn3.setText(lets[c3 - 1].toUpperCase());
        btn3.setTextSize(40);
        btn4.setText(lets[c4-1].toUpperCase());
        btn4.setTextSize(40);

        ran = 1 + (int)(Math.random()*4);

        switch (ran){
            case 1:
                question.setText(lets[c1-1]);
                correct=lets[c1-1].toUpperCase();
                break;
            case 2:
                question.setText(lets[c2-1]);
                correct=lets[c2-1].toUpperCase();
                break;
            case 3:
                question.setText(lets[c3-1]);
                correct=lets[c3-1].toUpperCase();
                break;
            case 4:
                question.setText(lets[c4-1]);
                correct=lets[c4-1].toUpperCase();
                break;

        }
        System.out.println(ran + " " +correct);

    }

    @Override
    public void onClick(View v) {
        String txtLevel, txtScore;

        if(v.getId()==R.id.backButton){
            startActivity(new Intent(this, main_Activity.class));
            this.finish();
        }

        if(level<8){
            if(v.getId()==R.id.a1){
                if(btn1.getText().toString().equals(correct)){
                    System.out.println("correct/n" + btn1.getText().toString() + " : " + correct);
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
                else{
                    System.out.println("incorrect/n" + btn1.getText().toString() + " : " + correct);
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
            }
            else if(v.getId()==R.id.a2){
                if(btn2.getText().toString().equals(correct)){
                    System.out.println("correct/n" + btn2.getText().toString() + " : " + correct);
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
                else{
                    System.out.println("incorrect/n" + btn2.getText().toString() + " : " + correct);
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
            }
            else if(v.getId()==R.id.a3){
                if(btn3.getText().toString().equals(correct)){
                    System.out.println("correct/n" + btn3.getText().toString() + " : " + correct);
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
                else{
                    System.out.println("incorrect/n" + btn3.getText().toString() + " : " + correct);
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
            }
            else if(v.getId()==R.id.a4){
                if (btn4.getText().toString().equals(correct)){
                    System.out.println("correct/n" + btn4.getText().toString() + " : " + correct);
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }
                else{
                    System.out.println("incorrect/n" + btn4.getText().toString() + " : " + correct);
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getLetters();
                }//end else
            }//end if
        }//end if for counter
        else{
            Intent intent = new Intent(this, gameOverActivity.class);
            intent.putExtra("Score", score);
            intent.putExtra("Game", "Letters");
            startActivity(intent);
            this.finish();
        }//end else to end game
    }//end method

    //view settings for background functionality
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
}
