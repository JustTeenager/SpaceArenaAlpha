package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import java.util.HashMap;
import java.util.Map;

import sun.applet.Main;

import static java.lang.StrictMath.abs;

public class MainMenu implements Screen {

    private MainGame game;

    SpriteBatch batch;
    private Stage s;

    private Texture backtxt;
    private Texture panel;
    private Texture leadTexture;

    private Buttons[] butt;
    private Buttons leadButton;
    private String[] buttTexts;
    private float setupX;

    private InputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;

    private Sound clickSound;

    private AuthorizationDialog autoDialog;

    private BitmapFont nameFont;
    private boolean isTouchOnSettings=false;

    public MainMenu(final MainGame game){
        this.game=game;

        MainGame.playersNum=0;
        MainGame.setPlayerIdentify(-1);

        nameFont=new BitmapFont(Gdx.files.internal("liter.fnt"));
        nameFont.getData().setScale(2.8f);
        nameFont.setColor(new Color(0,0,0,0.55f));


        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));
        batch = new SpriteBatch();
        s=new Stage();

        leadTexture=new Texture("leaderIcon.png");
        backtxt=new Texture("menuBack.jpg");
        setupX=0;
        panel=new Texture("mainPanel.png");
        buttTexts= new String[] {"Start Game", "Settings"};

        inputMultiplexer=new InputMultiplexer();
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
                if (autoDialog.isVisible()) {
                    if ((abs(Gdx.graphics.getHeight() - screenY) > autoDialog.getLogInButton().btn.getY() && abs(Gdx.graphics.getHeight() - screenY) < autoDialog.getLogInButton().btn.getY() + autoDialog.getLogInButton().btn.getHeight())
                            && (screenX > autoDialog.getLogInButton().btn.getX() && screenX < autoDialog.getLogInButton().btn.getX() + autoDialog.getLogInButton().btn.getWidth()) && (autoDialog.getLogInButton().isTouchable())) {
                        System.out.println("FB LOGIN");
                        clickSound.play(MainGame.volume);
                        MainGame.playerLogin = autoDialog.getEmailField().getText();
                        MainGame.playerPassword = autoDialog.getPasswordField().getText();
                        try {
                                FireBaseClass.signIn(MainGame.playerLogin, MainGame.playerPassword.toCharArray(), autoDialog,FireBaseClass.getuID());
                        }catch (IllegalArgumentException e){
                            autoDialog.setErrorText("empty pass or email");
                            autoDialog.getRegisterButton().setTouchable(Touchable.enabled);
                            autoDialog.getLogInButton().setTouchable(Touchable.enabled);
                        }
                    } else if ((abs(Gdx.graphics.getHeight() - screenY) > autoDialog.getRegisterButton().btn.getY() && abs(Gdx.graphics.getHeight() - screenY) < autoDialog.getRegisterButton().btn.getY() + autoDialog.getRegisterButton().btn.getHeight())
                            && (screenX > autoDialog.getRegisterButton().btn.getX() && screenX < autoDialog.getRegisterButton().btn.getX() + autoDialog.getRegisterButton().btn.getWidth()) && autoDialog.getRegisterButton().isTouchable()) {
                        System.out.println("FB REGISTER");
                        clickSound.play(MainGame.volume);
                        MainGame.playerLogin = autoDialog.getEmailField().getText();
                        MainGame.playerPassword = autoDialog.getPasswordField().getText();
                        try {
                            FireBaseClass.register(MainGame.playerLogin, MainGame.playerPassword.toCharArray(), autoDialog);
                        }catch (IllegalArgumentException e){
                            autoDialog.setErrorText("empty pass or email");
                            autoDialog.getRegisterButton().setTouchable(Touchable.enabled);
                            autoDialog.getLogInButton().setTouchable(Touchable.enabled);
                        }
                    }
                }


                else {

                    if ((abs(Gdx.graphics.getHeight() - screenY) > leadButton.btn.getY() && abs(Gdx.graphics.getHeight() - screenY) < leadButton.btn.getY() + leadButton.btn.getHeight())
                            && (screenX > leadButton.btn.getX() && screenX < leadButton.btn.getX() + leadButton.btn.getWidth()) && leadButton.isTouchable()) {
                        leadButton.setTouchable(Touchable.disabled);
                        for (Buttons butts:butt){
                            butts.setTouchable(Touchable.disabled);
                        }
                        FireBaseClass.actionListener();
                        isTouchOnSettings=true;
                        clickSound.play(MainGame.volume);
                    }


                    else if ((abs(Gdx.graphics.getHeight() - screenY) > butt[0].btn.getY() && abs(Gdx.graphics.getHeight() - screenY) < butt[0].btn.getY() + butt[0].btn.getHeight())
                            && (screenX > butt[0].btn.getX() && screenX < butt[0].btn.getX() + butt[0].btn.getWidth()) && butt[0].isTouchable()) {
                        clickSound.play(MainGame.volume);
                        try {
                            ClientClass.startClient();
                            game.setScreen(new WaitingMenu(game));
                        } catch (Exception e) {
                            e.printStackTrace();
                            game.setScreen(new ConnectMenu(game));
                        }
                    } else if ((abs(Gdx.graphics.getHeight() - screenY) > butt[1].btn.getY() && abs(Gdx.graphics.getHeight() - screenY) < butt[1].btn.getY() + butt[1].btn.getHeight()
                            && (screenX > butt[1].btn.getX() && screenX < butt[1].btn.getX() + butt[1].btn.getWidth())) && butt[1].isTouchable()) {
                        clickSound.play(MainGame.volume);
                        game.setScreen(new SettingsMenu(game));
                    }
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
        leadButton=new Buttons(955,75,"leaders",leadTexture.getWidth(),leadTexture.getHeight(),s,leadTexture,leadTexture);
        butt=new Buttons[2];
        for (int i=0;i<butt.length;i++){
            if (i!=0 && (buttTexts[i].toCharArray().length<buttTexts[i-1].toCharArray().length)){
                setupX=buttTexts[i-1].toCharArray().length-buttTexts[i].toCharArray().length;
            }
            butt[i]=new Buttons(Gdx.graphics.getWidth()/2+25*setupX-160,Gdx.graphics.getHeight()/4+200-i*MainGame.buttonDistanceFromEachOther,
                    "button_"+i,buttTexts[i],1.7f,s);
            s.addActor(butt[i]);
            butt[i].setTouchable(Touchable.disabled);
            setupX=0;
        }
        leadButton.setTouchable(Touchable.enabled);
        autoDialog=new AuthorizationDialog(2f,s);
        checkAuto();
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        inputMultiplexer.addProcessor(this.s);
        inputMultiplexer.addProcessor(inputProcessor);

        if (MainGame.registered) {FireBaseClass.addKDInDataBase();MainGame.registered=false;}
        if (MainGame.leaderMap!=null && isTouchOnSettings){ isTouchOnSettings=false; game.setScreen(new Leaderboard(game)); }
        checkAuto();

        Gdx.input.setInputProcessor(inputMultiplexer);

        batch.begin();
        batch.draw(backtxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(panel,Gdx.graphics.getWidth()/2-330,180,760,750);
        nameFont.draw(batch,"Space Arena",Gdx.graphics.getWidth()/2-240,Gdx.graphics.getHeight()-310);
        batch.end();


        s.act(delta);
        s.draw();
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
        dispose();
    }

    @Override
    public void dispose() {
        backtxt.dispose();
        batch.dispose();
        s.dispose();
        panel.dispose();
    }

    private void checkAuto(){
        if (MainGame.authorized) {
            autoDialog.becomeInvisible();
            if (!isTouchOnSettings) {
                for (Buttons button : butt) {
                    button.setTouchable(Touchable.enabled);
                }
                leadButton.setTouchable(Touchable.enabled);
            }
        }
        else {autoDialog.becomeVisible();
            for (Buttons button:butt){
                button.setTouchable(Touchable.disabled);
            }
            leadButton.setTouchable(Touchable.disabled);
        }
    }
}