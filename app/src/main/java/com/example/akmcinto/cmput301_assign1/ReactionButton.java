package com.example.akmcinto.cmput301_assign1;

import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Andrea McIntosh on 17/09/2015.
 */
public class ReactionButton {

    protected Button button;
    private Long appearTime;
    private Long clickTime;
    private String visibility;

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

    public Long getAppearTime() {
        return appearTime;
    }

    public void setAppearTime(Long appearTime) {
        this.appearTime = appearTime;
    }

    public Long getClickTime() {
        return clickTime;
    }

    public void setClickTime(Long clickTime) {
        this.clickTime = clickTime;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public void changeVisibility(String visibility) {
        if (visibility.equals("visible")) {
            this.button.setVisibility(View.VISIBLE);
        }
        else if(visibility.equals("invisible")) {
            this.button.setVisibility(View.INVISIBLE);
        }
        setVisibility(visibility);
    }

    public Long getReactionTime() {
        Long reactionTime = this.getClickTime() - this.getAppearTime();
        return reactionTime;
    }
}
