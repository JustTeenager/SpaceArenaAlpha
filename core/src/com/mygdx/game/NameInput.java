package com.mygdx.game;

import com.badlogic.gdx.Input;

public class NameInput implements Input.TextInputListener {
    @Override
    public void input (String text) {
        MainGame.playerName=text;
    }

    @Override
    public void canceled () {
    }
}
