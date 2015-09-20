package com.example.ndrea.assignment1;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ReactionTimerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_timer);

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

    public void ControlButton(View view) {
        Button button = (Button) findViewById(R.id.reactionButton);
        ReactionButton reactionButton = new ReactionButton(button);
        // Start button's hide/show behaviour
        Long delay = reactionButton.GetDelay();

        button.setVisibility(View.INVISIBLE);

        Handler timerHandler = new Handler();
        timerHandler.removeCallbacks(waitRunnable);
        timerHandler.postDelayed(waitRunnable, delay);
    }

    Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            Button button = (Button) findViewById(R.id.reactionButton);
            button.setVisibility(View.VISIBLE);
        }
    };

}
