package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

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

    public MainMenu(final MainGame game){
        this.game=game;

        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));

        batch = new SpriteBatch();
        s=new Stage();
        backtxt=new Texture("menuBack.jpg");
        setupX=0;
        panel=new Texture("Panel 2.png");
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
                if ((screenY>butt[0].btn.getY()&&screenY<butt[0].btn.getY()+butt[0].btn.getHeight())
                        && (screenX>butt[0].btn.getX()&&screenX<butt[0].btn.getX()+butt[0].btn.getWidth())){
                    clickSound.play();
                }
                else if ((screenY>butt[1].btn.getY()&&screenY<butt[1].btn.getY()+butt[1].btn.getHeight()
                        && (screenX>butt[1].btn.getX()&&screenX<butt[1].btn.getX()+butt[1].btn.getWidth()))){
                    clickSound.play();
                    try {
                        game.setScreen(new WaitingMenu(game));
                    } catch (Exception e) {
                        e.printStackTrace();
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

        butt=new Buttons[2];
        for (int i=0;i<butt.length;i++){
            if (i!=0 && (buttTexts[i].toCharArray().length<buttTexts[i-1].toCharArray().length)){
                setupX=buttTexts[i-1].toCharArray().length-buttTexts[i].toCharArray().length;
            }
            butt[i]=new Buttons(Gdx.graphics.getWidth()/2+Gdx.graphics.getWidth()/150*setupX-50,panel.getHeight()+250-i*100,
                    "button_"+i,buttTexts[i],1f,s);
            s.addActor(butt[i]);
            setupX=0;
        }
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
        batch.draw(panel,Gdx.graphics.getWidth()/2-115,400,400,325);
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
        clickSound.dispose();
    }
}
