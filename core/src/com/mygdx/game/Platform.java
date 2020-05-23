package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Platform extends ActorObj {

    private Texture platTexture;
    private Texture playertxt;
    private ShapeRenderer shapeRenderer=new ShapeRenderer();
    float coreX;
    float coreY;
    float top;
    float bottom;
    float left;
    float right;

    Rectangle rect;

    public Platform(float left, float bottom,Texture plat, Stage s) {
        super(left,bottom,s);
        this.platTexture=plat;
        this.playertxt=new Texture("Aim (1).png");
        this.top = bottom+plat.getHeight()-5;
        this.bottom = bottom;
        this.left = left;
        this.right = left + plat.getWidth();
        this.coreX=left;
        this.coreY=top-platTexture.getHeight()/2;
        rect=new Rectangle(left,bottom,platTexture.getWidth(),platTexture.getHeight()-25);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);


        batch.draw(platTexture,rect.getX(),rect.getY(),platTexture.getWidth(),rect.getHeight()+12.4f);
    }

    @Override
    public void collapse(Player player) {
    }

    @Override
    public void update() {
    }

    @Override
    public void platformReact(Platform pl) {

    }

    public Texture getPlatTexture() {
        return platTexture;
    }
}
