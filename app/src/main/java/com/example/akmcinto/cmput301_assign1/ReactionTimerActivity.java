package com.example.akmcinto.cmput301_assign1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

public class ReactionTimerActivity extends AppCompatActivity {

    ReactionButton reactionButton;
    private ReactionTimes reactionTimes = ReactionTimes.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_timer);

        Button button = (Button) findViewById(R.id.reactionButton);
        reactionButton = new ReactionButton(button);

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
            this.reactionTimes.addReactionTime(reactionTime, this.getBaseContext());
            clickText.setText("Reaction time: " + reactionTime.toString() + "ms");
        }

        // Text message should disappear after a second
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

}

