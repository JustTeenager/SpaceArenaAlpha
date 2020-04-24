package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Platform extends ActorObj {

    private Texture platTexture;
    private Texture playertxt;
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
        this.top = bottom+plat.getHeight();
        this.bottom = bottom;
        this.left = left;
        this.right = left + plat.getWidth();
        this.coreX=left+playertxt.getWidth()/2;
        this.coreY=top-platTexture.getHeight()/2;
        rect=new Rectangle(left,bottom,platTexture.getWidth()+50,58);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(platTexture,coreX,coreY);

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
