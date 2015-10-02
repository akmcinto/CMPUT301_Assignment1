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

// Gson code from https://github.com/google/gson
// Instructions on how to add: lepoetemaudit, http://stackoverflow.com/questions/16608135/android-studio-add-jar-as-library, 2015-09-25
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* Class for handling saving and loading reaction times */
public class ReactionTimes {

    // Create the filenames for persistent app data
    private String REACTION_FILE_NAME = "reactionTimerDataFile";
    private ArrayList<Long> reactionTimes = new ArrayList<Long>();
    private static ReactionTimes ourInstance = new ReactionTimes();


    public static ReactionTimes getInstance() {
        return ourInstance;
    }

    private ReactionTimes() {
    }

    public void addReactionTime(Long time, Context context) {
        reactionTimes.add(time);
        saveReactionTime(context);
    }

    public void saveReactionTime(Context context) {
        // Save data to file
        try {
            // http://stackoverflow.com/questions/3625837/android-what-is-wrong-with-openfileoutput, naikus, 2015-09-26
            // TODO: From lab
            FileOutputStream fos = context.openFileOutput(REACTION_FILE_NAME, Context.MODE_PRIVATE);
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

    public void loadReactionTime(Context context) {
        try {
            FileInputStream fis = context.openFileInput(REACTION_FILE_NAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();
            // https://google-gson.googlecode.com/svn/trunk/gson/docs/javadocs/com/google/gson/Gson.html, 2015-09-23
            Type arrayListType = new TypeToken<ArrayList<Long>>() {}.getType();
            this.reactionTimes = gson.fromJson(in, arrayListType);

        } catch (FileNotFoundException e) {
            this.reactionTimes = new ArrayList<Long>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void clearData() {
        this.reactionTimes.clear();
    }

    /*
    Return dictionary containing all relevant stat values - key is string describing what stat
    value is being recorded.
    */
    public HashMap<String, Long> timeStats() {
        // Save all times to a new variable to sort it (for median) without destroying ordering in file
        List<Long> allTimes = (List<Long>) this.reactionTimes.clone();
        if (allTimes.isEmpty()) { allTimes.add(Long.valueOf(0)); }
        int end = allTimes.size();

        int last10end = end - 10;
        if (last10end < 0) { last10end = 0; }
        int last100end = end - 100;
        if (last100end < 0) { last100end = 0; }
        List<Long> last10Times = allTimes.subList(last10end, end);
        List<Long> last100Times = allTimes.subList(last100end, end);

        // Sum arrays together to calculate mean
        Long sum10 = Long.valueOf(0);
        for (Long num : last10Times) {
            sum10 += num;
        }
        Long sum100 = Long.valueOf(0);
        for (Long num : last100Times) {
            sum100 += num;
        }
        Long sumAll = Long.valueOf(0);
        for (Long num : allTimes) {
            sumAll += num;
        }

        // Save values to a dictionary
        HashMap<String, Long> timeStats = new HashMap<String, Long>();

        // http://stackoverflow.com/questions/8304767/how-to-get-maximum-value-from-the-list-arraylist, gotomanners, 2015-09-26
        timeStats.put("fast10", Collections.min(last10Times));
        timeStats.put("slow10", Collections.max(last10Times));
        timeStats.put("fast100", Collections.min(last100Times));
        timeStats.put("slow100", Collections.max(last100Times));
        timeStats.put("fastAll", Collections.min(allTimes));
        timeStats.put("slowAll", Collections.max(allTimes));
        timeStats.put("mean10", sum10 / last10Times.size());
        timeStats.put("mean100", sum100 / last100Times.size());
        timeStats.put("meanAll", sumAll / end);
        // Sort lists to find median
        Collections.sort(last10Times);
        timeStats.put("median10", last10Times.get(Math.round(last10Times.size() / 2)));
        Collections.sort(last100Times);
        timeStats.put("median100", last100Times.get(Math.round(last100Times.size() / 2)));
        Collections.sort(allTimes);
        timeStats.put("medianAll", allTimes.get(Math.round(end/2)));

        return timeStats;
    }

}
