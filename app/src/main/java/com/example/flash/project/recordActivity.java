package com.example.flash.project;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.*;
import android.os.*;
import android.widget.*;
import android.media.*;
import android.util.Log;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.view.View;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import java.io.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class recordActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMain, btnRecord, btnSave, btnPlay;
    //,
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;

    private MediaRecorder mRecorder = null;

    //    private PlayButton   btnPlay = null;
    private MediaPlayer mPlayer = null;

    private int counter = 0, i = 0;

    private String[] questionsMain = {"Press the Letter ", "Press the Colour ", "Press the Number ", "What does "};

    private String[] questionsLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private String[] questionsColour = {"black", "blue", "red", "green", "grey", "pink", "white", "yellow"};
    private String[] questionsNumber = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private String[] questionsSum = {"5 + 3 =", "4 + 2 =", "1 + 1 =", "7 + 2 =", "3 + 4 =", "2 + 3 =", "4 + 1 = ", "5 + 5 = ", " 6 + 2 =", "2 + 2 ="};


    TextView txtQuestion;
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
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void savePlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        //String ToastMessageShow = "";
        //ToastMessageShow("Preparing the Voice Recorder.",record.keywords.Common.False);
        mRecorder = new MediaRecorder();
        System.out.println("new recorder");
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        System.out.println("audio source complete");
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        //mRecorder.setOutputFile(mFileName);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/panic_record/" + System.currentTimeMillis() + ".mp4";
        mRecorder.setOutputFile(path);
        System.out.println("audio path complete");

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {

        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }


    /*
    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText("Stop recording");
                } else {
                    setText("Start recording");
                }
                mStartRecording = !mStartRecording;
            }
        };
        public RecordButton(Context ctx) {
            super(ctx);
            setText("Start recording");
            setOnClickListener(clicker);
        }
    }
    */


    public recordActivity() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_record);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        btnRecord = (Button) findViewById(R.id.btnRecord);
        btnMain = (Button) findViewById(R.id.btnMain);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnSave = (Button) findViewById(R.id.btnSave);

        txtQuestion = (TextView) findViewById(R.id.txtVariableText);

        btnMain.setOnClickListener(this);
        btnRecord.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnPlay.setOnClickListener(this);

        btnSave.setEnabled(false);

        questions();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

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

    public void questions() {
        for (i = i; i < 53; i++) {
            System.out.println(i);
            if (i < 26) {
                txtQuestion.setText(questionsMain[0] + questionsLetters[i]);
                break;
            } else if (i < 25 && i < 33) {
                txtQuestion.setText(questionsMain[1] + questionsColour[counter]);
                break;
            } else if (i > 32 && i < 42) {
                if (i == 33) {
                    counter = 0;
                }
                txtQuestion.setText(questionsMain[2] + questionsNumber[counter]);
                break;
            } else {
                if (i == 43) {
                    counter = 0;
                }
                txtQuestion.setText(questionsMain[3] + questionsSum[counter]);
                break;
            }
        }
    }


    //actions
    public void onClick(View v) {

        if (v.getId() == R.id.btnMain) {
            startActivity(new Intent(this, main_Activity.class));
            this.finish();
        }


        if (v.getId() == R.id.btnRecord) {
            System.out.println("Call Record");
            startRecording();
        }

        if (v.getId() == R.id.btnSave) {
            stopRecording();
        }
        /*
        if(v.getId()==R.id.btnPlay){
            startPlaying();
        }
        */

    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "record Page", // TODO: Define a title for the content shown.
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
                "record Page", // TODO: Define a title for the content shown.
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
}
