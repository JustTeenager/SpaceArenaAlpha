package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class ScoreLogs extends Actor {

    private Stage stage;

    private GlyphLayout gl;
    private Texture backTxt;
    private BitmapFont scoreFont;

    public ScoreLogs(float size, Stage st){
        this.stage=st;
        backTxt=new Texture("Achievement Panel.png");
        scoreFont=new BitmapFont();
        scoreFont=new BitmapFont(Gdx.files.internal("liter.fnt"));
        scoreFont.getData().setScale(size);
        scoreFont.setColor(new Color(0,1,0,0.4f));
        gl=new GlyphLayout(scoreFont,(MainGame.current_player_name.length() >= MainGame.enemy_name.length() ? MainGame.current_player_name : MainGame.enemy_name));
        stage.addActor(this);
        setVisible(false);
    }


    public void draw(Batch batch, float parentAlpha) {
        //if (MainGame.timeFromLastKill - MainGame.seconds > 4) {
            batch.draw(backTxt, stage.getWidth() / 2 - 150, stage.getHeight() / 2,gl.width+75, 160);
            scoreFont.draw(batch, MainGame.current_player_name + ": " + MainGame.current_player_score, stage.getWidth() / 2 + 120, stage.getHeight() / 2 + 115);
            scoreFont.draw(batch, MainGame.enemy_name + ": " + MainGame.enemy_score, stage.getWidth() / 2 + 120, stage.getHeight() / 2 + 65);
        //}
    }
}