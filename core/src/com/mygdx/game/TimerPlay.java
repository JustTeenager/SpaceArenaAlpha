package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.Locale;
import java.util.TimerTask;


public class TimerPlay extends TimerTask {
    private int seconds=300;
    @Override
    public void run() {
        seconds--;
        int minute = (seconds % 3600) / 60;
        int sec = seconds % 60;
        MainGame.time = String.format(Locale.getDefault(), "%2d %02d", minute, sec);
        System.out.println(MainGame.time);
    }
}
