package com.mygdx.game;

import com.badlogic.gdx.Input;

public class NameInput implements Input.TextInputListener {
    @Override
    public void input (String text) {
        MainGame.current_player_name=text;
        System.out.println(text);
        System.out.println(MainGame.current_player_name);
    }

    @Override
    public void canceled () {
    }
}
