package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

public class SettingsDialog {
    Window window;
    Texture backTxt;
    private BitmapFont font;



    public SettingsDialog(int size, Stage st){
        backTxt=new Texture("settingsPanel.png");
        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();
        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window("Settings",windowstyle);
        window.padTop(64);
        window.setPosition(st.getWidth()/2-window.getWidth()/2,st.getHeight()/2-window.getHeight()/2);
        window.setVisible(false);
        st.addActor(window);
    }

    public void addButton(Button button){
        this.window.add(button);
    }
}
