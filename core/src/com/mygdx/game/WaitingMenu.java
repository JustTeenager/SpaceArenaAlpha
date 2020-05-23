package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.Align;

public class WaitingMenu extends ScreenAdapter {
    private MainGame game;
    private Stage st;
    private SpriteBatch batch;
    private Buttons txtbutton;
    private Buttons waitButton;
    private Texture backtxt;
    private Texture replaytxt;
    private Texture panel;
    private InputProcessor inputProcessor;
    public WaitingMenu(final MainGame game){
        this.game=game;
        batch=new SpriteBatch();
        st=new Stage();

        backtxt=new Texture("menuBack.jpg");
        panel=new Texture("loadingPanel.png");
        replaytxt=new Texture("Replay Icon.png");

        txtbutton=new Buttons(Gdx.graphics.getWidth()/2.3f+15,Gdx.graphics.getHeight()/2+90,
                "waitingTXT","wait for players",1.15f,st);

        waitButton=new Buttons(Gdx.graphics.getWidth()/2.2f+60,Gdx.graphics.getHeight()/2-220,"circleTxt",replaytxt.getWidth(),replaytxt.getHeight(),st,replaytxt,replaytxt);
        waitButton.btn.setTransform(true);
        waitButton.btn.setOrigin(Align.center);
        waitButton.btn.addAction(Actions.repeat(RepeatAction.FOREVER,
                Actions.sequence(
                        Actions.rotateTo(360),
                        Actions.rotateTo(0,3.5f))));

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
                return false;
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
        if (!ClientClass.isConnected()){
            game.setScreen(new ConnectMenu(game));
        }

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(inputProcessor);

        batch.begin();
        batch.draw(backtxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(panel,Gdx.graphics.getWidth()/2.4f-50,Gdx.graphics.getHeight()/2-panel.getHeight()-50,600,700);
        batch.end();
        waitButton.btn.act(delta);
        st.draw();

        ClientClass.sendPlayersWaitingBox(new PlayersWaitingBox());

        if (MainGame.playersNum==2){
            game.setScreen(new ArenaGame(game));
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
