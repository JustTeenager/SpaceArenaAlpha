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
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import static java.lang.StrictMath.abs;

public class Leaderboard implements Screen {
    private int yScale;
    private Buttons backButton;
    private SpriteBatch batch;
    private String string;
    private ArrayList<HashMap> leaderList;
    private MainGame game;
    private Sound clickSound;
    private Stage s;
    private Texture backtxt;
    private InputMultiplexer inputMultiplexer;
    private InputProcessor inputProcessor;
    private ScrollPane.ScrollPaneStyle style;
    private Texture scroll;
    private Texture scrollKnob;
    private BitmapFont labelFont;
    private Label.LabelStyle labelStyle;
    private Table table;
    private Table coreTable;
    Leaderboard(final MainGame game){
        this.game=game;
        yScale=(Gdx.graphics.getHeight()-465)/3-150;
        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));
        batch = new SpriteBatch();
        backtxt=new Texture("menuBack.jpg");
        scroll= new Texture("Slider 1.png");
        scrollKnob= new Texture("Slider 2.png");
        Drawable drawableBack = new Image(backtxt).getDrawable();
        Drawable drawableScroll = new Image(scroll).getDrawable();
        Drawable drawableScrollKnob = new Image(scrollKnob).getDrawable();
        labelFont=new BitmapFont(Gdx.files.internal("registerLit.fnt"));
        labelFont.getData().setScale(1.5f);
        style = new ScrollPane.ScrollPaneStyle(drawableBack,null,null,drawableScroll,drawableScrollKnob);
        labelStyle= new Label.LabelStyle(labelFont,new Color(1,1,1,0.55f));
        s= new Stage();
        backButton=new Buttons(Gdx.graphics.getWidth()/2-120,yScale,"backButt","Back",2f,s);
        Gdx.input.setInputProcessor(s);
        leaderList=new ArrayList<>();
        leaderSetting();

        coreTable = new Table();
        s.addActor(coreTable);
        //coreTable.setFillParent(true);
        coreTable.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()-300);
        coreTable.setY(300);

        table = new Table();

        final ScrollPane scroll = new ScrollPane(table, style);
        scroll.setScrollingDisabled(true,false);

        table.pad(100).defaults().expandX().space(100);
        for (int i = 0; i < leaderList.size(); i++) {
            table.row();
            string=leaderList.get(i).get("Name")+"     "+leaderList.get(i).get("KD");
            Label label=new Label(string, labelStyle);
            label.setAlignment(Align.center);
            label.setWrap(true);
            table.add(label).width(Gdx.graphics.getWidth());
        }

        coreTable.add(scroll).expand().fill();

        inputMultiplexer=new InputMultiplexer();
        inputProcessor = new InputProcessor() {
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
                        && (screenX>backButton.btn.getX()&&screenX<backButton.btn.getX()+backButton.btn.getWidth()) && backButton.isTouchable()){
                    clickSound.play(MainGame.volume);
                    MainGame.leaderMap=null;
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
        inputMultiplexer.addProcessor(this.s);
        inputMultiplexer.addProcessor(inputProcessor);

        Gdx.input.setInputProcessor(inputMultiplexer);

        batch.begin();
        batch.draw(backtxt,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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

    }

    @Override
    public void dispose() {

    }
    public void leaderSetting(){
        Set set = MainGame.leaderMap.keySet();
        Object[] array= set.toArray();
        for (int i=0;i<array.length;i++){
            leaderList.add((HashMap)MainGame.leaderMap.get(array[i]));
        }
    }
}
