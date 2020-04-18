package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.awt.Dialog;
import java.awt.Frame;

import javax.xml.soap.Text;

public class SettingsDialog extends Actor {
    Window window;
    Texture backTxt;

    Texture line;
    private BitmapFont font;
    private Stage st;

    public SettingsDialog(float x, float y, float size, Stage st){
        this.st=st;
        backTxt=new Texture("settingsPanel.png");
        line=new Texture("Shield Bar.png");
        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();
        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window("Settings",windowstyle);
        window.padTop(64);
        window.setSize(600,400);
        window.setPosition(x,y);
        //window.setVisible(false);
        this.setVisible(false);
        st.addActor(this);
    }

    public void addButton(Buttons button){
        this.window.add(button);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        window.draw(batch, parentAlpha);
        //batch.draw(line,st.getWidth()/2-window.getWidth()/2,st.getHeight()/2-window.getHeight()/2);
    }

    public Window getWindow() {
        return window;
    }

    public Texture getBackTxt() {
        return backTxt;
    }

    public Texture getLine() {
        return line;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Stage getSt() {
        return st;
    }
}
