/*
   Copyright 2015 Andrea McIntosh

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.example.akmcinto.cmput301_assign1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;

/*
    Activity for running the reaction timer activity.  Shows/hides reaction timer button.  Displays
    instructions, reaction time.  Records when button was shown and clicked to find the reaction time.
*/
public class ReactionTimerActivity extends AppCompatActivity {

    private ReactionButton reactionButton;
    private ReactionTimes reactionTimes = ReactionTimes.getInstance();
    private AlertDialog alertDialog;
    private Handler timerHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reaction_timer);

        Button button = (Button) findViewById(R.id.reactionButton);
        reactionButton = new ReactionButton(button);

        // Display instructions dialog
        displayInstructions();
    }

    // Show a popup with instructions for the game.  After it is dismissed start the game.
    private void displayInstructions() {
        this.alertDialog = new AlertDialog.Builder(ReactionTimerActivity.this).create();
        alertDialog.setMessage("Click button as quickly as possible after it appears.  " +
                "Press back button to exit.");
        alertDialog.setTitle("Reaction Timer Instructions");

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Okay", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                controlButton();
            }
        });

        alertDialog.show();
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

    // Hides the button then starts a handler to show it after a delay.
    private void controlButton() {
        // Start button's hide/show behaviour
        Long delay = this.reactionButton.getDelay();

        this.reactionButton.changeVisibility("invisible");

        timerHandler.removeCallbacks(waitRunnable);
        timerHandler.postDelayed(waitRunnable, delay);
    }

    // Runnable to show the button after a delay has elapsed
    Runnable waitRunnable = new Runnable() {
        @Override
        public void run() {
            showButton();
        }
    };

    // Show the button after delay has elapsed.  Save the time it appeared to later calculate delay.
    private void showButton() {
        this.reactionButton.changeVisibility("visible");
        //Save when the button appeared to calculate reaction time
        this.reactionButton.setAppearTime(Calendar.getInstance().getTimeInMillis());
    }

    // Click method for the timer button
    public void timerButtonClicked(View view) {
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

        controlButton();
    }

    // Separate runnable to show text about when the button was pressed for only a short amount of time
    Runnable textRunnable = new Runnable() {
        @Override
        public void run() {
            TextView clickText = (TextView) findViewById(R.id.clickMessage);
            clickText.setText("");
        }
    };

}

