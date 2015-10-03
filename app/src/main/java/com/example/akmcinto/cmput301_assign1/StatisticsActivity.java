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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

/*
    Activity for showing, clearing and emailing statistics
*/
public class StatisticsActivity extends AppCompatActivity {

    // Get reaction times singleton
    private ReactionTimes reactionTimes = ReactionTimes.getInstance();
    // Get buzzer winner singleton
    private BuzzerWinners buzzerWinners = BuzzerWinners.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        loadReactionTimes();
        loadBuzzerWinners();

        // Set up clear button
        Button clearButton = (Button) findViewById(R.id.clearAllStatsButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBuzzerWinners();
                clearReactionTimes();
            }
        });

        // Set up email button
        Button emailButton = (Button) findViewById(R.id.emailStatsButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailStats();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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

    // Load the reaction times from their save file and display
    private void loadReactionTimes() {
        reactionTimes.loadReactionTime(this.getBaseContext());

        HashMap<String, Long> timeStats = reactionTimes.timeStats();
        displayReactionTimes(timeStats);
    }

    // Load the buzzer winners from their save file and display
    private void loadBuzzerWinners() {
        buzzerWinners.loadBuzzerWinners(this.getBaseContext());

        HashMap<String, Integer> buzzerStats = buzzerWinners.buzzerStats();
        displayBuzzerWinners(buzzerStats);
    }

    // Clear the save file and display the blank stats
    private void clearBuzzerWinners() {
        this.buzzerWinners.clearData(this.getBaseContext());
        loadBuzzerWinners();
    }

    // Clear the save file and display the blank stats
    private void clearReactionTimes() {
        this.reactionTimes.clearData(this.getBaseContext());
        loadReactionTimes();
    }

    // Create email of the statistics
    private void emailStats() {
        String emailStr = getStatsEmail();
        // From:  https://developer.android.com/guide/components/intents-common.html, 2015-09-29
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, "");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "akmcinto-reflex Statistics");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailStr);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
    }

    // Put statistics from dictionary on the screen
    private void displayReactionTimes(HashMap<String, Long> timeStats) {
        TextView tableCell = (TextView) findViewById(R.id.fast10);
        tableCell.setText(timeStats.get("fast10").toString());
        tableCell = (TextView) findViewById(R.id.fast100);
        tableCell.setText(timeStats.get("fast100").toString());
        tableCell = (TextView) findViewById(R.id.fastAll);
        tableCell.setText(timeStats.get("fastAll").toString());

        tableCell = (TextView) findViewById(R.id.slow10);
        tableCell.setText(timeStats.get("slow10").toString());
        tableCell = (TextView) findViewById(R.id.slow100);
        tableCell.setText(timeStats.get("slow100").toString());
        tableCell = (TextView) findViewById(R.id.slowAll);
        tableCell.setText(timeStats.get("slowAll").toString());

        tableCell = (TextView) findViewById(R.id.mean10);
        tableCell.setText(timeStats.get("mean10").toString());
        tableCell = (TextView) findViewById(R.id.mean100);
        tableCell.setText(timeStats.get("mean100").toString());
        tableCell = (TextView) findViewById(R.id.meanAll);
        tableCell.setText(timeStats.get("meanAll").toString());

        tableCell = (TextView) findViewById(R.id.median10);
        tableCell.setText(timeStats.get("median10").toString());
        tableCell = (TextView) findViewById(R.id.median100);
        tableCell.setText(timeStats.get("median100").toString());
        tableCell = (TextView) findViewById(R.id.medianAll);
        tableCell.setText(timeStats.get("medianAll").toString());
    }

    // Put statistics from dictionary on the screen
    private void displayBuzzerWinners(HashMap<String, Integer> buzzerStats) {
        TextView tableCell = (TextView) findViewById(R.id.player1Mode2);
        tableCell.setText(buzzerStats.get("player1Mode2").toString());
        tableCell = (TextView) findViewById(R.id.player2Mode2);
        tableCell.setText(buzzerStats.get("player2Mode2").toString());

        tableCell = (TextView) findViewById(R.id.player1Mode3);
        tableCell.setText(buzzerStats.get("player1Mode3").toString());
        tableCell = (TextView) findViewById(R.id.player2Mode3);
        tableCell.setText(buzzerStats.get("player2Mode3").toString());
        tableCell = (TextView) findViewById(R.id.player3Mode3);
        tableCell.setText(buzzerStats.get("player3Mode3").toString());

        tableCell = (TextView) findViewById(R.id.player1Mode4);
        tableCell.setText(buzzerStats.get("player1Mode4").toString());
        tableCell = (TextView) findViewById(R.id.player2Mode4);
        tableCell.setText(buzzerStats.get("player2Mode4").toString());
        tableCell = (TextView) findViewById(R.id.player3Mode4);
        tableCell.setText(buzzerStats.get("player3Mode4").toString());
        tableCell = (TextView) findViewById(R.id.player4Mode4);
        tableCell.setText(buzzerStats.get("player4Mode4").toString());
    }

    // For the email:  create a formatted string of all the statistics to send
    private String getStatsEmail() {
        HashMap<String, Integer> buzzerStats = this.buzzerWinners.buzzerStats();
        HashMap<String, Long> timeStats = this.reactionTimes.timeStats();

        String email = "Reaction timer:  \n" +
                "Fastest (last 10):  " +  timeStats.get("fast10").toString() + "ms\n" +
                "Fastest (last 100):  " + timeStats.get("fast100").toString() + "ms\n" +
                "Fastest (all time):  " + timeStats.get("fastAll").toString() + "ms\n" +
                "Slowest (last 10):  " +  timeStats.get("slow10").toString() + "ms\n" +
                "Slowest (last 100):  " + timeStats.get("slow100").toString() + "ms\n" +
                "Slowest (all time):  " + timeStats.get("slowAll").toString() + "ms\n" +
                "Mean (last 10):  " +  timeStats.get("mean10").toString() + "ms\n" +
                "Mean (last 100):  " + timeStats.get("mean100").toString() + "ms\n" +
                "Mean (all time):  " + timeStats.get("meanAll").toString() + "ms\n" +
                "Median (last 10):  " +  timeStats.get("median10").toString() + "ms\n" +
                "Median (last 100):  " + timeStats.get("median100").toString() + "ms\n" +
                "Median (all time):  " + timeStats.get("medianAll").toString() + "ms\n" +
                "\n"+ "Buzzer winners:  \n" +
                "Player 1 wins (2-player):  " + buzzerStats.get("player1Mode2").toString() + "\n" +
                "Player 1 wins (3-player):  " + buzzerStats.get("player1Mode3").toString() + "\n" +
                "Player 1 wins (4-player):  " + buzzerStats.get("player1Mode4").toString() + "\n" +
                "Player 2 wins (2-player):  " + buzzerStats.get("player2Mode2").toString() + "\n" +
                "Player 2 wins (3-player):  " + buzzerStats.get("player2Mode3").toString() + "\n" +
                "Player 2 wins (4-player):  " + buzzerStats.get("player2Mode4").toString() + "\n" +
                "Player 3 wins (3-player):  " + buzzerStats.get("player3Mode3").toString() + "\n" +
                "Player 3 wins (4-player):  " + buzzerStats.get("player3Mode4").toString() + "\n" +
                "Player 4 wins (4-player):  " + buzzerStats.get("player4Mode4").toString();
        return email;
    }
}
