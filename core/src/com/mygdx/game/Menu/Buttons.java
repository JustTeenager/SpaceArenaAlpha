package com.mygdx.game.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.mygdx.game.Core.MainGame;

//класс всех испоьзуемых кнопок,обьект включает в себя кнопку и "обертку для нее" - текстуру и текст
public class Buttons extends Actor {
    static BitmapFont font;
    private GlyphLayout gl;

    public TextButton btn;
    private TextButton.TextButtonStyle style;
    private Texture tappedTexture;
    private Texture untappedTexture;

    //создание кнопки с текстом
    public Buttons(float x, float y,String name, String text,float size, Stage s){
        super();
        setName(name);

        this.tappedTexture=new Texture("Button 1 ON.png");
        this.untappedTexture=new Texture("Button 1 OFF.png");

        Drawable on=new Image(tappedTexture).getDrawable();
        Drawable off=new Image(untappedTexture).getDrawable();

        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.getData().setScale(size);
        style=new TextButton.TextButtonStyle();
        style.font=font;
        style.fontColor=new Color(0,0,0,0.55f);
        style.up=on;
        style.down=off;
        style.checked=on;

        gl=new GlyphLayout(font, text);

        btn=new TextButton(text,style);
        btn.setName(name);
        btn.setSize(gl.width+ MainGame.buttonDistancefromPanel,gl.height+ MainGame.buttonDistancefromPanel);
        btn.setX(x);
        btn.setY(y);
        s.addActor(this);
    }

    //создание кнопки с какой-то картинкой
    public Buttons(float x, float y,String name,float width,float height, Stage s,Texture tappedtxt,Texture untappedtxt){
        super();
        setName(name);

        this.tappedTexture=tappedtxt;
        this.untappedTexture=untappedtxt;

        Drawable on=new Image(tappedTexture).getDrawable();
        Drawable off=new Image(untappedTexture).getDrawable();

        font=new BitmapFont(Gdx.files.internal("liter.fnt"));
        style=new TextButton.TextButtonStyle();
        style.font=font;
        style.fontColor=new Color(0,0,0,0.55f);
        style.up=on;
        style.down=off;
        style.checked=on;

        btn=new TextButton("",style);
        btn.setName(name);
        btn.setSize(width,height);
        btn.setX(x);
        btn.setY(y);
        s.addActor(this);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void draw(Batch batch, float parentAlpha){
        btn.draw(batch,parentAlpha);
    }
}

