package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.Client.ClientClass;
import com.mygdx.game.Core.MainGame;

import static java.lang.StrictMath.abs;

//класс окна отсоединении одного из игроков
public class ConnectMenu implements Screen {
    private MainGame game;
    private SpriteBatch batch;

    private Stage st;
    private Buttons connectTxTButton;
    private Buttons backButton;
    private InputProcessor inputProcessor;

    private Texture backtxt;
    private Texture panel;

    private Sound clickSound;


    public ConnectMenu(final MainGame game){
        this.game=game;
        st=new Stage();
        batch=new SpriteBatch();

        backtxt=new Texture("menuBack.jpg");
        panel=new Texture("loadingPanel.png");

        connectTxTButton=new Buttons(Gdx.graphics.getWidth()/2.4f+35,Gdx.graphics.getHeight()/2+90,
                "waitingTXT",(ClientClass.isConnected() ? "Enemy disconnected" : "Unable to Connect"),1.15f,st);
        backButton=new Buttons(Gdx.graphics.getWidth()/2-35,Gdx.graphics.getHeight()/3,"backButt","Back",2f,st);

        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.mp3"));

        inputProcessor=new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if ((abs(Gdx.graphics.getHeight()-screenY)>backButton.btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<backButton.btn.getY()+backButton.btn.getHeight())
                        && (screenX>backButton.btn.getX()&&screenX<backButton.btn.getX()+backButton.btn.getWidth())){
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
        batch.draw(backtxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(panel,Gdx.graphics.getWidth()/2.4f-50,Gdx.graphics.getHeight()/2-panel.getHeight()-50,600,700);
        batch.end();
        st.act();
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
