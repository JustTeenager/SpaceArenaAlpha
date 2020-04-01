package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public class Player extends ActorObj {
    Vector2 lastFrame;
    Vector2 position;

    JumpState jumpState;
    private Rectangle rectangle;
    private Texture txt;
    private Vector2 velocityY;
    private float dt=0;
    private float velocity=150;
    private float JUMP=325;
    public  static boolean flip=false;

    private Array<TextureRegion> aim_player_2= new Array<>();
    private Array<TextureRegion> move_player_2= new Array<>();
    private Array<TextureRegion> jump_player_2= new Array<>();

    public Array<TextureRegion> getTextureArray_aim_player_2() {
        return aim_player_2;
    }

    public Array<TextureRegion> getTextureArray_move_player_2() {
        return move_player_2;
    }

    public Array<TextureRegion> getTextureArray_jump_player_2() {
        return jump_player_2;
    }

    public JumpState getJumpState() {
        return jumpState;
    }

    enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED
    }




    public Player(int x, int y, Stage s) {
        super(x,y,s);
        position=new Vector2();
        jumpState = JumpState.FALLING;
        velocityY=new Vector2();
        lastFrame=new Vector2();

        txt=new Texture("Aim (1).png");
        setPosition(x,y);
        setWidth(txt.getWidth());
        setHeight(txt.getHeight());
        setOrigin(getWidth()/2,getHeight());
        rectangle = new Rectangle(getX(), getY(),getWidth(),getHeight());
        s.addActor(this);

    }



    @Override
    public void act(float delta) {
        super.act(delta);
        dt+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(
                animation.getKeyFrame(dt),getX(),getY(),getOriginX(),
                getOriginY(),getWidth(),getHeight(),
                (flip ? -1 : 1)*getScaleX(),getScaleY(),getRotation() );
        //batch.draw(txt,getX(),getY());
    }


    @Override
    public void collapse() {

    }

    @Override
    public void update() {
        lastFrame.set(position);
        float delta = Gdx.graphics.getDeltaTime();
        velocityY.y -= delta * MainGame.GRAVITY;
        position.mulAdd(velocityY, delta);
        setY(position.y);


        if (position.y - txt.getHeight() / 2 < 0) {
            jumpState = JumpState.GROUNDED;
            position.y = txt.getHeight() / 2;
            velocityY.y = 0;
        }

        if (MainGame.jumped) {
            switch (jumpState) {
                case GROUNDED:
                    startJump();
                    break;
                case JUMPING:
                    continueJump();
                    break;
                case FALLING:
                    break;
            }
        } else endJump();

        if (!JoystickLeft.CheckAngleLeft && JoystickLeft.isTouchLeft) {
            flip = true;
            useAnim(0.1f, true,move_player_2);
            txt.dispose();
            position.x = getX() - velocity * Gdx.graphics.getDeltaTime();
            setX(position.x);
        } else if (JoystickLeft.isTouchLeft) {
            flip = false;
            useAnim(0.1f, true,move_player_2);
            txt.dispose();
            position.x = getX() + velocity * Gdx.graphics.getDeltaTime();
            setX(position.x);
        }
        rectangle.setPosition(getX(), getY());
        if ((!JoystickLeft.isTouchLeft) && (jumpState== JumpState.GROUNDED)){
            useAnim(0.1f, true,aim_player_2);
            txt.dispose();
        }

        for (Platform pl : ArenaGame.plat){
            platformReact(pl);
        }
    }

    @Override
    public void platformReact(Platform pl) {
        if (lastFrame.x >= pl.left && lastFrame.x <= pl.right && lastFrame.y >= pl.top && (position.y - pl.top < 0.2f)) {//запрыгивать
            if (velocityY.y <= 0) {
                velocityY.y = 0;
                jumpState = JumpState.GROUNDED;
                position.y = pl.top;
            }
        } else if (lastFrame.x >= pl.left && lastFrame.x <= pl.right && position.y <= pl.top) {//стукаться головой о нижнюю грань
            if (pl.coreY - (position.y + txt.getHeight()) <= 0.2f) velocityY.y = -10;
        }


        else if ((jumpState==JumpState.GROUNDED)&&(pl.left - position.x < 0.2f) && (position.y+10f >= pl.bottom - txt.getHeight() + 50) && (position.y <= pl.top)) {
            position.x = lastFrame.x - 5f;
            setX(position.x);
        }
         /*else if ((pl.coreX-lastFrame.x-txt.getWidth()/2<1f) && (lastFrame.y+txt.getHeight()/2-30>=pl.bottom)&& (position.y-txt.getHeight()/2>pl.bottom)){
                if (lastFrame.y>=pl.bottom){
                    position.y=lastFrame.y;
                }
                position.x=lastFrame.x-5f;
                setX(position.x);
            }*/
        rectangle.setPosition(getX(), getY());
    }

    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
        MainGame.jumped=false;
    }

    private void startJump(){
        jumpState=JumpState.JUMPING;
        velocityY.y+=JUMP;
        useAnim(0.1f,false,jump_player_2);
        continueJump();
    }

    private void continueJump(){
        if (jumpState == JumpState.JUMPING)
            endJump();
    }

    public void dispose(){
        txt.dispose();
    }
}