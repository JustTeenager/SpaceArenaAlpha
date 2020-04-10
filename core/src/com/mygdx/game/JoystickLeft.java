package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public class JoystickLeft extends BaseJoystick {
    private Texture circle;
    private Texture circleCur;

    private float curX=0;
    private float curY=0;
    public static boolean isTouchLeft= false;
    public static boolean CheckAngleLeft=true;
    private float rad = 0;
    public static double angleLeft=0;

    private static final float CURSOR_RADIUS = 40;
    public JoystickLeft(Texture circle, Texture circleCur){
        super();
        this.circle = circle;
        this.circleCur = circleCur;
        setDefaultWH();
        setDefaultXY();
        //setVisible(false);
    }

    public void isTouch(float x,float y) { // проверяем коснулись ли мы курсора
        if ((x>=40 && x<=360) &&(y>=720 && y<=1040)) {
            isTouchLeft = true;
        }
    }

    public void setDefaultWH(){// задаётся ширина,высота и радиус большого шара
        setWidth(320);
        setHeight(320);
        rad = 160;

    }
    public void setDefaultXY(){//задаётся левый нижни угол квадрата длбольшого шара
        setX(40);
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
        if (isTouchLeft){
            batch.draw(circleCur,this.getX()+rad-CURSOR_RADIUS+curX,
                    this.getY()+rad - CURSOR_RADIUS+curY,
                    2*CURSOR_RADIUS,
                    2*CURSOR_RADIUS);
        }
        else{            batch.draw(circleCur,this.getX()+rad-CURSOR_RADIUS,
                this.getY()+rad - CURSOR_RADIUS,
                2*CURSOR_RADIUS,
                2*CURSOR_RADIUS);
        }
    }
    public void changeCursor(float x, float y){
        float dx = x - rad-40;
        float dy =720-(y - rad);
        float lenght = (float) Math.sqrt(dx*dx+dy*dy);
        if (lenght<rad){
            this.curX = dx;
            this.curY =dy;
        }
        else{
            float k = rad/lenght;
            this.curX = k*dx;
            this.curY = k*dy;
        }

    }
    public  void setAngle(){
        angleLeft = Math.atan(curY/curX)*180/Math.PI;
        if(angleLeft>0 &&curY<0)
            angleLeft+=180;
        if(angleLeft <0) {
            if (curX < 0)
                angleLeft = 180 + angleLeft;
            else
                angleLeft += 360;
        }

        if (angleLeft>0 && angleLeft<=90 || angleLeft<360 && angleLeft>=270){
            CheckAngleLeft = true;
        }
        else {
            CheckAngleLeft = false;
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX<1000 && screenY>700) {
            isTouch(screenX, screenY);
            if (isTouchLeft) {
                changeCursor(screenX, screenY);
                setAngle();
                //setVisible(true);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX<1000  ) {
            isTouchLeft = false;
            angleLeft = 0;
            //setVisible(false);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX<1000 && screenY>700) {
            changeCursor(screenX, screenY);
            setAngle();
        }
        return false;
    }
}
