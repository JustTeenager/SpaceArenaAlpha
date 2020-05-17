package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.utils.Align;

public class Ammunition extends ActorObj {
    private Texture txtAmmunition;
    private Rectangle recAmmunition;
    private Stage stage;
    private int pickedTime;

    Ammunition(Texture txtAmmunition, float x, float y, Stage  stage){
        this.stage=stage;
        this.txtAmmunition=txtAmmunition;
        setPosition(x,y);
        recAmmunition = new Rectangle();
        recAmmunition.set(getX(),getY(),txtAmmunition.getWidth(),txtAmmunition.getHeight());
        this.setTransform(true);
        this.setOrigin(Align.center);
        this.addAction(Actions.repeat(RepeatAction.FOREVER,
                Actions.sequence(
                        Actions.moveBy(0,-35,1.6f),
                        Actions.moveBy(0,35,1.6f))));
        this.stage.addActor(this);
    }

    public void collapse(Player player){
        if (this.recAmmunition.overlaps(player.rectangle) && this.isVisible() && !GameHUD.scoreLogs.isVisible()){
            pickedTime=MainGame.seconds;
            player.amountBullets=MainGame.AMOUNT_BULLETS;//восстанавливаем количество пуль до начального значения
            this.setVisible(false);
        }
        if (pickedTime-MainGame.seconds>=8){
            ammoRespawn();
        }
    }

    public void ammoRespawn(){
        this.setVisible(true);
    }

    @Override
    public void update() {
        collapse(ArenaGame.CURRENT_PLAYER);
        collapse(ArenaGame.ENEMY);
    }

    @Override
    public void platformReact(Platform pl) {
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(txtAmmunition,this.getX(),this.getY());
    }
}
