package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;


public class ArenaGame extends ScreenAdapter {

	private MainGame game;

	private Texture backTxt;
	public static Music gameMusic;

	private ShapeRenderer shapeRenderer=new ShapeRenderer();

	private InputMultiplexer inputMultiplexer;
	private SpriteBatch batch;
	private ExtendViewport viewport;

	public static Stage playerStage;
	public static Player pl1;
	public static Player pl2;
	public static Player CURRENT_PLAYER;
	public static Player ENEMY;

	public static ChaseCam chaseCam;

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

	private HpSmall hpSmall;

	private Texture txtAmmunition;
	private Ammunition[] ammunitions;

	private GameHUD hud;
	private Stage hudStage;
	private Texture jumpbtn;
	private Texture settingsbtn;

	private CoordBox coordBox;


	public ArenaGame (final MainGame game) {
		this.game=game;
		CURRENT_PLAYER=new Player(-100,50);
		ENEMY=new Player(700,50);
		shootings = new ArrayList<>();
		shootingsEnemy = new ArrayList<>();
		MainGame.setPlayerIdentify(-1);
		try {
			ClientClass.sendBox(new CoordBox(-1));
			ClientClass.sendBox(new PlayerNameBox(MainGame.current_player_name));
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

		pl1=new Player(-100,50);
		pl1.setID(-100);
		pl1.setAnim(pl1.getTextureArray_aim_player_2(),MainGame.Aim_2);
		pl1.setAnim(pl1.getTextureArray_move_player_2(),MainGame.RunShoot_2);
		pl1.setAnim(pl1.getTextureArray_jump_player_2(),MainGame.JumpShoot_2);
		pl1.setAnim(pl1.getTextureArray_dead_player_2(),MainGame.Dead_2);

		pl2=new Player(700,50);
		pl2.setID(700);
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

		circle = new Texture("analog_base.png");
		circleCur = new Texture("analog_button.png");
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
				new Platform(1040,-26,txtplatCornerRight,playerStage), new Platform(-935,-35,txtplatCornerLeft,playerStage),
				new Platform(1030,-60+80,txtplatRightWall,playerStage),new Platform(-915,-60+80,txtplatLeftWall,playerStage),
				new Platform(1030,-80+210+80,txtplatRightWall,playerStage),new Platform(-915,-80+210+80,txtplatLeftWall,playerStage),
				new Platform(1030,-100+210+210+80,txtplatRightWall,playerStage),new Platform(-915,-100+210+210+80,txtplatLeftWall,playerStage),
				new Platform(1030,-120+210+210+210+80,txtplatRightWall,playerStage),new Platform(-915,-120+210+210+210+80,txtplatLeftWall,playerStage),
				new Platform(1030,-140+210+210+210+210+80,txtplatRightWall,playerStage),new Platform(-915,-140+210+210+210+210+80,txtplatLeftWall,playerStage),
				new Platform(1040,-141+210+210+210+210+250,txtplatCornerRight,playerStage), new Platform(-935,-151+210+210+210+210+250,txtplatCornerLeft,playerStage),
				new Platform(-850,-140+210+210+210+210+250,txtplatFloor,playerStage),new Platform(-640,-140+210+210+210+210+250,txtplatFloor,playerStage),new Platform(-430,-140+210+210+210+210+250,txtplatFloor,playerStage),
				new Platform(-220,-140+210+210+210+210+250,txtplatFloor,playerStage),new Platform(-10,-140+210+210+210+210+250,txtplatFloor,playerStage), new Platform(200,-140+210+210+210+210+250,txtplatFloor,playerStage),
				new Platform( 410, -140+210+210+210+210+250,txtplatFloor,playerStage),new Platform(620,-140+210+210+210+210+250,txtplatFloor,playerStage),new Platform(830,-140+210+210+210+210+250,txtplatFloor,playerStage),
				new Platform( 100, 150,txtplat, playerStage),new Platform(480, 280,txtplat, playerStage),new Platform(100, 450,txtplat, playerStage),
				new Platform(-250, 280,txtplat, playerStage),new Platform(700, 450,txtplat, playerStage),new Platform(-560, 450,txtplat, playerStage)
		};

		hpSmall=new HpSmall(playerStage);

		txtAmmunition = new Texture("ammunition.png");
		ammunitions = new Ammunition[]{new Ammunition(txtAmmunition,790,550,playerStage),new Ammunition(txtAmmunition,-500,550,playerStage),
				new Ammunition(txtAmmunition,190,550,playerStage)};

		inputMultiplexer = new InputMultiplexer();

		hud=new GameHUD(hudStage,viewport,CURRENT_PLAYER);
		jumpbtn=new Texture("analog_button.png");
		settingsbtn=new Texture("Options Icon.png");
		hud.setjumpButton(1700,450,"jumpbutton",120,120,jumpbtn);
		hud.setSettingsButton(1700,850,"settingsbutton",160,160,settingsbtn);
		hud.setScore();
		hud.setSettings(2.7f);
		FinalDialog finalDialog=new FinalDialog(2f,hudStage,game);
		finalDialog.setVisible(false);
		hud.setFinalDialog(finalDialog);
		hud.setTimePanel();
		hud.setHpPanel();

		playerStage.addActor(CURRENT_PLAYER);
		playerStage.addActor(ENEMY);
		CURRENT_PLAYER.setStartPosition(new Vector2(CURRENT_PLAYER.getX(),CURRENT_PLAYER.getY()));
		ENEMY.setStartPosition(new Vector2(ENEMY.getX(),ENEMY.getY()));

		setStartSettings();

	}


	@Override
	public void show() {
		chaseCam = new ChaseCam(viewport.getCamera(), CURRENT_PLAYER); }

	@Override
	public void render (float delta) {
		if ((!ClientClass.isConnected() || ClientClass.playerNUM==-1) && MainGame.seconds>0){
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
		try {
			playerStage.act(delta);
		} catch (Exception e) {
			System.out.println("RENDER EXCEPTION!!!");
		}
		joystickRight.checkCreateBullet();

		for (int i=0;i<shootingsEnemy.size();i++){
			shootingsEnemy.get(i).update();
			shootingsEnemy.get(i).collapse(CURRENT_PLAYER);
			if (ENEMY.hp<=0 || CURRENT_PLAYER.hp<=0)shootingsEnemy.get(i).setVisible(false);
			if (shootingsEnemy.get(i).isOut || !shootingsEnemy.get(i).isVisible()){//удаление той пули, которая выышла за экран
				shootingsEnemy.remove(i);
			}
		}

		for (int i=0;i<shootings.size();i++){
			shootings.get(i).update();
			shootings.get(i).collapse(ENEMY);
			if (ENEMY.hp<=0 || CURRENT_PLAYER.hp<=0)shootings.get(i).setVisible(false);
			if (shootings.get(i).isOut || !shootings.get(i).isVisible()){//удаление той пули, которая выышла за экран
				shootings.remove(i);
			}
		}

		if (CURRENT_PLAYER.hp<=0){
			if (!MainGame.flag ){
				GameHUD.scoreLogs.setVisible(true);
				MainGame.enemy_score++;
				MainGame.timeFromLastKill=MainGame.seconds;
				MainGame.flag=true;
				MessageBox box=new MessageBox();
				box.message=true;
				ClientClass.sendBox(box);
			}

			if (MainGame.timeFromLastKill - MainGame.seconds >= 3) {
				GameHUD.scoreLogs.setVisible(false);
				MainGame.flag=false;
				MainGame.timeFromLastKill = -1;
				CURRENT_PLAYER.setNextRound();
			}
		}

		else if (MainGame.needEnemyReanimate) {

			if (!MainGame.flag){
				GameHUD.scoreLogs.setVisible(true);
				MainGame.current_player_score++;
				MainGame.timeFromLastKill=MainGame.seconds;
				MainGame.flag=true;
			}

			if (MainGame.timeFromLastKill - MainGame.seconds >= 3) {
				GameHUD.scoreLogs.setVisible(false);
				MainGame.needEnemyReanimate=false;
				MainGame.flag=false;
				MainGame.timeFromLastKill = -1;
				CURRENT_PLAYER.setNextRound();
				ENEMY.setNextRound();
			}
		}

		for (Ammunition ammunition: ammunitions){
			ammunition.update();
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
		hud.drawHpPanel(delta,batch);
		batch.end();
		hudStage.draw();
		if (MainGame.seconds==0){
			if (MainGame.current_player_score>MainGame.enemy_score){
				FinalDialog.winner=MainGame.current_player_name+" was victorious!";
			}
			else if(MainGame.current_player_score==MainGame.enemy_score){
				FinalDialog.winner="Draw!!!";
			}
			else{
				FinalDialog.winner=MainGame.enemy_name+" was victorious!";
			}
			hud.getFinalDialog().setVisible(true);
			try {
				ClientClass.close();
				hud.getBackButtonFinal().setVisible(true);
			}catch (Exception e){}
		}
		//отладка платформ
		/*Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer.setProjectionMatrix(chaseCam.camera.combined);
		shapeRenderer.setColor(0, 1, 1, 0.5f);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(CURRENT_PLAYER.rectangle.getX(),CURRENT_PLAYER.rectangle.getY(),CURRENT_PLAYER.rectangle.getWidth(), CURRENT_PLAYER.rectangle.getHeight());
		for (Platform plat:plat){
			shapeRenderer.rect(plat.rect.getX(),plat.rect.getY(),plat.rect.getWidth(), plat.rect.getHeight());
		}
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);*/
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
	}

	public void setStartSettings(){
		MainGame.seconds=MainGame.TIME_SECONDS;
		MainGame.time="-1";
		MainGame.isShooted=false;
		MainGame.jumped=false;
		MainGame.isSettingsDialogOpened=false;
		MainGame.current_player_score=0;
		MainGame.enemy_score=0;
		CURRENT_PLAYER.amountBullets=MainGame.AMOUNT_BULLETS;
		JoystickLeft.CheckAngleLeft=true;
		JoystickLeft.isTouchLeft=false;
		JoystickLeft.angleLeft=0;
		JoystickRight.CheckAngleRight=true;
		JoystickRight.angleRight=0;
		JoystickRight.isTouchRight=false;
		pl1.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());
		pl2.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());
		MainGame.bulletsDamage=5;
		MainGame.timeFromLastKill=-1;
		MainGame.flag=false;
	}
}