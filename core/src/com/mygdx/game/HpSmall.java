package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class HpSmall extends Actor {
    private Stage stage;

    private Texture hpLowScale;
    private Sprite hpLowScaleSprite;

    public HpSmall(Stage st){
        hpLowScale=new Texture("Small health Bar.png");
        hpLowScaleSprite=new Sprite(hpLowScale);
        this.stage=st;
        stage.addActor(this);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (ArenaGame.ENEMY.hp>=0)
            batch.draw(hpLowScaleSprite,ArenaGame.ENEMY.getX(),ArenaGame.ENEMY.getY()+ArenaGame.ENEMY.getTxt().getHeight()+10,hpLowScaleSprite.getWidth()*ArenaGame.ENEMY.hp/MainGame.MAX_HP,hpLowScaleSprite.getHeight());
    }
}
