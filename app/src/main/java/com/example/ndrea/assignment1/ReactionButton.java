package com.example.ndrea.assignment1;

import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrea McIntosh on 17/09/2015.
 */
public class ReactionButton {

    Button button;

    public ReactionButton(Button button) {
        this.button = button;
    }

    public Long GetDelay() {
        Double delay = Math.random();
        // Convert delay to be up to 2000ms
        delay = delay * 2000;
        // Make sure delay is at least 10ms
        if (delay < 10) {
            delay = Math.random();
            delay = delay * 2000;
        }
        // For .wait() function, must return long
        return delay.longValue();
    }

}
