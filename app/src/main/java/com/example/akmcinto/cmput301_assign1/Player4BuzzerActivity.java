package com.example.akmcinto.cmput301_assign1;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class Player4BuzzerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player4_buzzer);

        // Set player buttons
        new BuzzerButton((Button) findViewById(R.id.player1Button4), 1, 4, Player4BuzzerActivity.this);
        new BuzzerButton((Button) findViewById(R.id.player2Button4), 2, 4, Player4BuzzerActivity.this);
        new BuzzerButton((Button) findViewById(R.id.player3Button4), 3, 4, Player4BuzzerActivity.this);
        new BuzzerButton((Button) findViewById(R.id.player4Button4), 4, 4, Player4BuzzerActivity.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player4_buzzer, menu);
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
}
