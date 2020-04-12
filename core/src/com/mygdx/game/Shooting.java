package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import static com.mygdx.game.ArenaGame.CURRENT_PLAYER;
import static com.mygdx.game.ArenaGame.pl1;
import static com.mygdx.game.ArenaGame.plat;
import static com.mygdx.game.ArenaGame.playerStage;

public class Shooting extends ActorObj {
    public static ArrayList<Double> anglesCurrentPlayer=new ArrayList<>();
    //public static ArrayList<Double> anglesEnemy=new ArrayList<>();

    public static Texture shoot = new Texture("ef_2_00000.png");
    public static Texture shootEnemy=new Texture("ef_2_00000_4.png");
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

       if(CURRENT_PLAYER.getID()==1) sprite = new Sprite(shoot);//поворачиваем пулю на угол, равный углу курсора
        else sprite=new Sprite(shootEnemy);
        sprite.setOriginCenter();
        sprite.rotate((float)JoystickRight.angleRight);
        sprite.setPosition(getX(),getY());

        positionGun(CURRENT_PLAYER);
        rectangle = new Rectangle(getX(), getY(),getWidth(),getHeight());
        s.addActor(this);

    }

    Shooting(Sprite sprite,Stage stage, double angle, Rectangle rectangle){
        super(sprite.getX(),sprite.getY(),stage);
        this.sprite = new Sprite(sprite);
        sprite.setOriginCenter();
        sprite.rotate((float) angle);
        sprite.setPosition(getX(),getY());
        this.rectangle=rectangle;
        stage.addActor(this);

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

    public static ArrayList<Vector2> getBulletsDirection(ArrayList<Shooting> shootings){
        ArrayList<Vector2> positionsBullets = new ArrayList<>();
        for (Shooting shoot: shootings){
            positionsBullets.add(shoot.direction);
        }
        return positionsBullets;
    }

    public static ArrayList<Rectangle> getBulletsRectangle(ArrayList<Shooting> shootings){
        ArrayList<Rectangle> rectanglesBullets = new ArrayList<>();
        for (Shooting shoot:shootings){
            rectanglesBullets.add(shoot.rectangle);
        }
        return rectanglesBullets;
    }

    public static ArrayList<Shooting> createEnemyShootingArray(CoordBox coordBox){
        ArrayList<Vector2> arrayList = coordBox.BbulletsPosition;
        ArrayList<Shooting> arrayList1 = new ArrayList<>();
        for(int i=0;i<arrayList.size();i++){
            Sprite sprite1 = new Sprite((MainGame.getPlayerIdentify()==1)?shoot:shootEnemy,(int)arrayList.get(i).x,(int)arrayList.get(i).y,shoot.getWidth(),shoot.getHeight());
            Shooting shooting = new Shooting(sprite1,playerStage,coordBox.Bangles.get(i),coordBox.BrectangleShoot.get(i));
            arrayList1.add(shooting);
        }
        return arrayList1;
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

