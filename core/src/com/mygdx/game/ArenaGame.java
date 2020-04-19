package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;
import java.util.Timer;

public class ArenaGame extends ScreenAdapter {
	MainGame game;

	private Texture backTxt;
	public static Music gameMusic;

	private InputMultiplexer inputMultiplexer;
	private SpriteBatch batch;
	private ExtendViewport viewport;

	public static Stage playerStage;
	public static Player pl1;
	public static Player pl2;
	public static Player CURRENT_PLAYER;
	public static Player ENEMY;

	private ChaseCam chaseCam;

	private Texture circle;
	private Texture circleCur;
	private JoystickLeft joystickLeft;
	private JoystickRight joystickRight;

	public static ArrayList<Shooting> shootings;
	public static ArrayList<Shooting> shootingsEnemy;


	public static Platform[] plat;
	private Texture txtplat;
	private Texture txtplatFloor;
	private Texture txtplatLeftWall;
	private Texture txtplatRightWall;
	private Texture txtplatRoof;
	private Texture txtplatCornerLeft;
	private Texture txtplatCornerRight;

	private GameHUD hud;
	private Stage hudStage;
	private Texture jumpbtn;
	private Texture settingsbtn;

	private CoordBox coordBox;

	//private Timer timer;

	public ArenaGame (final MainGame game) {
		this.game=game;

		CURRENT_PLAYER=new Player(0,50);
		ENEMY=new Player(1000,50);
        shootings = new ArrayList<>();
        shootingsEnemy = new ArrayList<>();
		try {
			ClientClass.sendBox(new CoordBox(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		batch = new SpriteBatch();

		backTxt=new Texture("backGame.jpg");
		gameMusic=Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
		gameMusic.setLooping(true);
		gameMusic.setVolume(MainGame.volume);
		gameMusic.play();

		viewport=new ExtendViewport(MainGame.WORLD_SIZE_X,MainGame.WORLD_SIZE_Y);

		playerStage=new Stage(viewport);
		hudStage = new Stage();

		pl1=new Player(0,50);
		pl1.setID(0);
		pl1.setAnim(pl1.getTextureArray_aim_player_2(),MainGame.Aim_2);
		pl1.setAnim(pl1.getTextureArray_move_player_2(),MainGame.RunShoot_2);
		pl1.setAnim(pl1.getTextureArray_jump_player_2(),MainGame.JumpShoot_2);
		pl1.setAnim(pl1.getTextureArray_dead_player_2(),MainGame.Dead_2);

		pl2=new Player(1000,50);
		pl2.setID(1000);
		pl2.setAnim(pl2.getTextureArray_aim_player_4(),MainGame.Aim_4);
		pl2.setAnim(pl2.getTextureArray_move_player_4(),MainGame.RunShoot_4);
		pl2.setAnim(pl2.getTextureArray_jump_player_4(),MainGame.JumpShoot_4);
		pl2.setAnim(pl2.getTextureArray_dead_player_4(),MainGame.Dead_4);

		switch (MainGame.getPlayerIdentify()){
			case 1:{
				CURRENT_PLAYER=pl1;
				playerStage.addActor(CURRENT_PLAYER);
				CURRENT_PLAYER.setX(pl1.getID());
				CURRENT_PLAYER.setY(50);
				CURRENT_PLAYER.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());
				CURRENT_PLAYER.setAnimationNum(11);

				ENEMY=pl2;
				playerStage.addActor(ENEMY);
				ENEMY.setX(pl2.getID());
				ENEMY.setY(50);
			    ENEMY.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());
			    ENEMY.setAnimationNum(21);

				CURRENT_PLAYER.setName("CURRENT_PLAYER");
				CURRENT_PLAYER.setID(pl1.getID());
				ENEMY.setName("ENEMY");
				ENEMY.setID(pl2.getID());

			}break;
			/*case 2:{
				CURRENT_PLAYER=new Player(1000,50);
				CURRENT_PLAYER=pl2;
				playerStage.addActor(CURRENT_PLAYER);
				CURRENT_PLAYER.setX(pl2.getID());
				CURRENT_PLAYER.setY(50);
				CURRENT_PLAYER.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());

				ENEMY=new Player(0,50);
				ENEMY=pl1;
				playerStage.addActor(ENEMY);
				ENEMY.setX(pl1.getID());
				ENEMY.setY(50);
			    ENEMY.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());//Стандартная анимация

				CURRENT_PLAYER.setName("CURRENT_PLAYER");
				CURRENT_PLAYER.setID(pl2.getID());
				ENEMY.setName("ENEMY");
				ENEMY.setID(pl1.getID());

			}break;*/
			default:{
				CURRENT_PLAYER=pl2;
				playerStage.addActor(CURRENT_PLAYER);
				CURRENT_PLAYER.setX(pl2.getID());
				CURRENT_PLAYER.setY(50);
				CURRENT_PLAYER.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());
				CURRENT_PLAYER.setAnimationNum(21);

				ENEMY=pl1;
				playerStage.addActor(ENEMY);
				ENEMY.setX(pl1.getID());
				ENEMY.setY(50);
				ENEMY.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());
				ENEMY.setAnimationNum(11);

				CURRENT_PLAYER.setName("CURRENT_PLAYER");
				CURRENT_PLAYER.setID(pl2.getID());
				ENEMY.setName("ENEMY");
				ENEMY.setID(pl1.getID());
			}
		}
		ENEMY.position=new Vector2(ENEMY.getID(),50);
		CURRENT_PLAYER.position=new Vector2(CURRENT_PLAYER.getID(),50);



		circle = new Texture("circle.png");
		circleCur = new Texture("circle.png");
		joystickLeft = new JoystickLeft(circle,circleCur);
		joystickRight = new JoystickRight(circle,circleCur);

		hudStage.addActor(joystickRight);
		hudStage.addActor(joystickLeft);

		txtplat=new Texture("SciFiPlatformset.png");
		txtplatFloor=new Texture("SciFiPlatformsetFloor.png");
		txtplatLeftWall=new Texture("LeftWall.png");
		txtplatRightWall=new Texture("RightWall.png");
		txtplatRoof=new Texture("Roof.png");
		txtplatCornerLeft=new Texture("SciFiPlatformset-5.png");
		txtplatCornerRight=new Texture("SciFiPlatformset-4.png");
		plat= new Platform[]{
				new Platform(-850,-25,txtplatFloor,playerStage),new Platform(-640,-25,txtplatFloor,playerStage),new Platform(-430,-25,txtplatFloor,playerStage),
				new Platform(-220,-25,txtplatFloor,playerStage),new Platform(-10,-25,txtplatFloor,playerStage), new Platform(200,-25,txtplatFloor,playerStage),
				new Platform(410,-25,txtplatFloor,playerStage),new Platform(620,-25,txtplatFloor,playerStage),new Platform(830,-25,txtplatFloor,playerStage),
				new Platform(1040,-25,txtplatCornerRight,playerStage), new Platform(-935,-43,txtplatCornerLeft,playerStage),
				new Platform(1030,-43,txtplatRightWall,playerStage),new Platform(-915,-43,txtplatLeftWall,playerStage),
				new Platform(1030,-43+210,txtplatRightWall,playerStage),new Platform(-915,-43+210,txtplatLeftWall,playerStage),
				new Platform(1030,-43+210+210,txtplatRightWall,playerStage),new Platform(-915,-43+210+210,txtplatLeftWall,playerStage),
				new Platform(1030,-43+210+210+210,txtplatRightWall,playerStage),new Platform(-915,-43+210+210+210,txtplatLeftWall,playerStage),
				new Platform(1040,-25+210+210+210+250,txtplatCornerRight,playerStage), new Platform(-935,-43+210+210+210+250,txtplatCornerLeft,playerStage),
				new Platform(-850,-25+210+210+210+250,txtplatFloor,playerStage),new Platform(-640,-25+210+210+210+250,txtplatFloor,playerStage),new Platform(-430,-25+210+210+210+250,txtplatFloor,playerStage),
				new Platform(-220,-25+210+210+210+250,txtplatFloor,playerStage),new Platform(-10,-25+210+210+210+250,txtplatFloor,playerStage), new Platform(200,-25+210+210+210+250,txtplatFloor,playerStage),
				new Platform(410,-25+210+210+210+250,txtplatFloor,playerStage),new Platform(620,-25+210+210+210+250,txtplatFloor,playerStage),new Platform(830,-25+210+210+210+250,txtplatFloor,playerStage),
				new Platform(150, 150,txtplat, playerStage),new Platform(550, 250,txtplat, playerStage),new Platform(830, 330,txtplat, playerStage),
				new Platform(-200, 200,txtplat, playerStage),new Platform(-870, 430,txtplat, playerStage),new Platform(-500, 300,txtplat, playerStage),
				new Platform(-750, 150,txtplat, playerStage)
		};

		inputMultiplexer = new InputMultiplexer();

		hud=new GameHUD(hudStage,viewport,CURRENT_PLAYER);
		jumpbtn=new Texture("circle.png");
		settingsbtn=new Texture("Options Icon.png");
		hud.setjumpButton(1700,450,"jumpbutton",100,100,jumpbtn);
		hud.setSettingsButton(1700,850,"settingsbutton",160,160,settingsbtn);
		hud.setSettings(2.7f);
		hud.setTimePanel();
		playerStage.addActor(CURRENT_PLAYER);
		playerStage.addActor(ENEMY);

	}


	@Override
	public void show() {
		chaseCam = new ChaseCam(viewport.getCamera(), CURRENT_PLAYER); }

	@Override
	public void render (float delta) {
		if (!ClientClass.isConnected() || ClientClass.playerNUM==-1){
			game.setScreen(new ConnectMenu(game));
			ClientClass.close();
		}


		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		inputMultiplexer.addProcessor(joystickLeft);
		inputMultiplexer.addProcessor(joystickRight);
		inputMultiplexer.addProcessor(hud);
		Gdx.input.setInputProcessor(inputMultiplexer);
		CURRENT_PLAYER.update();
		ENEMY.update();
		playerStage.act(delta);
		joystickRight.checkCreateBullet();

		for (int i=0;i<shootings.size();i++){
			shootings.get(i).update();
			shootings.get(i).collapse(ENEMY);
			if (shootings.get(i).isOut || !shootings.get(i).isVisible()){//удаление той пули, которая выышла за экран
				shootings.remove(i);
				//anglesCurrentPlayer.remove(i);
			}
		}
		for (int i=0;i<shootingsEnemy.size();i++){
			shootingsEnemy.get(i).update();
			shootingsEnemy.get(i).collapse(CURRENT_PLAYER);
			// shootings.get(i).collapse(ENEMY);
			if (shootingsEnemy.get(i).isOut || !shootingsEnemy.get(i).isVisible()){//удаление той пули, которая выышла за экран
				shootingsEnemy.remove(i);
				//anglesCurrentPlayer.remove(i);
			}
		}

		if (!MainGame.isShooted){
			coordBox = new CoordBox(MainGame.getPlayerIdentify(),CURRENT_PLAYER.position,CURRENT_PLAYER.getAnimationNum(),CURRENT_PLAYER.flip,CURRENT_PLAYER.rectangle,CURRENT_PLAYER.hp);
			ClientClass.sendBox(coordBox);
		}

		else if (MainGame.isShooted) {
			MainGame.isShooted=false;
			coordBox = new CoordBox(MainGame.getPlayerIdentify(), CURRENT_PLAYER.position, CURRENT_PLAYER.getAnimationNum(), CURRENT_PLAYER.flip, CURRENT_PLAYER.rectangle, CURRENT_PLAYER.hp,
					JoystickRight.shootTemp.getDirection(), (double) JoystickRight.shootTemp.getRotation(), JoystickRight.shootTemp.getRectangle());

			ClientClass.sendBox(coordBox);
		}
		hud.setColor(1,1,1,0.5f);
		chaseCam.update();
		viewport.apply();
		batch.begin();
		batch.draw(backTxt,0,0,2320,1080);//2020?
		batch.end();
		playerStage.draw();
		batch.begin();
		hud.drawTimer(delta,batch);
		batch.end();
		hudStage.draw();
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose () {
		backTxt.dispose();
		gameMusic.dispose();

		batch.dispose();
		playerStage.dispose();
		CURRENT_PLAYER.dispose();
		ENEMY.dispose();

		circle.dispose();
		circleCur.dispose();

		txtplat.dispose();

		hudStage.dispose();
		jumpbtn.dispose();
		try {
			hud.getTimer().cancel();
		}catch (Exception e){}
	}
}