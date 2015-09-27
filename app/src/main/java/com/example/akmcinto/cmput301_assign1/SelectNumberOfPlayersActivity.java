package com.example.akmcinto.cmput301_assign1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SelectNumberOfPlayersActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_number_of_players);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_number_of_players, menu);
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

    public void select2PlayerBuzzer(View view) {
        Intent intent = new Intent(SelectNumberOfPlayersActivity.this, Player2BuzzerActivity.class);
        startActivity(intent);
    }

    public void select3PlayerBuzzer(View view) {
        Intent intent = new Intent(SelectNumberOfPlayersActivity.this, Player3BuzzerActivity.class);
        startActivity(intent);
    }

    public void select4PlayerBuzzer(View view) {
        Intent intent = new Intent(SelectNumberOfPlayersActivity.this, Player4BuzzerActivity.class);
        startActivity(intent);
    }

}
