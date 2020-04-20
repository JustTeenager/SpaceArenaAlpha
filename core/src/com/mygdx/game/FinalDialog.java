package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class FinalDialog extends Actor {
    private Window window;
    private Texture backTxt;
    private BitmapFont font;
    private Stage st;
    Buttons backButton;
    String winner;


    public FinalDialog(float size, Stage st){
        this.st=st;

        backTxt=new Texture("settingsPanel.png");
        winner=(MainGame.current_player_score>MainGame.enemy_score ? MainGame.current_player_name+" was victorious!" : MainGame.enemy_name+" was victorious!");

        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();
        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window(MainGame.GAME_OVER,windowstyle);
        window.padTop(150);
        window.padLeft(325);
        window.setSize(backTxt.getWidth(),backTxt.getHeight());
        window.setPosition(350,100);
        setVisible(false);
        st.addActor(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setVisible(true);
        try {
            backButton.setVisible(true);
        }catch (Exception e){}
        if (MainGame.seconds<=0){
            window.draw(batch,parentAlpha);
            font.draw(batch,winner,st.getWidth()/2,st.getHeight()/2);
            font.draw(batch,MainGame.current_player_name+" score is: "+MainGame.current_player_score,st.getWidth()/2-150,st.getHeight()/2-150);
            font.draw(batch,MainGame.enemy_name+" score is: "+MainGame.enemy_score,st.getWidth()/2-150,st.getHeight()/2-400);
        }
    }

    public void addButton(Buttons button){
        button.setVisible(false);
        this.window.add(button);
        backButton=button;
    }
}
