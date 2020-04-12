package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class WaitingMenu extends ScreenAdapter {
    private MainGame game;
    private Stage st;
    SpriteBatch batch;
    Buttons txtbutton;
    Texture backtxt;
    public WaitingMenu(final MainGame game) throws Exception {
        this.game=game;
        batch=new SpriteBatch();
        backtxt=new Texture("menuBack.jpg");
        st=new Stage();
        txtbutton=new Buttons(Gdx.graphics.getWidth()/2.4f,Gdx.graphics.getHeight()/2,
                "waitingTXT","wait for players",1.15f,st);

        st.addActor(txtbutton);
        ClientClass.startClient();
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

        //st.act();
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
