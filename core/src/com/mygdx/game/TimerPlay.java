package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

import java.util.Locale;
import java.util.TimerTask;


public class TimerPlay extends TimerTask {

    @Override
    public void run() {
        if (MainGame.seconds>0) {
            MainGame.seconds--;
            int minute = (MainGame.seconds % 3600) / 60;
            int sec = MainGame.seconds % 60;
            MainGame.time = String.format(Locale.getDefault(), "%2d %02d", minute, sec);
            //System.out.println(MainGame.time);
            System.out.println(MainGame.seconds);
        }
    }
}
