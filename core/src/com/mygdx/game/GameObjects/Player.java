package com.mygdx.game.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Core.ArenaGame;
import com.mygdx.game.HUD.JoystickLeft;
import com.mygdx.game.HUD.JoystickRight;
import com.mygdx.game.Core.MainGame;

//Класс игрового персонажа
public class Player extends ActorObj {
    private Vector2 lastFrame;
    public Vector2 position;
    private JumpState jumpState;
    public Rectangle rectangle;
    public int hp;
    public int amountBullets;
    public boolean killed;

    private Texture txt;
    private Vector2 velocityY;


    private Vector2 startPosition;
    private float dt=0;
    private float velocity=250;
    private float JUMP=350;
    private int ID;
    private int animationNum;
    //переменная зеркальной отрисовки при развороте персонажа
    public boolean flip=false;

    private Array<TextureRegion> aim_player_2= new Array<>();
    private Array<TextureRegion> move_player_2= new Array<>();
    private Array<TextureRegion> jump_player_2= new Array<>();
    private Array<TextureRegion> dead_player_2= new Array<>();

    private Array<TextureRegion> aim_player_4= new Array<>();
    private Array<TextureRegion> move_player_4= new Array<>();
    private Array<TextureRegion> jump_player_4= new Array<>();
    private Array<TextureRegion> dead_player_4= new Array<>();


    public Array<TextureRegion> getTextureArray_aim_player_2() {return aim_player_2;}

    public Array<TextureRegion> getTextureArray_move_player_2() {
        return move_player_2;
    }

    public Array<TextureRegion> getTextureArray_jump_player_2() {
        return jump_player_2;
    }

    public Array<TextureRegion> getTextureArray_dead_player_2() {return dead_player_2;}

    public Array<TextureRegion> getTextureArray_aim_player_4() {
        return aim_player_4;
    }

    public Array<TextureRegion> getTextureArray_move_player_4() {
        return move_player_4;
    }

    public Array<TextureRegion> getTextureArray_jump_player_4() {
        return jump_player_4;
    }

    public Array<TextureRegion> getTextureArray_dead_player_4() {return dead_player_4;}


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAnimationNum() {
        return animationNum;
    }

    public void setAnimationNum(int animationNum) {
        this.animationNum = animationNum;
    }

    public JumpState getJumpState() {
        return jumpState;
    }

    public Texture getTxt() {
        return txt;
    }

    public enum JumpState {
        JUMPING,
        FALLING,
        GROUNDED
    }


    public Player(int x, int y) {
        position=new Vector2();
        //состояние персонажа по y - летит,стоит или падает
        jumpState = JumpState.FALLING;
        velocityY=new Vector2();
        lastFrame=new Vector2();
        killed=false;
        this.hp=100;
        this.amountBullets= MainGame.AMOUNT_BULLETS;

        txt=new Texture("Aim (1).png");
        setPosition(x,y);
        setWidth(txt.getWidth());
        setHeight(txt.getHeight());
        setOrigin(getWidth()/2,getHeight()/2);
        rectangle = new Rectangle(getX()-10, getY()+15,getWidth()-20,getHeight()-15);
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        dt+=delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        try{
        batch.draw(
                animation.getKeyFrame(dt),getX(),getY(),getOriginX(),
                getOriginY(),getWidth(),getHeight(),
                (flip ? -1 : 1)*getScaleX(),getScaleY(),getRotation() );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void collapse(Player player) {

    }

    @Override
    public void update() {
        //CURRENT PLAYER - игрок клиента
        if (this.getName().equals("CURRENT_PLAYER")) {
            lastFrame.set(position);
            float delta = Gdx.graphics.getDeltaTime();
            velocityY.y -= delta * MainGame.GRAVITY;
            position.mulAdd(velocityY, delta);
            setY(position.y);

            if (this.killed) {
                position.x=lastFrame.x;
                position.y=lastFrame.y;
                setPosition(position.x,position.y);
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
            if (!JoystickLeft.checkAngleLeft && JoystickLeft.isTouchLeft && MainGame.seconds>0) {
                flip = true;
                if (this.getID() == -100) {
                    if (!this.killed) {
                        useAnim(0.1f, true, move_player_2);
                        animationNum = 12;
                    }
                }
                else {
                    if (!this.killed) {
                        useAnim(0.1f, true, move_player_4);
                        animationNum = 22;
                    }
                }
                txt.dispose();
                if (!this.killed ) {
                    position.x = getX() - velocity * Gdx.graphics.getDeltaTime();
                    setX(position.x);
                }
            } else if (JoystickLeft.isTouchLeft && MainGame.seconds>0) {
                flip = false;
                if (this.getID() == -100) {
                    if (!this.killed) {
                        useAnim(0.1f, true, move_player_2);
                        animationNum = 12;
                    }
                }
                else {
                    if (!this.killed) {
                        useAnim(0.1f, true, move_player_4);
                        animationNum = 22;
                    }
                }
                txt.dispose();
                if (!this.killed) {
                    position.x = getX() + velocity * Gdx.graphics.getDeltaTime();
                    setX(position.x);
                }
            }
            if (!this.killed) rectangle.setPosition(getX(), getY()+15);

            if ((!JoystickLeft.isTouchLeft) && (jumpState == JumpState.GROUNDED)) {
                if (this.getID() == -100) {
                    if (!this.killed) {
                        useAnim(0.1f, true, aim_player_2);
                        animationNum = 11;
                    }
                }
                else {
                    if (!this.killed) {
                        useAnim(0.1f, true, aim_player_4);
                        animationNum = 21;
                    }
                }
                txt.dispose();
            }
                for (Platform pl : ArenaGame.plat) {
                    platformReact(pl);
            }
        }
    }
    @Override
    public void platformReact(Platform pl) {
        float x1=lastFrame.x;
        float y1=lastFrame.y;
        float x2=lastFrame.x+txt.getWidth();
        float y2=lastFrame.y+txt.getHeight();
        float x3=pl.left;
        float y3=pl.rect.y;
        float x4=pl.left+pl.getPlatTexture().getWidth();
        float y4=pl.rect.y+pl.getPlatTexture().getHeight();

        //условия соприкосновения по x и y
        boolean x=((x3 > x1 && x3 < x2) || (x4 > x1 && x4 < x2) || (x1 > x3 && x1 < x4) || (x2 > x3 && x2 < x4));
        boolean y=((y3 > y1 && y3 < y2) || (y4 > y1 && y4 < y2) || (y1 > y3 && y1 < y4) || (y2 > y3 && y2 < y4));

        if(position.x>915 || position.x<-835){// чтобы персонаж не выходил за границы карты
            position.x=lastFrame.x;
            setX(position.x);
        }

        //проверка соприкосновения физических "обьектов " - rectangle
        if (rectangle.overlaps(pl.rect)) {
                    if (y && rectangle.y >= pl.rect.y) {
                        if (velocityY.y <= 0) {
                            velocityY.y = 0;
                            jumpState = JumpState.GROUNDED;
                            position.y = pl.rect.y + pl.rect.getHeight()-15;
                        }
                    } else {
                        if (y && rectangle.y<= pl.rect.y) {
                                if (velocityY.y>0){
                                    if ((jumpState!=JumpState.GROUNDED) && (rectangle.getX()>pl.rect.getX() && rectangle.getX()<pl.rect.getX()+pl.rect.getWidth()))
                                    velocityY.y = -10;
                                }
                        }
                        if (x) {
                            position.x = lastFrame.x - (JoystickLeft.checkAngleLeft ? 1 : -1) * 0.3f;
                        }
                    }

                    setPosition(position.x, position.y);
                    rectangle.setPosition(position.x, position.y+15);
        }
    }
    private void endJump() {
        if (jumpState == JumpState.JUMPING) {
            jumpState = JumpState.FALLING;
        }
        MainGame.jumped=false;
    }

    private void startJump(){
        jumpState=JumpState.JUMPING;
        if (!this.killed) velocityY.y+=JUMP;
        if (this.getID()==-100) {
            if (!this.killed) {
                useAnim(0.1f, false, jump_player_2);
                animationNum = 13;
            }
        }
        else {
            if (!this.killed) {
                useAnim(0.1f, false, jump_player_4);
                animationNum = 23;
            }
        }
        continueJump();
    }

    private void continueJump(){
        if (jumpState == JumpState.JUMPING)
            endJump();
    }

    public void setStartPosition(Vector2 startPosition) {
        this.startPosition = startPosition;
    }

    //восстановление состояний переменных  после смерти
    public void setNextRound(){
        this.position.set(startPosition);
        setPosition(position.x,position.y);
        if (this.hp==0)
        this.amountBullets=MainGame.AMOUNT_BULLETS;
        this.hp=100;

        this.flip=false;
        this.killed=false;
        JoystickLeft.isTouchLeft=false;
        JoystickLeft.angleLeft=0;
        JoystickLeft.checkAngleLeft=true;
        JoystickRight.isTouchRight=false;
        JoystickRight.angleRight=0;
        JoystickRight.checkAngleRight=true;
        MainGame.bulletsDamage=5;
        ArenaGame.chaseCam.update();
    }

    public void dispose(){
        txt.dispose();
    }
}