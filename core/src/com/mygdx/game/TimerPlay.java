package com.mygdx.game;

import java.util.Locale;
import java.util.TimerTask;


public class TimerPlay extends TimerTask {

    private int seconds=300;
    public static String time;
    @Override
    public void run() {
        seconds--;
        int minute = (seconds % 3600) / 60;
        int sec = seconds % 60;
        String time = String.format(Locale.getDefault(), "%2d:%02d", minute, sec);
        System.out.println(time);
    }
}
