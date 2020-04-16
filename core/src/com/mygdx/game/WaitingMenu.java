package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class WaitingMenu extends ScreenAdapter {
    private MainGame game;
    private Stage st;
    SpriteBatch batch;
    Buttons txtbutton;
    Buttons waitButton;
    Texture backtxt;
    Texture replaytxt;
    public WaitingMenu(final MainGame game){
        this.game=game;
        batch=new SpriteBatch();
        st=new Stage();

        backtxt=new Texture("menuBack.jpg");
        replaytxt=new Texture("Replay Icon.png");

        txtbutton=new Buttons(Gdx.graphics.getWidth()/2.4f,Gdx.graphics.getHeight()/2,
                "waitingTXT","wait for players",1.15f,st);
        //waitButton=new Buttons(Gdx.graphics.getWidth()/2.4f,Gdx.graphics.getHeight()/2-400,"waitButton",replaytxt.getWidth(),replaytxt.getHeight(),st,replaytxt,replaytxt);
        //waitButton=new Sprite(replaytxt);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(backtxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.end();

        st.draw();

        ClientClass.sendPlayersWaitingBox(new PlayersWaitingBox());
        if (MainGame.playersNum==2){
            game.setScreen(new ArenaGame());
        }
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
