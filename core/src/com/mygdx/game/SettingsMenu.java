package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static java.lang.StrictMath.abs;

public class SettingsMenu implements Screen {
    private MainGame game;
    SpriteBatch batch;
    Texture backTxt;
    Texture volumeTxt;
    Texture volumeScale;
    Texture soundTxt;
    Texture panel;
    Buttons volumeButton;
    Buttons backButton;
    Stage st;
    InputProcessor inputProcessor;
    Sound clickSound;
    BitmapFont nameFont;
    static float distance;
    static float distanceGeneral;
    int xScale;
    int yScale;

    public SettingsMenu(final MainGame game){
        this.game=game;

        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));

        batch=new SpriteBatch();
        st=new Stage();

        nameFont=new BitmapFont(Gdx.files.internal("liter.fnt"));
        nameFont.getData().setScale(2.7f);
        nameFont.setColor(new Color(0,0,0,0.55f));

        backTxt=new Texture("menuBack.jpg");
        volumeTxt=new Texture("ButtonVol.png");
        volumeScale=new Texture("Shield Bar.png");
        soundTxt=new Texture("Sound Icon.png");
        panel=new Texture("settingsPanel.png");

        xScale=Gdx.graphics.getWidth()/3;
        yScale=Gdx.graphics.getHeight()-465;

        volumeButton=new Buttons(xScale+volumeScale.getWidth()-volumeTxt.getWidth(),yScale-volumeTxt.getHeight()/4,
                "volButt",volumeTxt.getWidth(),volumeTxt.getHeight(),st,volumeTxt,volumeTxt);

        backButton=new Buttons(Gdx.graphics.getWidth()/2-120,yScale/3,"backButt","Back",2f,st);

        distanceGeneral=volumeScale.getWidth();
        distance=distanceGeneral;

        inputProcessor=new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) { return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if ((abs(Gdx.graphics.getHeight()-screenY)>backButton.btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<backButton.btn.getY()+backButton.btn.getHeight())
                        && (screenX>backButton.btn.getX()&&screenX<backButton.btn.getX()+backButton.btn.getWidth())){
                    MainGame.volume=distance/distanceGeneral;
                    clickSound.play(MainGame.volume);
                    game.setScreen(new MainMenu(game));
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
            if (abs(Gdx.graphics.getHeight()-screenY)>=volumeButton.btn.getY() && abs(Gdx.graphics.getHeight()-screenY)<=volumeButton.btn.getY()+volumeButton.btn.getHeight()) {
                Vector2 previous = new Vector2(volumeButton.btn.getX(), volumeButton.btn.getY());
                volumeButton.btn.setX(screenX);
                if (volumeButton.btn.getX() <= xScale || volumeButton.btn.getX() >= (xScale + volumeScale.getWidth() - volumeTxt.getWidth()))
                    volumeButton.btn.setPosition(previous.x, previous.y);

                if (screenX >= xScale && screenX <= xScale + volumeScale.getWidth())
                    distance = screenX - xScale;
                if (screenX <= xScale) {
                    distance = 0;
                }
                if (screenX >= xScale + volumeScale.getWidth() - volumeTxt.getWidth()) {
                    distance = distanceGeneral;
                }
            }
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(int amount) {
                return false;
            }
        };
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(inputProcessor);

        batch.begin();
        batch.draw(backTxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(panel,350,100);
        nameFont.draw(batch,"Game Settings",Gdx.graphics.getWidth()/2-340,Gdx.graphics.getHeight()-180);
        batch.draw(volumeScale,xScale,yScale);
        batch.draw(soundTxt,xScale-soundTxt.getWidth()+15,yScale-soundTxt.getHeight()/4-15);
        batch.end();

        st.act(delta);
        st.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
