package com.example.flash.project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class sumsActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, mainMenu;
    int correct, c1, c2, c3, c4, score = 1, level = 1;
    TextView question, intScore, lev, highScoreVar;

    String[] sums = {"1 + 0 =","4 + 2 =","1 + 1 =","7 + 2 =","3 + 4 =","2 + 1 =","4 + 1 = ","5 + 5 = "," 6 + 2 =","2 + 2 ="};
    String[] ans = {"1","6","2","9","7","3","5","10","8","4"};
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
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sums);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        /*mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });*/

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        btn1 = (Button) findViewById(R.id.btnA1);
        btn2 = (Button) findViewById(R.id.btnA2);
        btn3 = (Button) findViewById(R.id.btnA3);
        btn4 = (Button) findViewById(R.id.btnA4);
        mainMenu = (Button) findViewById(R.id.btnMainMenu);
        question = (TextView) findViewById(R.id.txtQuestionVariable);
        intScore = (TextView) findViewById(R.id.txtScoreVariable);
        lev = (TextView) findViewById(R.id.txtLevelVariable);
        highScoreVar = (TextView)findViewById(R.id.highScoreVar);

        SharedPreferences prefs = this.getSharedPreferences("myHighScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("Sums", 0); //0 is the default value

        highScoreVar.setText("" + score);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        mainMenu.setOnClickListener(this);

        getSums();

    }

    public void getSums(){
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

        btn1.setText(ans[c1]);
        btn2.setText(ans[c2]);
        btn3.setText(ans[c3]);
        btn4.setText(ans[c4]);
        System.out.println(ans[c1] + " " + ans[c2] + " " + ans[c3] + " " + ans[c4] + " ");

        ran = 1 + (int)(Math.random()*3);

        switch(ran){
            case 1:
                correct = Integer.parseInt(ans[c1]);
                System.out.println(correct + " 1 + " + (c1));
                question.setText(sums[c1]);
                break;
            case 2:
                correct = Integer.parseInt(ans[c2]);
                System.out.println(correct + " 2+ " + (c2));
                question.setText(sums[c2]);
                break;
            case 3:
                correct = Integer.parseInt(ans[c3]);
                System.out.println(correct + " 3+ " + (c3));
                question.setText(sums[c3]);
                break;
            case 4:
                correct = Integer.parseInt(ans[c4]);
                System.out.println(correct + " 4+ " + (c4));
                question.setText(sums[c4]);
                break;
        }

    }

    public void onClick(View v) {
        int val;
        String value;
        String txtScore, txtLevel;
        if(v.getId()==R.id.btnMainMenu){
            startActivity(new Intent(this, main_Activity.class));
            this.finish();
        }
        if(level<8){
            if(v.getId()==R.id.btnA1){
                value = (String) btn1.getText();
                val = Integer.parseInt(value);
                if(val==correct){   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
                else{
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
            }
            else if(v.getId()==R.id.btnA2){
                value = (String) btn2.getText();
                val = Integer.parseInt(value);
                if(val==correct){   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
                else{
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
            }
            else if(v.getId()==R.id.btnA3){
                value = (String) btn3.getText();
                val = Integer.parseInt(value);
                if(val==correct){   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
                else{
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
            }
            else if(v.getId()==R.id.btnA4){
                value = (String) btn4.getText();
                val = Integer.parseInt(value);
                if(val==correct){   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }
                else{
                    level++;
                    txtLevel = String.valueOf(level);
                    lev.setText(txtLevel);
                    getSums();
                }//end else
            }//end if
        }//end if for counter
        else{
            Intent intent = new Intent(this, gameOverActivity.class);
            intent.putExtra("Score", score);
            intent.putExtra("Game", "Sums");
            startActivity(intent);
            this.finish();
        }//end else to end game
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
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
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.

        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "shapes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.flash.project/http/host/path")
        );

    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "shapes Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.flash.project/http/host/path")
        );

    }
}
