package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

public abstract class ActorObj extends Group {
    Texture textTEMP;
    Animation<TextureRegion> anim;

    protected Animation<TextureRegion> animation;
    public ActorObj(float x, float y, Stage s){
        super();
        setPosition(x,y);
        s.addActor(this);
    }

    protected ActorObj() {
    }

    public void setAnim(Array<TextureRegion> array,String[] str) {
        for (int i = 0; i < str.length; i++) {
            textTEMP = new Texture(str[i]);
            textTEMP.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            array.add(new TextureRegion(textTEMP));
        }
    }
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
    public void positionGunPL1(ActorObj other)
    {
            if (other instanceof Player && ((Player) other).flip)
                centerAtPosition(other.getX() - other.getWidth() / 2 + 20, other.getY() + other.getHeight() / 2 + 4);
            else
                centerAtPosition(other.getX() + other.getWidth() / 2 + 20, other.getY() + other.getHeight() / 2 + 4);

    }
    public void positionGunPL2(ActorObj other)
    {

            if (other instanceof Player && ((Player) other).flip)
                centerAtPosition(other.getX() - other.getWidth() / 2 + 25, other.getY() + other.getHeight() / 2-15);
            else
                centerAtPosition(other.getX() + other.getWidth() / 2 + 30, other.getY() + other.getHeight() / 2 -15);
    }

    public abstract void platformReact(Platform pl);
}

