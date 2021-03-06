package com.mygdx.game.GameObjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

//Базовый класс обьектов "взаимодействия" - пуль,игроков,патронов
public abstract class ActorObj extends Group {
    private Texture textTEMP;
    private Animation<TextureRegion> anim;

    //анимация "на выходе"
    protected Animation<TextureRegion> animation;

    public ActorObj(float x, float y, Stage s){
        super();
        setPosition(x,y);
        s.addActor(this);
    }

    protected ActorObj() {
    }

    //создание анимации из набора кадров
    public void setAnim(Array<TextureRegion> array,String[] str) {
        for (int i = 0; i < str.length; i++) {
            textTEMP = new Texture(str[i]);
            textTEMP.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            array.add(new TextureRegion(textTEMP));
        }
    }
    //функция использования соответсвующей анимации
    public void useAnim(float frameT,boolean loop,Array<TextureRegion> array){
        anim = new Animation<>(frameT, array);
        if (loop)
            anim.setPlayMode(Animation.PlayMode.LOOP);
        else
            anim.setPlayMode(Animation.PlayMode.NORMAL);

        animation = anim;
    }
    public abstract void collapse(Player player);

    public abstract void update();

    public void centerAtPosition(float x, float y)
    {
        setPosition( x - getWidth()/2 , y - getHeight()/2 );
    }
    //считает координаты оружия
    public void positionGun(ActorObj other) {
            if (other instanceof Player && ((Player) other).flip)
                centerAtPosition(other.getX() - other.getWidth() / 2 + 20, other.getY() + other.getHeight() / 2 -5);
            else
                centerAtPosition(other.getX() + other.getWidth() / 2 + 20, other.getY() + other.getHeight() / 2 -5);
    }

    public abstract void platformReact(Platform pl);
}

