package com.mygdx.game.HUD;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.game.Core.MainGame;
//класс левого джойстика, который отвечает за ходьбу
public class JoystickLeft extends BaseJoystick {
    private Texture circle;
    private Texture circleCur;

    private float curX=0;
    private float curY=0;
    public static boolean isTouchLeft= false;
    public static boolean checkAngleLeft=true;//отвечает за проверку угла, если угол лежит в пределах от 90 до 270, то переменная равняется false, иначе true
    private float rad = 0;
    public static double angleLeft=0;

    private static final float CURSOR_RADIUS = 60;
    public JoystickLeft(Texture circle, Texture circleCur){
        super();
        this.circle = circle;
        this.circleCur = circleCur;
        setDefaultWH();
        setDefaultXY();
        setVisible(false);
    }

    public void isTouch(float x,float y) { // проверяем коснулись ли мы курсора
        if ((x>=40 && x<=360) &&(y>=720 && y<=1040)) {
            isTouchLeft = true;
        }
    }

    public void setDefaultWH(){// задаётся ширина,высота и радиус большого круга
        setWidth(320);
        setHeight(320);
        rad = 160;

    }
    public void setDefaultXY(){//задаётся левый нижний угол квадрата для большого круга
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
        if(MainGame.seconds==0){//если игра завешается, то курсор джойстика возвращается в начальное положение
            batch.draw(circleCur,this.getX()+rad-CURSOR_RADIUS,
                    this.getY()+rad - CURSOR_RADIUS,
                    2*CURSOR_RADIUS,
                    2*CURSOR_RADIUS);
        }
        else if (isTouchLeft){// если коснулись джойстика, то отрисовываем курсор в зависимости от расположения пальца, но не выходя из области большого круга
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
    // метод, которые не даёт курсору выйти за пределы большого круга
    public void changeCursor(float x, float y){
        float dx = x - rad-40;
        float dy =720-(y - rad);
        float length = (float) Math.sqrt(dx*dx+dy*dy);
        if (length<rad){
            this.curX = dx;
            this.curY =dy;
        }
        else{
            float c = rad/length;
            this.curX = c*dx;
            this.curY = c*dy;
        }

    }
    // метод, которые считает угол отклонения курсора от начального положения
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
            checkAngleLeft = true;
        }
        else {
            checkAngleLeft = false;
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (screenX<1000 && screenY>700 && MainGame.seconds>0) {
            isTouch(screenX, screenY);
            if (isTouchLeft ) {
                changeCursor(screenX, screenY);
                setAngle();
                setVisible(true);
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (screenX<1000 && MainGame.seconds>0 ) {
            isTouchLeft = false;
            angleLeft = 0;
            setVisible(false);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (screenX<1000 && screenY>700 && MainGame.seconds>0) {
            changeCursor(screenX, screenY);
            setAngle();
        }
        return false;
    }
}
