package com.mygdx.game.HUD;import com.badlogic.gdx.Gdx;import com.badlogic.gdx.graphics.Color;import com.badlogic.gdx.graphics.Texture;import com.badlogic.gdx.graphics.g2d.BitmapFont;import com.badlogic.gdx.graphics.g2d.Sprite;import com.badlogic.gdx.graphics.g2d.SpriteBatch;import com.badlogic.gdx.math.Vector2;import com.badlogic.gdx.scenes.scene2d.Stage;import com.badlogic.gdx.utils.viewport.Viewport;import com.mygdx.game.Menu.Buttons;import com.mygdx.game.Core.MainGame;import com.mygdx.game.Dialogs.FinalDialog;import com.mygdx.game.GameObjects.Player;import com.mygdx.game.Dialogs.ScoreLogs;import com.mygdx.game.Dialogs.SettingsDialog;import static com.mygdx.game.Core.ArenaGame.CURRENT_PLAYER;import static com.mygdx.game.Core.ArenaGame.gameMusic;import static java.lang.StrictMath.abs;//класс игрового интерфейсаpublic class GameHUD extends BaseJoystick {    public final Viewport viewport;    private Player pl;    private BitmapFont font;    private Stage stage;    private Buttons jumpbtn;    private Buttons settingbtn;    private Buttons volumeButton;    private Buttons backButtonFinal;    private SettingsDialog dialog;    private FinalDialog finalDialog;    private Texture timePanel;    private BitmapFont timeBitmap;    private Texture hpPanel;    private Texture hpScale;    private Sprite hpSprite;    public static ScoreLogs scoreLogs;    private Texture bulletsTxt;    public GameHUD(Stage s, Viewport view, Player pl) {        this.viewport = view;        this.stage=s;        this.pl=pl;        font = new BitmapFont(Gdx.files.internal("liter.fnt"));        font.setColor(new Color(new Color(0,1,0,0.55f)));        font.getData().setScale(1.7f);        stage.addActor(this);        bulletsTxt=new Texture("bulletsTxt.png");    }    public Buttons getBackButtonFinal() {        return backButtonFinal;    }    public FinalDialog getFinalDialog() {        return finalDialog;    }    public void setjumpButton(int x, int y, String name, int width, int height, Texture texture){        jumpbtn=new Buttons(x,y,name,width,height,stage,texture,texture);        jumpbtn.btn.setColor(1,1,1,0.5f);    }    public void setSettingsButton(int x, int y, String name, int width, int height, Texture texture){        settingbtn=new Buttons(x,y,name,width,height,stage,texture,texture);    }    public void setTimePanel(){        timePanel=new Texture("Time Panel.png");        timeBitmap=new BitmapFont();        timeBitmap=new BitmapFont(Gdx.files.internal("liter.fnt"));        timeBitmap.getData().setScale(2.25f);        timeBitmap.setColor(new Color(0,1,0,0.4f));    }    public void drawTimer(float delta, SpriteBatch batch){        batch.draw(timePanel,80,stage.getHeight()-2.3f*timePanel.getHeight()-60);        timeBitmap.draw(batch, MainGame.time,60+timePanel.getWidth()/2,stage.getHeight()-1.6f*timePanel.getHeight()-60);        batch.draw(bulletsTxt,80,stage.getHeight()-2.3f*timePanel.getHeight()-200);        font.draw(batch,String.valueOf(CURRENT_PLAYER.amountBullets), 220,stage.getHeight()-2.3f*timePanel.getHeight()-120);    }    public void setHpPanel(){        hpPanel=new Texture("Health Panel.png");        hpScale=new Texture("Health Bar.png");        hpSprite=new Sprite(hpScale);    }    public void drawHpPanel(float delta, SpriteBatch batch){        batch.draw(hpPanel,60,stage.getHeight()-hpPanel.getHeight()-20);        if (CURRENT_PLAYER.hp>=0)            batch.draw(hpSprite,60+210,stage.getHeight()-hpPanel.getHeight()/2-60,hpSprite.getWidth()*CURRENT_PLAYER.hp/ com.mygdx.game.Core.MainGame.MAX_HP,hpSprite.getHeight());    }    public void setSettings(float size){        dialog=new SettingsDialog(size,stage);        volumeButton=new Buttons(dialog.xScale+dialog.volumeScale.getWidth()-dialog.volumeTxt.getWidth(),dialog.yScale-dialog.volumeTxt.getHeight()/4,                "volButt",dialog.volumeTxt.getWidth(),dialog.volumeTxt.getHeight(),dialog.getSt(),dialog.volumeTxt,dialog.volumeTxt);        dialog.addButton(volumeButton);        stage.addActor(volumeButton);    }    public void setFinalDialog(FinalDialog finalDialog){        this.finalDialog=finalDialog;        backButtonFinal=new Buttons(Gdx.graphics.getWidth()/2-120,stage.getHeight()/4-60,"backButt","Back",2f,stage);        finalDialog.addButton(backButtonFinal);        stage.addActor(backButtonFinal);    }    public void setScore(){        scoreLogs=new ScoreLogs(1.3f,stage);    }    //функция обработки нажатия на кнопки    @Override    public boolean touchDown(int screenX, int screenY, int pointer, int button) {        //нажатие на кнопку прыжка        if ((screenY > jumpbtn.btn.getY() && screenY < jumpbtn.btn.getY() + jumpbtn.btn.getHeight()+100)                && (screenX > jumpbtn.btn.getX() && screenX < jumpbtn.btn.getX() + jumpbtn.btn.getWidth())) {            if (pl.getJumpState()== Player.JumpState.GROUNDED)                MainGame.jumped = true;        }        //нажатие на кнопку выхода в финальном окне        if ((abs(Gdx.graphics.getHeight()-screenY) > backButtonFinal.btn.getY() && abs(Gdx.graphics.getHeight()-screenY) < backButtonFinal.btn.getY() + backButtonFinal.btn.getHeight()+100)                && (screenX > backButtonFinal.btn.getX() && screenX < backButtonFinal.btn.getX() + backButtonFinal.btn.getWidth())) {            finalDialog.returnToMenu();        }        //нажатие на кнопку настроек        if ((abs(Gdx.graphics.getHeight()-screenY) > settingbtn.btn.getY() && abs(Gdx.graphics.getHeight()-screenY) < settingbtn.btn.getY() + settingbtn.btn.getHeight()+100)                && (screenX > settingbtn.btn.getX() && screenX < settingbtn.btn.getX() + settingbtn.btn.getWidth())) {            if (!MainGame.isSettingsDialogOpened) {                float prevVolumePosition;                if (MainGame.volButtonX!=-1){                    prevVolumePosition=MainGame.volButtonX;                    volumeButton.btn.setX(prevVolumePosition);                }                dialog.setVisible(true);                for (Buttons butt : dialog.getbArray()) {                    butt.setVisible(true);                }                MainGame.isSettingsDialogOpened=true;            }            else{                MainGame.volume=dialog.getDistance()/dialog.getDistanceGeneral();                gameMusic.setVolume(com.mygdx.game.Core.MainGame.volume);                dialog.setVisible(false);                for (Buttons butt : dialog.getbArray()) {                    butt.setVisible(false);                }               MainGame.isSettingsDialogOpened=false;            }        }        //true - продолжать поиск считываемых нажатий        return true;    }    //регулировка звука (обрабатывает слайды)    @Override    public boolean touchDragged(int screenX, int screenY, int pointer) {        if (MainGame.isSettingsDialogOpened){            if (abs(Gdx.graphics.getHeight()-screenY)>=volumeButton.btn.getY() && abs(Gdx.graphics.getHeight()-screenY)<=volumeButton.btn.getY()+volumeButton.btn.getHeight()) {                Vector2 previous = new Vector2(volumeButton.btn.getX(), volumeButton.btn.getY());                volumeButton.btn.setX(screenX);                if (volumeButton.btn.getX() <= dialog.xScale || volumeButton.btn.getX() >= (dialog.xScale + dialog.volumeScale.getWidth() - dialog.volumeTxt.getWidth()))                    volumeButton.btn.setPosition(previous.x, previous.y);                if (screenX >= dialog.xScale && screenX <= dialog.xScale + dialog.volumeScale.getWidth()) {                    dialog.setDistance(screenX - dialog.xScale);                   MainGame.volButtonX=volumeButton.btn.getX();                }                if (screenX <= dialog.xScale) {                    dialog.setDistance(0);                    MainGame.volButtonX=dialog.xScale;                }                if (screenX >= dialog.xScale + dialog.volumeScale.getWidth() - dialog.volumeTxt.getWidth()) {                    dialog.setDistance(dialog.getDistanceGeneral());                    MainGame.volButtonX=dialog.xScale + dialog.volumeScale.getWidth() - dialog.volumeTxt.getWidth();                }            }        }        return true;    }}