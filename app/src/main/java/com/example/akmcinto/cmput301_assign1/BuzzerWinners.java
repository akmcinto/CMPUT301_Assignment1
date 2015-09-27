package com.example.akmcinto.cmput301_assign1;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Andrea McIntosh on 26/09/2015.
 */
public class BuzzerWinners {

    // Create the filenames for persistent app data
    private String BUZZER_FILE_NAME = "buzzerDataFile";
    private HashMap<Integer, ArrayList<Integer>> buzzerWinners = new HashMap<Integer, ArrayList<Integer>>();
    private static BuzzerWinners ourInstance = new BuzzerWinners();

    public static BuzzerWinners getInstance() {
        return ourInstance;
    }

    private BuzzerWinners() {
    }

    public void addBuzzerWinner(Integer numPlayers, Integer winner, Context context) {
        ArrayList<Integer> winnersList = this.buzzerWinners.get(numPlayers);
        if (winnersList == null) {
            winnersList = new ArrayList<Integer>();
        }
        winnersList.add(winner);
        this.buzzerWinners.put(numPlayers, winnersList);
        saveBuzzerWinners(context);
    }

    private void saveBuzzerWinners(Context context) {
        // Save data to file
        try {
            // http://stackoverflow.com/questions/3625837/android-what-is-wrong-with-openfileoutput, naikus, 2015-09-26
            // TODO: From lab
            FileOutputStream fos = context.openFileOutput(BUZZER_FILE_NAME, Context.MODE_PRIVATE);
            BufferedWriter output = new BufferedWriter(new OutputStreamWriter(fos));
            Gson gson = new Gson();
            gson.toJson(this.buzzerWinners, output);
            output.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadBuzzerTimes(Context context) {
        try {
            FileInputStream fis = context.openFileInput(BUZZER_FILE_NAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type hasMapType = new TypeToken<HashMap<Integer, ArrayList<Integer>>>() {}.getType();
            this.buzzerWinners = gson.fromJson(in, hasMapType);

        } catch (FileNotFoundException e) {
            this.buzzerWinners = new HashMap<Integer, ArrayList<Integer>>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
