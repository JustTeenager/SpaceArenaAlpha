package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static com.mygdx.game.ArenaGame.CURRENT_PLAYER;
import static com.mygdx.game.ArenaGame.ENEMY;
import static com.mygdx.game.ArenaGame.plat;
import static com.mygdx.game.ArenaGame.playerStage;

public class Shooting extends ActorObj {

    public static Texture shoot = new Texture("ef_2_00000.png");
    public static Texture shootEnemy=new Texture("ef_2_00000_4.png");
    public boolean isOut;//переменная для проверки пули в области экрана

    private Rectangle rectangle;
    private float velocity;
    private Vector2 direction;
    private Sprite sprite;
    static Sound shootSound2=Gdx.audio.newSound(Gdx.files.internal("shootSound_2.wav"));
    static Sound shootSound4=Gdx.audio.newSound(Gdx.files.internal("shootSound_4.wav"));
    public Shooting(float x, float y, Stage s,Vector2 direction,float velocity){
        super(x,y,s);
        setPosition(x,y);
        this.direction = new Vector2(direction);
        this.velocity = velocity;

       if(CURRENT_PLAYER.getID()==0) sprite = new Sprite(shoot);
        else sprite=new Sprite(shootEnemy);
        sprite.setOriginCenter();
        sprite.rotate((float)JoystickRight.angleRight);//поворачиваем пулю на угол, равный углу курсора
        sprite.setPosition(getX(),getY());

        positionGun(CURRENT_PLAYER);
        rectangle = new Rectangle(getX(), getY(),getWidth(),getHeight());
        s.addActor(this);

        if (ArenaGame.CURRENT_PLAYER.getID()==0) Shooting.shootSound2.play(MainGame.volume);
        else Shooting.shootSound4.play(MainGame.volume);

    }

    public Shooting(float x, float y, Stage s,Vector2 direction,float velocity,double angle){
        super(x,y,s);
        setPosition(x,y);
        this.direction = new Vector2(direction);
        this.velocity = velocity;

        if(CURRENT_PLAYER.getID()==1000) sprite = new Sprite(shoot);
        else sprite=new Sprite(shootEnemy);
        positionGun(ENEMY);
        sprite.setOriginCenter();
        sprite.rotate((float) angle);//поворачиваем пулю на угол, равный углу курсора
        sprite.setPosition(getX(),getY());
        rectangle = new Rectangle(getX(), getY(),getWidth(),getHeight());
        s.addActor(this);
        if (ArenaGame.CURRENT_PLAYER.getID()==1000) shootSound2.play(MainGame.volume);
        else shootSound4.play(MainGame.volume);

    }


    @Override
    public void collapse(Player player) {
        if (player.hp>0) {
            if (this.rectangle.overlaps(player.rectangle)) {
                this.setVisible(false);
                player.hp -= 1;
            }
        }
        if (player.hp<=0 && !player.killed) {
            player.killed=true;
            if (player.getID()==0) {
                player.useAnim(0.1f,false,player.getTextureArray_dead_player_2());
                player.setAnimationNum(14);
            }
            else {
                player.useAnim(0.1f, false, player.getTextureArray_dead_player_4());
                player.setAnimationNum(24);
            }
        }

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
        isOut = x > 1050 || x < -850 || y > 1000 || y < -20;//надо будет поставить размры нашего экрана
    }

    public static void addEnemyShootingArray(CoordBox coordBox){
        Shooting enemySh=new Shooting(coordBox.BpositionPlayer.x,coordBox.BpositionPlayer.y, playerStage,coordBox.BbulletsPosition,MainGame.VELOCITY_BULLETS,coordBox.Bangles);
        ArenaGame.shootingsEnemy.add(enemySh);
    }


    @Override
    public void platformReact(Platform pl) {}

    public Rectangle getRectangle() {
        return rectangle;
    }

    public Vector2 getDirection() {
        return direction;
    }
}

