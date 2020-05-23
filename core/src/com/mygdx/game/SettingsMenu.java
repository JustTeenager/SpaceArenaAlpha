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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import static java.lang.StrictMath.abs;

public class SettingsMenu implements Screen {
    private MainGame game;
    Stage st;

    private SpriteBatch batch;
    private Texture backTxt;
    private Texture volumeTxt;
    private Texture volumeScale;
    private Texture soundTxt;
    private Texture panel;

    private Buttons volumeButton;
    private Buttons backButton;
    private Buttons setNameButton;
    private Buttons logOutButton;

    private AuthorizationDialog autoDialog;

    private InputProcessor inputProcessor;
    private InputMultiplexer inputMultiplexer;
    private Sound clickSound;
    private BitmapFont nameFont;
    private float distance;
    private float distanceGeneral;
    private int xScale;
    private int yScale;

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
        logOutButton=new Buttons(Gdx.graphics.getWidth()/2+100,yScale/3+180,"logOutButt","Log out",2f,st);
        setNameButton=new Buttons(Gdx.graphics.getWidth()/2-500,yScale/3+180,"nameButt","Set Name",2f,st);

        distanceGeneral=volumeScale.getWidth();
        distance=MainGame.volume*distanceGeneral;

        autoDialog=new AuthorizationDialog(2f,st);
        autoDialog.becomeInvisible();

        float prevVolumePosition;
        if (MainGame.volButtonX!=-1){
            prevVolumePosition=MainGame.volButtonX;
            volumeButton.btn.setX(prevVolumePosition);
            System.out.println("changed");
        }

        inputMultiplexer=new InputMultiplexer();

        inputProcessor=new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return true;
            }

            @Override
            public boolean keyUp(int keycode) {
                return true;
            }

            @Override
            public boolean keyTyped(char character) {
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if ((abs(Gdx.graphics.getHeight()-screenY)>logOutButton.btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<logOutButton.btn.getY()+logOutButton.btn.getHeight())
                        && (screenX>logOutButton.btn.getX()&&screenX<logOutButton.btn.getX()+logOutButton.btn.getWidth()) && !autoDialog.isVisible()){
                    System.out.println("FB LOGOUT");
                    clickSound.play(MainGame.volume);
                    FireBaseClass.signOut(autoDialog);
                }
                if (autoDialog.isVisible()) {
                    System.out.println(autoDialog.isTouchable());
                    System.out.println("auth is visible");
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

                else if ((abs(Gdx.graphics.getHeight()-screenY)>setNameButton.btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<setNameButton.btn.getY()+setNameButton.btn.getHeight())
                        && (screenX>setNameButton.btn.getX()&&screenX<setNameButton.btn.getX()+setNameButton.btn.getWidth()) && setNameButton.isTouchable()){
                    clickSound.play(MainGame.volume);
                    NameInput input = new NameInput(inputMultiplexer);
                    Gdx.input.getTextInput(input, "Enter your name", MainGame.current_player_name, "Your name");
                }

                else if ((abs(Gdx.graphics.getHeight()-screenY)>backButton.btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<backButton.btn.getY()+backButton.btn.getHeight())
                        && (screenX>backButton.btn.getX()&&screenX<backButton.btn.getX()+backButton.btn.getWidth()) && backButton.isTouchable()){
                    MainGame.volume=distance/distanceGeneral;
                    clickSound.play(MainGame.volume);
                    try {
                        FireBaseClass.updatePLayerName(MainGame.current_player_name);
                    }catch (Exception e){e.printStackTrace();}
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
                if (!autoDialog.isVisible()) {
                    if (abs(Gdx.graphics.getHeight() - screenY) >= volumeButton.btn.getY() && abs(Gdx.graphics.getHeight() - screenY) <= volumeButton.btn.getY() + volumeButton.btn.getHeight() && volumeButton.isTouchable()) {
                        Vector2 previous = new Vector2(volumeButton.btn.getX(), volumeButton.btn.getY());
                        volumeButton.btn.setX(screenX);
                        if (volumeButton.btn.getX() <= xScale || volumeButton.btn.getX() >= (xScale + volumeScale.getWidth() - volumeTxt.getWidth()))
                            volumeButton.btn.setPosition(previous.x, previous.y);

                        if (screenX >= xScale && screenX <= xScale + volumeScale.getWidth()) {
                            distance = screenX - xScale;
                            MainGame.volButtonX = volumeButton.btn.getX();
                        }
                        if (screenX <= xScale) {
                            distance = 0;
                            MainGame.volButtonX = xScale;
                        }
                        if (screenX >= xScale + volumeScale.getWidth() - volumeTxt.getWidth()) {
                            distance = distanceGeneral;
                            MainGame.volButtonX = xScale + volumeScale.getWidth() - volumeTxt.getWidth();
                        }
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
        checkAuto();
    }
    private void checkAuto(){
        if (MainGame.authorized) {
            autoDialog.becomeInvisible();
            backButton.setTouchable(Touchable.enabled);
            volumeButton.setTouchable(Touchable.enabled);
            logOutButton.setTouchable(Touchable.enabled);
            setNameButton.setTouchable(Touchable.enabled);
        }
        else {
            autoDialog.becomeVisible();
            backButton.setTouchable(Touchable.disabled);
            volumeButton.setTouchable(Touchable.disabled);
            logOutButton.setTouchable(Touchable.disabled);
            setNameButton.setTouchable(Touchable.disabled);
        }
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        inputMultiplexer.addProcessor(this.st);
        inputMultiplexer.addProcessor(inputProcessor);
        checkAuto();

        Gdx.input.setInputProcessor(inputMultiplexer);

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