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
import com.badlogic.gdx.scenes.scene2d.Stage;

import static java.lang.StrictMath.abs;

public class MainMenu implements Screen {
    private MainGame game;

    private Texture backtxt;

    SpriteBatch batch;
    private Stage s;
    private Texture panel;
    private Buttons[] butt;
    private String[] buttTexts;
    private float setupX;
    private InputProcessor inputProcessor;
    private Sound clickSound;

    BitmapFont nameFont;


    public MainMenu(final MainGame game){
        this.game=game;

        nameFont=new BitmapFont(Gdx.files.internal("liter.fnt"));
        nameFont.getData().setScale(2.8f);
        nameFont.setColor(new Color(0,0,0,0.55f));


        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));
        batch = new SpriteBatch();
        s=new Stage();
        backtxt=new Texture("menuBack.jpg");
        setupX=0;
        //panel=new Texture("Panel 2.png");
        panel=new Texture("Panel 3.png");
        buttTexts= new String[] {"Start Game", "Settings"};
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
                if ((abs(Gdx.graphics.getHeight()-screenY)>butt[0].btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<butt[0].btn.getY()+butt[0].btn.getHeight())
                        && (screenX>butt[0].btn.getX()&&screenX<butt[0].btn.getX()+butt[0].btn.getWidth())){
                    clickSound.play(MainGame.volume);
                    try {
                        if (!ClientClass.isClientStarted) {
                            ClientClass.startClient();
                            game.setScreen(new WaitingMenu(game));
                            ClientClass.isClientStarted=true;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else if ((abs(Gdx.graphics.getHeight()-screenY)>butt[1].btn.getY()&&abs(Gdx.graphics.getHeight()-screenY)<butt[1].btn.getY()+butt[1].btn.getHeight()
                        && (screenX>butt[1].btn.getX()&&screenX<butt[1].btn.getX()+butt[1].btn.getWidth()))){
                    clickSound.play(MainGame.volume);
                    game.setScreen(new SettingsMenu(game));
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

        butt=new Buttons[2];
        for (int i=0;i<butt.length;i++){
            if (i!=0 && (buttTexts[i].toCharArray().length<buttTexts[i-1].toCharArray().length)){
                setupX=buttTexts[i-1].toCharArray().length-buttTexts[i].toCharArray().length;
            }
            butt[i]=new Buttons(Gdx.graphics.getWidth()/2+25*setupX-160,Gdx.graphics.getHeight()/4+200-i*MainGame.buttonDistanceFromEachOther,
                    "button_"+i,buttTexts[i],1.7f,s);
            s.addActor(butt[i]);
            setupX=0;
        }
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(inputProcessor);

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
}
