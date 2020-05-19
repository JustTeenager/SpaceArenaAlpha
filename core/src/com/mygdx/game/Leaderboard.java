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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class Leaderboard implements Screen {
    SpriteBatch batch;


    private MainGame game;
    private Sound clickSound;
    private Stage s;
    private Texture backtxt;
    private InputMultiplexer inputMultiplexer;
    private InputProcessor inputProcessor;
    private Buttons butt;
    private ScrollPane.ScrollPaneStyle style;
    private Texture scroll;
    private Texture scrollKnob;
    private BitmapFont labelFont;
    private Label.LabelStyle labelStyle;
    private Table table;
    private Table coreTable;
    Leaderboard(final MainGame game){
        this.game=game;
        clickSound=Gdx.audio.newSound(Gdx.files.internal("clickmusic.wav"));
        batch = new SpriteBatch();
        backtxt=new Texture("menuBack.jpg");
        scroll= new Texture("Slider 1.png");
        scrollKnob= new Texture("Slider 2.png");
        Drawable drawableBack = new Image(backtxt).getDrawable();
        Drawable drawableScroll = new Image(scroll).getDrawable();
        Drawable drawableScrollKnob = new Image(scrollKnob).getDrawable();
        labelFont=new BitmapFont(Gdx.files.internal("liter.fnt"));
        labelFont.getData().setScale(1.5f);
        style = new ScrollPane.ScrollPaneStyle(drawableBack,null,null,drawableScroll,drawableScrollKnob);
        labelStyle= new Label.LabelStyle(labelFont,new Color(0,0,0,0.55f));
        s= new Stage();
        Gdx.input.setInputProcessor(s);

        coreTable = new Table();
        s.addActor(coreTable);
        coreTable.setFillParent(true);

        table = new Table();

        final ScrollPane scroll = new ScrollPane(table, style);
        scroll.setScrollingDisabled(true,false);

        table.pad(100).defaults().expandX().space(100);
        for (int i = 0; i < 100; i++) {
            table.row();

            Label label=new Label(i + ". TABLE GAVNINA.", labelStyle);
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
}
