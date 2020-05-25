package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.Core.MainGame;

//класс считывания имени при смене
public class NameInput implements Input.TextInputListener {
    private String text;
    private InputProcessor inputProcessor;

    public NameInput(InputProcessor processor){
        this.inputProcessor=processor;
        Gdx.input.setInputProcessor(inputProcessor);
    }

    @Override
    public void input (String text) {
        this.text=text;
        if (text.length()>10) text.substring(0,10);
        MainGame.current_player_name=text;
    }
    @Override
    public void canceled () {
    }
}
