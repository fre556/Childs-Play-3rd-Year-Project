package com.example.flash.project;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.*;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class coloursActivity extends AppCompatActivity implements View.OnClickListener {


    //Declare global variables
    Button num1, num2, num3, num4, mainMenu;
    int  a1, a2, a3, a4, score = 1, levelNum = 1;
    String correct;
    TextView question, intScore, level, highScoreVar;
    Activity activity;

    String[] cols = {"black", "blue", "red", "green", "grey", "pink", "white", "yellow"};
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

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                //delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    //--------------------------- on create method ---------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colours);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        num1 = (Button) findViewById(R.id.btnA1);
        num2 = (Button) findViewById(R.id.btnA2);
        num3 = (Button) findViewById(R.id.btnA3);
        num4 = (Button) findViewById(R.id.btnA4);
        mainMenu = (Button) findViewById(R.id.btnMainMenu);
        question = (TextView) findViewById(R.id.txtQuestionVeriable);
        intScore = (TextView) findViewById(R.id.txtScoreVariable);
        level = (TextView) findViewById(R.id.txtLevelVariable);
        highScoreVar = (TextView)findViewById(R.id.highScoreVar);

        SharedPreferences prefs = this.getSharedPreferences("myHighScore", Context.MODE_PRIVATE);
        int score = prefs.getInt("Colours", 0); //0 is the default value

        highScoreVar.setText("" + score);

        getColours();

        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        mainMenu.setOnClickListener(this);

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//end on create method


    public void getColours() {
        int ran;
        a1 = (int) (Math.random() * 7);
        a2 = (int) (Math.random() * 7);
        while (a2 == a1) {
            a2 = (int) (Math.random() * 7);
        }
        a3 = (int) (Math.random() * 7);
        while (a3 == a2 || a3 == a1) {
            a3 = (int) (Math.random() * 7);
        }
        a4 = (int) (Math.random() * 7);
        while (a4 == a3 || a4 == a2 || a4 == a1) {
            a4 = (int) (Math.random() * 7);
        }

        ran = 1 + (int) (Math.random() * 4);


        if(ran==1){
            question.setText(cols[a1]);
            correct = cols[a1];
        }
        else if(ran==2){
            question.setText(cols[a2]);
            correct = cols[a2];
        }
        else if(ran==3){
            question.setText(cols[a3]);
            correct = cols[a3];
        }
        else{
            question.setText(cols[a4]);
            correct = cols[a4];
        }

        System.out.println(a1 + " " + a2 + " " + a3 + " " + a4 + " " + correct);

        for(int i=0;i<4;i++){
            if(i==0){
                switch(a1){
                    case 0:

                        num1.setText(cols[a1]);
                        num1.setTextColor(0xff000000);
                        num1.setBackgroundColor(0xff000000);
                        break;
                    case 1:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xff0000ff);
                        num1.setBackgroundColor(0xff0000ff);
                        break;
                    case 2:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xffff0000);
                        num1.setBackgroundColor(0xffff0000);
                        break;
                    case 3:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xff00ff00);
                        num1.setBackgroundColor(0xff00ff00);
                        break;
                    case 4:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xff444444);
                        num1.setBackgroundColor(0xff444444);
                        break;
                    case 5:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xffff00ff);
                        num1.setBackgroundColor(0xffff00ff);
                        break;
                    case 6:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xffffffff);
                        num1.setBackgroundColor(0xffffffff);
                        break;
                    case 7:
                        num1.setText(cols[a1]);
                        num1.setTextColor(0xffffff00);
                        num1.setBackgroundColor(0xffffff00);
                        break;
                }
            }
            else if(i==1){
                switch(a2){
                    case 0:

                        num2.setText(cols[a2]);
                        num2.setTextColor(0xff000000);
                        num2.setBackgroundColor(0xff000000);
                        break;
                    case 1:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xff0000ff);
                        num2.setBackgroundColor(0xff0000ff);
                        break;
                    case 2:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xffff0000);
                        num2.setBackgroundColor(0xffff0000);
                        break;
                    case 3:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xff00ff00);
                        num2.setBackgroundColor(0xff00ff00);
                        break;
                    case 4:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xff444444);
                        num2.setBackgroundColor(0xff444444);
                        break;
                    case 5:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xffff00ff);
                        num2.setBackgroundColor(0xffff00ff);
                        break;
                    case 6:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xffffffff);
                        num2.setBackgroundColor(0xffffffff);
                        break;
                    case 7:
                        num2.setText(cols[a2]);
                        num2.setTextColor(0xffffff00);
                        num2.setBackgroundColor(0xffffff00);
                        break;
                }
            }
            else if(i==2){
                switch(a3){
                    case 0:

                        num3.setText(cols[a3]);
                        num3.setTextColor(0xff000000);
                        num3.setBackgroundColor(0xff000000);
                        break;
                    case 1:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xff0000ff);
                        num3.setBackgroundColor(0xff0000ff);
                        break;
                    case 2:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xffff0000);
                        num3.setBackgroundColor(0xffff0000);
                        break;
                    case 3:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xff00ff00);
                        num3.setBackgroundColor(0xff00ff00);
                        break;
                    case 4:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xff444444);
                        num3.setBackgroundColor(0xff444444);
                        break;
                    case 5:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xffff00ff);
                        num3.setBackgroundColor(0xffff00ff);
                        break;
                    case 6:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xffffffff);
                        num3.setBackgroundColor(0xffffffff);
                        break;
                    case 7:
                        num3.setText(cols[a3]);
                        num3.setTextColor(0xffffff00);
                        num3.setBackgroundColor(0xffffff00);
                        break;
                }
            }
            else{
                switch(a4){
                    case 0:

                        num4.setText(cols[a4]);
                        num4.setTextColor(0xff000000);
                        num4.setBackgroundColor(0xff000000);
                        break;
                    case 1:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xff0000ff);
                        num4.setBackgroundColor(0xff0000ff);
                        break;
                    case 2:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xffff0000);
                        num4.setBackgroundColor(0xffff0000);
                        break;
                    case 3:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xff00ff00);
                        num4.setBackgroundColor(0xff00ff00);
                        break;
                    case 4:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xff444444);
                        num4.setBackgroundColor(0xff444444);
                        break;
                    case 5:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xffff00ff);
                        num4.setBackgroundColor(0xffff00ff);
                        break;
                    case 6:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xffffffff);
                        num4.setBackgroundColor(0xffffffff);
                        break;
                    case 7:
                        num4.setText(cols[a4]);
                        num4.setTextColor(0xffffff00);
                        num4.setBackgroundColor(0xffffff00);
                        break;
                }//end switch
            }//end else
        }//end for
    }//end get colours

    public void onClick(View v) {
        String txtScore, txtLevel;
        if (v.getId() == R.id.btnMainMenu) {
            startActivity(new Intent(this, main_Activity.class));
            this.finish();
        }
        if (levelNum < 8) {
            if (v.getId() == R.id.btnA1) {
                if (num1.getText() == correct) {   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num1.getText());
                    getColours();
                } else {
                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num1.getText());
                    getColours();
                }
            } else if (v.getId() == R.id.btnA2) {
                if (num2.getText() == correct) {   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num2.getText());
                    getColours();
                } else {
                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num2.getText());
                    getColours();
                }
            } else if (v.getId() == R.id.btnA3) {
                if (num3.getText() == correct) {   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num3.getText());
                    getColours();
                } else {
                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num3.getText());
                    getColours();
                }
            } else if (v.getId() == R.id.btnA4) {
                if (num4.getText() == correct) {   // ---------------- This is the conditions to check -----------
                    score++;
                    txtScore = String.valueOf(score);
                    intScore.setText(txtScore);

                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num4.getText());
                    getColours();
                } else {
                    levelNum++;
                    txtLevel = String.valueOf(levelNum);
                    level.setText(txtLevel);
                    System.out.println(num4.getText());
                    getColours();
                }//end else
            }//end if
        }//end if for counter
        else {
            Intent intent = new Intent(this, gameOverActivity.class);
            intent.putExtra("Score", score);
            intent.putExtra("Game", "Colours");
            startActivity(intent);
            this.finish();
        }//end else to end game
    }


    //------------------ View methods -----------------------
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
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

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "colours Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.flash.project/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "colours Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.flash.project/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */

}
