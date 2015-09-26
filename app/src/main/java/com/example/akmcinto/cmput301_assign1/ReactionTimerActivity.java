package com.example.akmcinto.cmput301_assign1;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

// Gson code from https://github.com/google/gson
// Instructions on how to add: lepoetemaudit, http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library, 2015-09-25
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ReactionTimerActivity extends AppCompatActivity {

    ReactionButton reactionButton;
    // Create the filenames for persistent app data
    public String REACTION_FILE_NAME = "reactionTimerDataFile";
    private ArrayList<Long> reactionTimes = new ArrayList<Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_timer);

        Button button = (Button) findViewById(R.id.reactionButton);
        reactionButton = new ReactionButton(button);

        loadReactionTime();

        ControlButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reaction_timer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ControlButton() {
        // Start button's hide/show behaviour
        Long delay = this.reactionButton.GetDelay();

        this.reactionButton.changeVisibility("invisible");

        Handler timerHandler = new Handler();
        timerHandler.removeCallbacks(waitRunnable);
        timerHandler.postDelayed(waitRunnable, delay);
    }

    Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            showButton();
        }
    };

    public void showButton() {
        this.reactionButton.changeVisibility("visible");
        //Save when the button appeared to calculate reaction time
        this.reactionButton.setAppearTime(Calendar.getInstance().getTimeInMillis());
    }

    public void TimerButtonClicked(View view) {
        // Make sure button is visible when screen pressed
        TextView clickText = (TextView) findViewById(R.id.clickMessage);
        if (this.reactionButton.getVisibility().equals("invisible")) {
            clickText.setText("Clicked too soon!");
        }
        else {
            // When button is clicked, save the time for computing reaction time, then restart
            this.reactionButton.setClickTime(Calendar.getInstance().getTimeInMillis());
            Long reactionTime = this.reactionButton.getReactionTime();
            this.reactionTimes.add(reactionTime);
            saveReactionTime();
            clickText.setText("Reaction time: " + reactionTime.toString() + "ms");
        }

        // Text message should disapear after a second
        Handler textHandler = new Handler();
        textHandler.removeCallbacks(textRunnable);
        textHandler.postDelayed(textRunnable, 1000);

        ControlButton();
    }

    Runnable textRunnable = new Runnable() {
        @Override
        public void run() {
            TextView clickText = (TextView) findViewById(R.id.clickMessage);
            clickText.setText("");
        }
    };

    private void saveReactionTime() {
        // Save data to file
        try {
            // From lab
            FileOutputStream fos = openFileOutput(REACTION_FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.reactionTimes, output);
            output.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadReactionTime() {
        try {
            FileInputStream fis = openFileInput(REACTION_FILE_NAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arrayListType = new TypeToken<ArrayList<Long>>() {}.getType();
            this.reactionTimes = gson.fromJson(in, arrayListType);

        } catch (FileNotFoundException e) {
            reactionTimes = new ArrayList<Long>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

