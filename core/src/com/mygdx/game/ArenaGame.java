package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;

public class ArenaGame extends ScreenAdapter {

	private Texture backTxt;
	Music gameMusic;

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


	public static Platform[] plat;
	private Texture txtplat;
	private Texture txtplatFloor;

	private GameHUD hud;
	private Stage hudStage;
	private Texture jumpbtn;

	private CoordBox coordBox;

	public ArenaGame () {
		try {
			ClientClass.startClient();
			ClientClass.sendBox(new CoordBox(0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		batch = new SpriteBatch();

		backTxt=new Texture("backGame.jpg");
		gameMusic=Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
		gameMusic.setLooping(true);
		gameMusic.play();

		viewport=new ExtendViewport(MainGame.WORLD_SIZE_X,MainGame.WORLD_SIZE_Y);

		playerStage=new Stage(viewport);
		hudStage = new Stage();

		pl1=new Player(0,50);
		pl1.setID(0);
		pl1.setAnim(pl1.getTextureArray_aim_player_2(),MainGame.Aim_2);
		pl1.setAnim(pl1.getTextureArray_move_player_2(),MainGame.RunShoot_2);
		pl1.setAnim(pl1.getTextureArray_jump_player_2(),MainGame.JumpShoot_2);

		pl2=new Player(1000,50);
		pl2.setID(1000);
		pl2.setAnim(pl2.getTextureArray_aim_player_4(),MainGame.Aim_4);
		pl2.setAnim(pl2.getTextureArray_move_player_4(),MainGame.RunShoot_4);
		pl2.setAnim(pl2.getTextureArray_jump_player_4(),MainGame.JumpShoot_4);


		switch (MainGame.getPlayerIdentify()){
			case 1:{
				CURRENT_PLAYER=pl1;
				playerStage.addActor(CURRENT_PLAYER);
				CURRENT_PLAYER.setX(pl1.getID());
				CURRENT_PLAYER.setY(50);

				ENEMY=pl2;
				ENEMY.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());
				playerStage.addActor(ENEMY);
				ENEMY.setX(pl2.getID());
				ENEMY.setY(50);

			}break;
			case 2:{
				CURRENT_PLAYER=pl2;
				playerStage.addActor(CURRENT_PLAYER);
				CURRENT_PLAYER.setX(pl2.getID());
				CURRENT_PLAYER.setY(50);

				ENEMY=pl1;
				playerStage.addActor(ENEMY);
				ENEMY.setX(pl1.getID());
				ENEMY.setY(50);
				ENEMY.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());

			}break;
			default:System.out.println("THATS WORNG IDENTITY NUMBER!");
		}
		CURRENT_PLAYER.setName("CURRENT_NAME");
		ENEMY.setName("ENEMY");







		shootings = new ArrayList<>();

		circle = new Texture("circle.png");
		circleCur = new Texture("circle.png");
		joystickLeft = new JoystickLeft(circle,circleCur);
		joystickRight = new JoystickRight(circle,circleCur);

		hudStage.addActor(joystickRight);
		hudStage.addActor(joystickLeft);

		txtplat=new Texture("SciFiPlatformset.png");
		txtplatFloor=new Texture("SciFiPlatformsetFloor.png");
		plat= new Platform[]{new Platform(150, 150,txtplat, playerStage),new Platform(550, 200,txtplat, playerStage)};

		inputMultiplexer = new InputMultiplexer();

		hud=new GameHUD(hudStage,viewport,CURRENT_PLAYER);//для второго игрока надо сделать отдельная сцена
		jumpbtn=new Texture("circle.png");
		hud.setjumpButton(1700,450,"jumpbutton",100,100,jumpbtn);
		playerStage.addActor(CURRENT_PLAYER);
		playerStage.addActor(ENEMY);
	}


	@Override
	public void show() {
		chaseCam = new ChaseCam(viewport.getCamera(), CURRENT_PLAYER); }//должна быть для второго игрока

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		coordBox=new CoordBox(MainGame.getPlayerIdentify());
		ClientClass.sendBox(coordBox);

		inputMultiplexer.addProcessor(joystickLeft);
		inputMultiplexer.addProcessor(joystickRight);
		inputMultiplexer.addProcessor(hud);
		Gdx.input.setInputProcessor(inputMultiplexer);
		playerStage.act(delta);
		CURRENT_PLAYER.update();
		ENEMY.update();
		//pl1.update();
		//pl2.update();
		//coordBox= new CoordBox(0,pl1.position,pl1.anim.getKeyFrame(delta).getTexture(),pl1.jumpState,pl1.hp,shootings);//золотая коробка
		joystickRight.checkCreateBullet();

		//сделать иф просмотра стреляет первый игрок или второй
		for (int i=0;i<shootings.size();i++){
			shootings.get(i).update();
			//shootings.get(i).collapse(pl1);
			shootings.get(i).collapse(ENEMY);
			if (shootings.get(i).isOut || !shootings.get(i).isVisible()){//удаление той пули, которая выышла за экран
				shootings.remove(i);
				//shootings.get(i).getShoot().dispose();
			}
		}
		hud.setColor(1,1,1,0.5f);
		chaseCam.update();
		viewport.apply();
		batch.begin();
		batch.draw(backTxt,0,0,2320,1080);//2020?
		batch.end();
		playerStage.draw();
		hudStage.draw();

	}

	@Override
	public void resize(int width, int height) {

	}

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
		//pl1.dispose();
		//pl2.dispose();

		circle.dispose();
		circleCur.dispose();

		txtplat.dispose();

		hudStage.dispose();
		jumpbtn.dispose();
		//joystick.dispose();
		//s.dispose();
	}
}