package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameHUD extends BaseJoystick {
    public final Viewport viewport;
    private Player pl;
    final BitmapFont font;
    private Stage stage;
    private Buttons jumpbtn;

    public GameHUD(Stage s,Viewport view,Player pl) {
        this.viewport = view;
        this.stage=s;
        this.pl=pl;
        font = new BitmapFont(Gdx.files.internal("liter.fnt"));
        font.setColor(Color.BLACK);
        stage.addActor(this);
    }

    public void setjumpButton(int x, int y, String name, int width, int height, Texture texture){
        jumpbtn=new Buttons(x,y,name,width,height,stage,texture,texture);
        jumpbtn.btn.setColor(1,1,1,0.5f);
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if ((screenY > jumpbtn.btn.getY() && screenY < jumpbtn.btn.getY() + jumpbtn.btn.getHeight()+100)
                && (screenX > jumpbtn.btn.getX() && screenX < jumpbtn.btn.getX() + jumpbtn.btn.getWidth())) {
            if (pl.getJumpState()==Player.JumpState.GROUNDED)
                MainGame.jumped = true;
        }
        return false;
    }
}

