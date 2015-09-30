package com.example.akmcinto.cmput301_assign1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

        Button clearButton = (Button) findViewById(R.id.clearAllStatsButton);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearBuzzerWinners();
                clearReactionTimes();
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

    private void loadReactionTimes() {
        reactionTimes.loadReactionTime(this.getBaseContext());

        HashMap<String, Long> timeStats = reactionTimes.timeStats();
        displayReactionTimes(timeStats);
    }

    private void loadBuzzerWinners() {
        buzzerWinners.loadBuzzerWinners(this.getBaseContext());

        HashMap<String, Integer> buzzerStats = buzzerWinners.buzzerStats();
        displayBuzzerWinners(buzzerStats);
    }

    private void clearBuzzerWinners() {
        this.buzzerWinners.clearData();
        this.buzzerWinners.saveBuzzerWinners(this.getBaseContext());
        loadBuzzerWinners();
    }

    private void clearReactionTimes() {
        this.reactionTimes.clearData();
        this.reactionTimes.saveReactionTime(this.getBaseContext());
        loadReactionTimes();
    }

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

}
