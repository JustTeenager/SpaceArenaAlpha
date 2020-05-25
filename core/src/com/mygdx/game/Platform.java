package com.mygdx.game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;


//к
public class Platform extends ActorObj {

    private Texture platTexture;
    //float coreX;
    public float coreY;
    public float top;
    public float bottom;
    public float left;
    public float right;
    public Rectangle rect;

    public Platform(float left, float bottom,Texture plat, Stage s) {
        super(left,bottom,s);
        this.platTexture=plat;
        this.top = bottom+plat.getHeight()-5;
        this.bottom = bottom;
        this.left = left;
        this.right = left + plat.getWidth();
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
