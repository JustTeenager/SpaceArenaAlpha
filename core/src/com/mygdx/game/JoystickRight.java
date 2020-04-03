package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class JoystickRight extends BaseJoystick {
    private Texture circle;
    private Texture circleCur;
    private float curX=0;
    private float curY=0;
    public static Vector2 direction;
    public static boolean isTouchRight= false;
    private float rad = 0;
    public static double angleRight=0;
    public static boolean CheckAngleRight= false;

    private static final float CURSOR_RADIUS = 40;
    public JoystickRight(Texture circle, Texture circleCur){
        super();
        this.circle = circle;
        this.circleCur = circleCur;
        direction = new Vector2();
        setDefault();
        setDefaultXY();
        //setVisible(false);
    }

    public void isTouch(float x,float y) { // проверяем коснулись ли мы курсора
        //if ((x>=440 && x<=600) &&(y>=280 && y<=440)) {//числа подобраны для десктопа
        if ((x>=1550 && x<=1870) &&(y>=720 && y<=1040)) {//числа подобраны для телефона
            isTouchRight = true;
        }
    }

    public void setDefault(){// задаётся ширина,высота и радиус большого шара
        setWidth(320);
        setHeight(320);
        rad = 160;

    }
    public void setDefaultXY(){//задаётся левый нижни угол квадрата длбольшого шара
        //setX(440);
        setX(1550);//для телефона
        setY(40);
    }
    public void setWidth(float w){
        super.setWidth(w);
        super.setHeight(w);
        rad = w/2;
    }
    public void setHeight(float h){
        super.setWidth(h);
        super.setHeight(h);
        rad = h/2;
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(1,1,1,0.5f);
        batch.draw(circle,this.getX(),this.getY(),this.getWidth(),this.getHeight());
        if (isTouchRight){// если курсор двигается
            batch.draw(circleCur,this.getX()+rad-CURSOR_RADIUS+curX,
                    this.getY()+rad - CURSOR_RADIUS+curY,
                    2*CURSOR_RADIUS,
                    2*CURSOR_RADIUS);
        }
        else{            batch.draw(circleCur,this.getX()+rad-CURSOR_RADIUS,// если курсор не двигают
                this.getY()+rad - CURSOR_RADIUS,
                2*CURSOR_RADIUS,
                2*CURSOR_RADIUS);
        }
    }
    public void changeCur(float x, float y){// функция , которая не даёт курсору выйти за границы большого круга
        float dx = x - rad-1550;//1700 подобрано специально, чтобы всё работало исправно
        //float dy =280-(y - rad);//280 подобрано специально для десктопа
        float dy =720-(y - rad);//720 подобрано специально для телефона
        float length = (float) Math.sqrt(dx*dx+dy*dy);
        float k = rad/length;
        if (length<rad){
            this.curX = dx;
            this.curY =dy;
        }
        else{
            this.curX = k*dx;
            this.curY = k*dy;
        }

        direction.x = curX/length;
        direction.y = curY/length;
    }
    public void setAngle(){//функция которая определяет на какой угол отклонён курсор
        angleRight = Math.atan(curY/curX)*180/Math.PI;
        if(angleRight>0 &&curY<0)
            angleRight+=180;
        if(angleRight <0) {
            if (curX < 0)
                angleRight = 180 + angleRight;
            else
                angleRight += 360;
        }

        if (angleRight>0 && angleRight<=90 || angleRight<360 && angleRight>=270){
            CheckAngleRight = true;
        }
        else {
            CheckAngleRight = false;
        }

    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX>1000 && screenY>700) {
            isTouch(screenX, screenY);
            if (isTouchRight) {
                changeCur(screenX, screenY);
                //setVisible(true);
                setAngle();
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX>1000 ) {
            isTouchRight = false;
            angleRight = 0;
        }
        //setVisible(false);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX>1000 && screenY>700) {
            changeCur(screenX, screenY);
            setAngle();
        }
        return false;
    }

    private float one = Gdx.graphics.getDeltaTime();
    private float two = Gdx.graphics.getDeltaTime()-5;
    public void checkCreateBullet(){//функция для создание пуль
        if (JoystickRight.isTouchRight && JoystickLeft.CheckAngleLeft==CheckAngleRight && one-two>=5){
            ArenaGame.shootings.add(new Shooting(ArenaGame.pl1.getX(),ArenaGame.pl1.getY(),ArenaGame.playerStage,JoystickRight.direction,1000f));
            two=one;
        }
        else{
            one++;
        }
    }
}
