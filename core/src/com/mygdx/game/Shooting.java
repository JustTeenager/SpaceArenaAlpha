package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.mygdx.game.ArenaGame.pl1;
import static com.mygdx.game.ArenaGame.plat;

public class Shooting extends ActorObj {

    private Texture shoot;
    public boolean isOut;//переменная для проверки пули в области экрана

    private Rectangle rectangle;
    private float velocity;
    private Vector2 direction;
    private Sprite sprite;
    public Shooting(float x, float y, Stage s,Vector2 direction,float velocity){
        super(x,y,s);
        setPosition(x,y);
        this.direction = new Vector2(direction);
        this.velocity = velocity;
        shoot = new Texture("ef_2_00000.png");

        sprite = new Sprite(shoot);//поворачиваем пулю на угол, равный углу курсора
        sprite.setOriginCenter();
        sprite.rotate((float)JoystickRight.angleRight);
        sprite.setPosition(getX(),getY());

        positionGun(pl1);
        rectangle = new Rectangle(getX(), getY(),getWidth(),getHeight());
        s.addActor(this);

    }
    @Override
    public void collapse(Player player) {
        if (player.hp>0) {
            if (this.rectangle.overlaps(player.rectangle)) {
                this.setVisible(false);
                player.hp -= 1;
            }
        }
        else player.killed=true;

        for (int i=0;i<plat.length;i++){
            if (rectangle.overlaps(plat[i].rect)){
                this.setVisible(false);
                setPosition(7000,7000);
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float x = getX() + direction.x * velocity * Gdx.graphics.getDeltaTime();
        sprite.setX(x);
        float y = getY() + direction.y * velocity * Gdx.graphics.getDeltaTime();
        sprite.setY(y);
        sprite.draw(batch);
    }

    @Override
    public void update() {
        float x = getX() + direction.x * velocity * Gdx.graphics.getDeltaTime();
        setX(x);
        float y = getY() + direction.y * velocity * Gdx.graphics.getDeltaTime();
        setY(y);
        rectangle.setPosition(x,y);
        isOut = x > 3420 || x < -1500 || y > 3580 || y < -1500;//надо будет поставить размры нашего экрана
    }



    @Override
    public void platformReact(Platform pl) {}

    public Texture getShoot() {
        return shoot;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getDirection() {
        return direction;
    }
}

