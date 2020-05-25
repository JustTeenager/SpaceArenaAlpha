package com.mygdx.game.Dialogs;

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
import com.mygdx.game.Core.MainGame;
import com.mygdx.game.Menu.Buttons;

import java.util.ArrayList;

//класс окна настроек в игре
public class SettingsDialog extends Actor {

    private float distance;
    private float distanceGeneral;
    private Window window;
    private Texture backTxt;
    private Texture soundTxt;

    private ArrayList<Buttons> bArray;

    public Texture volumeScale;
    public Texture volumeTxt;
    private BitmapFont font;
    private Stage st;
    public int xScale;
    public int yScale;

    public SettingsDialog(float size, Stage st){
        this.st=st;
        xScale=Gdx.graphics.getWidth()/3;
        yScale=Gdx.graphics.getHeight()-465;
        bArray=new ArrayList<>();

        backTxt=new Texture("settingsPanel.png");
        volumeTxt=new Texture("ButtonVol.png");
        volumeScale=new Texture("Shield Bar.png");
        soundTxt=new Texture("Sound Icon.png");

        distanceGeneral=volumeScale.getWidth();
        distance= MainGame.volume*distanceGeneral;

        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();
        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window(MainGame.GAME_SETTINGS,windowstyle);
        window.padTop(150);
        window.padLeft(325);
        window.setSize(backTxt.getWidth(),backTxt.getHeight());
        window.setPosition(350,100);
        setVisible(false);
        st.addActor(this);
    }

    public void addButton(Buttons button){
        button.setVisible(false);
        bArray.add(button);
        this.window.add(button);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        window.draw(batch, parentAlpha);
        if (MainGame.isSettingsDialogOpened) {
            batch.draw(volumeScale,xScale,yScale);
            batch.draw(soundTxt,xScale-soundTxt.getWidth()+15,yScale-soundTxt.getHeight()/4-15);
        }
    }
    public ArrayList<Buttons> getbArray() {
        return bArray;
    }

    public Stage getSt() {
        return st;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public float getDistanceGeneral() {
        return distanceGeneral;
    }
}
