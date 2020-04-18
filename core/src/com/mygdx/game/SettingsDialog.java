package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.ArrayList;
public class SettingsDialog extends Actor {

    private float distance;
    private float distanceGeneral;
    Window window;
    Texture backTxt;
    Texture soundTxt;

    private ArrayList<Buttons> bArray;

    Texture volumeScale;
    Texture volumeTxt;
    private BitmapFont font;
    private Stage st;
    int xScale;
    int yScale;

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
        distance=MainGame.volume*distanceGeneral;

        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();
        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window("Game Settings",windowstyle);
        window.padTop(150);
        window.padLeft(325);
        window.setSize(backTxt.getWidth(),backTxt.getHeight());
        window.setPosition(350,100);
        this.setVisible(false);
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

    public Window getWindow() {
        return window;
    }

    public Texture getBackTxt() {
        return backTxt;
    }

    public Texture getLine() {
        return volumeScale;
    }

    public BitmapFont getFont() {
        return font;
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

    public void setDistanceGeneral(float distanceGeneral) {
        this.distanceGeneral = distanceGeneral;
    }
}
