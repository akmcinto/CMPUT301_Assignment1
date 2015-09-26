package com.example.akmcinto.cmput301_assign1;

/**
 * Created by Andrea McIntosh on 26/09/2015.
 */

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
import java.util.Map;
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

    private void saveReactionTime(Context context) {
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



}
