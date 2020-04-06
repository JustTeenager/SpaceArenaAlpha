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
		batch = new SpriteBatch();

		backTxt=new Texture("backGame.jpg");
		gameMusic=Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));
		gameMusic.setLooping(true);
		gameMusic.play();

		viewport=new ExtendViewport(MainGame.WORLD_SIZE_X,MainGame.WORLD_SIZE_Y);

		playerStage=new Stage(viewport);
		hudStage = new Stage();

		pl1=new Player(0,50,playerStage);
		pl1.setName("pl1");
		pl1.setAnim(pl1.getTextureArray_aim_player_2(),MainGame.Aim_2);
		pl1.setAnim(pl1.getTextureArray_move_player_2(),MainGame.RunShoot_2);
		pl1.setAnim(pl1.getTextureArray_jump_player_2(),MainGame.JumpShoot_2);

		pl2=new Player(1000,50,playerStage);
		pl2.setName("pl2");
		pl2.setAnim(pl2.getTextureArray_aim_player_4(),MainGame.Aim_4);
		pl2.setAnim(pl2.getTextureArray_move_player_4(),MainGame.RunShoot_4);
		pl2.setAnim(pl2.getTextureArray_jump_player_4(),MainGame.JumpShoot_4);
		pl2.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());

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

		hud=new GameHUD(hudStage,viewport,pl1);//для второго игрока надо сделать отдельная сцена
		jumpbtn=new Texture("circle.png");
		hud.setjumpButton(1700,450,"jumpbutton",100,100,jumpbtn);

		try {
			ClientClass.startClient();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	@Override
	public void show() {
		chaseCam = new ChaseCam(viewport.getCamera(), pl1); }//должна быть для второго игрока

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		inputMultiplexer.addProcessor(joystickLeft);
		inputMultiplexer.addProcessor(joystickRight);
		inputMultiplexer.addProcessor(hud);
		Gdx.input.setInputProcessor(inputMultiplexer);
		playerStage.act(delta);
		pl1.update();

		//pl2.update();
		//coordBox= new CoordBox(0,pl1.position,pl1.anim.getKeyFrame(delta).getTexture(),pl1.jumpState,pl1.hp,shootings);//золотая коробка
		joystickRight.checkCreateBullet();

		//сделать иф просмотра стреляет первый игрок или второй
		for (int i=0;i<shootings.size();i++){
			shootings.get(i).update();
			//shootings.get(i).collapse(pl1);
			shootings.get(i).collapse(pl2);
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
		pl1.dispose();
		pl2.dispose();

		circle.dispose();
		circleCur.dispose();

		txtplat.dispose();

		hudStage.dispose();
		jumpbtn.dispose();
		//joystick.dispose();
		//s.dispose();
	}
}