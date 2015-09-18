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

    public void WaitToAppear() {
        // For delaying, record current time
        Calendar timeCal = Calendar.getInstance();
        Long startTime = timeCal.getTimeInMillis();

        // Make sure button is not visible
        this.button.setVisibility(View.INVISIBLE);

        // Get delay
        Long delay = GetDelay();

        // Get button to wait for delay time, then appear
        // TODO: Get Delay
        while (Calendar.getInstance().getTimeInMillis() < (startTime + delay)) {
            // Make sure button is not visible
            this.button.setVisibility(View.INVISIBLE);
            continue;
        }
        this.button.setVisibility(View.VISIBLE);
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
