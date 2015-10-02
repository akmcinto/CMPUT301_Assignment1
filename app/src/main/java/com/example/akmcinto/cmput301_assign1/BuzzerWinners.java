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
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

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

    public void saveBuzzerWinners(Context context) {
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

    public void loadBuzzerWinners(Context context) {
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

    public void clearData() {
        this.buzzerWinners.clear();
    }

    public HashMap<String, Integer> buzzerStats() {
        Integer player1Mode2Wins = 0;
        Integer player2Mode2Wins = 0;
        ArrayList<Integer> mode2 = this.buzzerWinners.get(2);
        if (mode2 == null) {
            mode2 = new ArrayList<Integer>();
        }
        for (Integer winner : mode2) {
            if (winner.equals(1)) { player1Mode2Wins += 1; }
            else if (winner.equals(2)) { player2Mode2Wins += 1; }
        }

        Integer player1Mode3Wins = 0;
        Integer player2Mode3Wins = 0;
        Integer player3Mode3Wins = 0;
        ArrayList<Integer> mode3 = this.buzzerWinners.get(3);
        if (mode3 == null) {
            mode3 = new ArrayList<Integer>();
        }
        for (Integer winner : mode3) {
            if (winner.equals(1)) { player1Mode3Wins += 1; }
            else if (winner.equals(2)) { player2Mode3Wins += 1; }
            else if (winner.equals(3)) { player3Mode3Wins += 1; }
        }

        Integer player1Mode4Wins = 0;
        Integer player2Mode4Wins = 0;
        Integer player3Mode4Wins = 0;
        Integer player4Mode4Wins = 0;
        ArrayList<Integer> mode4 = this.buzzerWinners.get(4);
        if (mode4 == null) {
            mode4 = new ArrayList<Integer>();
        }
        for (Integer winner : mode4) {
            if (winner.equals(1)) { player1Mode4Wins += 1; }
            else if (winner.equals(2)) { player2Mode4Wins += 1; }
            else if (winner.equals(3)) { player3Mode4Wins += 1; }
            else if (winner.equals(4)) { player4Mode4Wins += 1; }
        }

        // Save values to a dictionary
        HashMap<String, Integer> buzzerStats = new HashMap<String, Integer>();

        buzzerStats.put("player1Mode2", player1Mode2Wins);
        buzzerStats.put("player1Mode3", player1Mode3Wins);
        buzzerStats.put("player1Mode4", player1Mode4Wins);
        buzzerStats.put("player2Mode2", player2Mode2Wins);
        buzzerStats.put("player2Mode3", player2Mode3Wins);
        buzzerStats.put("player2Mode4", player2Mode4Wins);
        buzzerStats.put("player3Mode3", player3Mode3Wins);
        buzzerStats.put("player3Mode4", player3Mode4Wins);
        buzzerStats.put("player4Mode4", player4Mode4Wins);

        return buzzerStats;
    }

}
