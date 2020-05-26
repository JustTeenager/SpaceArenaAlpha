package com.mygdx.game.Core;

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
import com.mygdx.game.Client.ClientClass;
import com.mygdx.game.GameObjects.ChaseCam;
import com.mygdx.game.GameObjects.Shooting;
import com.mygdx.game.Menu.ConnectMenu;
import com.mygdx.game.Client.CoordBox;
import com.mygdx.game.Dialogs.FinalDialog;
import com.mygdx.game.HUD.GameHUD;
import com.mygdx.game.GameObjects.Ammunition;
import com.mygdx.game.GameObjects.Platform;
import com.mygdx.game.GameObjects.Player;
import com.mygdx.game.HUD.HpSmall;
import com.mygdx.game.HUD.JoystickLeft;
import com.mygdx.game.HUD.JoystickRight;
import com.mygdx.game.Client.MessageBox;
import com.mygdx.game.Client.PlayerNameBox;
import java.util.ArrayList;

//Класс непосредственно игры
public class ArenaGame extends ScreenAdapter {
	//"контекст" приложения,используется для смены экранов
	private MainGame game;
	private Texture backTxt;
	public static Music gameMusic;
	//Обьект для обработки нажатий на экран,включает несколько обьектов InputProcessor
	private InputMultiplexer inputMultiplexer;
	//"Кисть" для отрисовки
	private SpriteBatch batch;
	private ExtendViewport viewport;
	//Один из ключевых обьектов игры и фреймворка в целом - Stage
	// Осуществляет обработку логики и отрисовку включенных в себя обьектов (актеров)
	public static Stage playerStage;
	public static com.mygdx.game.GameObjects.Player pl1;
	public static com.mygdx.game.GameObjects.Player pl2;
	public static com.mygdx.game.GameObjects.Player CURRENT_PLAYER;
	public static com.mygdx.game.GameObjects.Player ENEMY;

	public static ChaseCam chaseCam;

	private Texture circle;
	private Texture circleCur;
	private JoystickLeft joystickLeft;
	private JoystickRight joystickRight;

	public static ArrayList<Shooting> shootings;
	public static ArrayList<Shooting> shootingsEnemy;


	public static com.mygdx.game.GameObjects.Platform[] plat;
	private Texture txtplat;
	private Texture txtplatFloor;
	private Texture txtplatLeftWall;
	private Texture txtplatRightWall;
	private Texture txtplatCornerLeft;
	private Texture txtplatCornerRight;

	private HpSmall hpSmall;

	private Texture txtAmmunition;
	private com.mygdx.game.GameObjects.Ammunition[] ammunitions;

	private GameHUD hud;
	private Stage hudStage;
	private Texture jumpbtn;
	private Texture settingsbtn;

	private CoordBox coordBox;


	public ArenaGame (final MainGame game) {
		this.game=game;

		//создание игроков и массивов пуль для обновления данных
		CURRENT_PLAYER=new Player(MainGame.PL1_X,MainGame.PL_Y);
		ENEMY=new Player(MainGame.PL2_X,MainGame.PL_Y);
		shootings = new ArrayList<>();
		shootingsEnemy = new ArrayList<>();
		//установка в начальное положение определителя персонажа
		MainGame.setPlayerIdentify(-1);

		//передача пакета с именем и запрос на определение персонажа
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

		//размер "Окна" для камеры
		viewport=new ExtendViewport(MainGame.WORLD_SIZE_X,MainGame.WORLD_SIZE_Y);

		playerStage=new Stage(viewport);
		hudStage = new Stage();

		//загрузка анимаций персонажей
		pl1=new Player(MainGame.PL1_X,MainGame.PL_Y);
		pl1.setID(MainGame.PL1_X);
		pl1.setAnim(pl1.getTextureArray_aim_player_2(),MainGame.Aim_2);
		pl1.setAnim(pl1.getTextureArray_move_player_2(),MainGame.RunShoot_2);
		pl1.setAnim(pl1.getTextureArray_jump_player_2(),MainGame.JumpShoot_2);
		pl1.setAnim(pl1.getTextureArray_dead_player_2(),MainGame.Dead_2);

		pl2=new Player(MainGame.PL2_X,MainGame.PL_Y);
		pl2.setID(MainGame.PL2_X);
		pl2.setAnim(pl2.getTextureArray_aim_player_4(),MainGame.Aim_4);
		pl2.setAnim(pl2.getTextureArray_move_player_4(),MainGame.RunShoot_4);
		pl2.setAnim(pl2.getTextureArray_jump_player_4(),MainGame.JumpShoot_4);
		pl2.setAnim(pl2.getTextureArray_dead_player_4(),MainGame.Dead_4);


		//Определение персонажа в зависимости от подключения
		if (MainGame.getPlayerIdentify() == 1) {
			CURRENT_PLAYER = pl1;
			playerStage.addActor(CURRENT_PLAYER);
			CURRENT_PLAYER.setX(pl1.getID());
			CURRENT_PLAYER.setY(MainGame.PL_Y);
			CURRENT_PLAYER.useAnim(0.1f, true, pl1.getTextureArray_aim_player_2());
			//установка кода для передачи анимации на другой клиент
			CURRENT_PLAYER.setAnimationNum(11);

			ENEMY = pl2;
			playerStage.addActor(ENEMY);
			ENEMY.setX(pl2.getID());
			ENEMY.setY(MainGame.PL_Y);
			ENEMY.useAnim(0.1f, true, pl2.getTextureArray_aim_player_4());
			ENEMY.setAnimationNum(21);

			CURRENT_PLAYER.setName("CURRENT_PLAYER");
			CURRENT_PLAYER.setID(pl1.getID());
			ENEMY.setName("ENEMY");
			ENEMY.setID(pl2.getID());
		} else {
			CURRENT_PLAYER = pl2;
			playerStage.addActor(CURRENT_PLAYER);
			CURRENT_PLAYER.setX(pl2.getID());
			CURRENT_PLAYER.setY(MainGame.PL_Y);
			CURRENT_PLAYER.useAnim(0.1f, true, pl2.getTextureArray_aim_player_4());
			CURRENT_PLAYER.setAnimationNum(21);

			ENEMY = pl1;
			playerStage.addActor(ENEMY);
			ENEMY.setX(pl1.getID());
			ENEMY.setY(MainGame.PL_Y);
			ENEMY.useAnim(0.1f, true, pl1.getTextureArray_aim_player_2());
			ENEMY.setAnimationNum(11);

			CURRENT_PLAYER.setName("CURRENT_PLAYER");
			//по ID отличаем,текстуры какого персонажа использовать в отрисовке
			CURRENT_PLAYER.setID(pl2.getID());
			ENEMY.setName("ENEMY");
			ENEMY.setID(pl1.getID());
		}


		ENEMY.position=new Vector2(ENEMY.getID(),MainGame.PL_Y);
		CURRENT_PLAYER.position=new Vector2(CURRENT_PLAYER.getID(),MainGame.PL_Y);

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
		txtplatCornerLeft=new Texture("SciFiPlatformset-5.png");
		txtplatCornerRight=new Texture("SciFiPlatformset-4.png");

		//создание карты
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

		//установка игрового интерфейса (окон и кнопок)
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

		//финальный перевод в начальное положение ресурсов перед началом матча
		setStartSettings();

	}


	@Override
	public void show() {
		chaseCam = new ChaseCam(viewport.getCamera(), CURRENT_PLAYER); }

	@Override
	public void render (float delta) {
		//проверка отключения одного из игроков
		if ((!ClientClass.isConnected() || ClientClass.playerNUM==-1) && MainGame.seconds>0){
			game.setScreen(new ConnectMenu(game));
			ClientClass.close();
		}

		//Очистка экрана перед следующим кадром
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

		//проверка создания пули и их обработка (проверка и своих пуль,и вражеских)
		joystickRight.checkCreateBullet();

		for (int i=0;i<shootingsEnemy.size();i++){
			shootingsEnemy.get(i).update();
			shootingsEnemy.get(i).collapse(CURRENT_PLAYER);
			if (ENEMY.hp<=0 || CURRENT_PLAYER.hp<=0)shootingsEnemy.get(i).setVisible(false);
			if (shootingsEnemy.get(i).isOut || !shootingsEnemy.get(i).isVisible()){//удаление той пули, которая вышла за экран
				shootingsEnemy.remove(i);
			}
		}

		for (int i=0;i<shootings.size();i++){
			shootings.get(i).update();
			shootings.get(i).collapse(ENEMY);
			if (ENEMY.hp<=0 || CURRENT_PLAYER.hp<=0)shootings.get(i).setVisible(false);
			if (shootings.get(i).isOut || !shootings.get(i).isVisible()){
				shootings.remove(i);
			}
		}
		//обработка обстоятельств смерти
		if (CURRENT_PLAYER.hp<=0){
			if (!MainGame.scoreFlag ){
				GameHUD.scoreLogs.setVisible(true);
				MainGame.enemy_score++;
				MainGame.timeFromLastKill=MainGame.seconds;
				MainGame.scoreFlag=true;
				MessageBox box=new MessageBox();
				box.message=true;
				ClientClass.sendBox(box);
			}

			if (MainGame.timeFromLastKill - MainGame.seconds >= 3) {
				GameHUD.scoreLogs.setVisible(false);
				MainGame.scoreFlag=false;
				MainGame.timeFromLastKill = -1;
				CURRENT_PLAYER.setNextRound();
			}
		}

		else if (MainGame.needEnemyReanimate) {

			if (!MainGame.scoreFlag){
				GameHUD.scoreLogs.setVisible(true);
				MainGame.current_player_score++;
				MainGame.timeFromLastKill=MainGame.seconds;
				MainGame.scoreFlag=true;
			}

			if (MainGame.timeFromLastKill - MainGame.seconds >= 3) {
				GameHUD.scoreLogs.setVisible(false);
				MainGame.needEnemyReanimate=false;
				MainGame.scoreFlag=false;
				MainGame.timeFromLastKill = -1;
				CURRENT_PLAYER.setNextRound();
				ENEMY.setNextRound();
			}
		}

		for (Ammunition ammunition: ammunitions){
			ammunition.update();
		}
		//отправка на сервер сведений,стрелял игрок или нет
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
		//отрисовка
		hud.setColor(1,1,1,0.5f);
		chaseCam.update();
		viewport.apply();
		batch.begin();
		batch.draw(backTxt,0,0,2320,1080);
		batch.end();
		playerStage.draw();
		batch.begin();
		hud.drawTimer(delta,batch);
		hud.drawHpPanel(delta,batch);
		batch.end();
		hudStage.draw();

		//обработка окончания игры
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
	//метод отвечающий за стартовое состояние переменных на начало матча
	public void setStartSettings(){
		MainGame.seconds=MainGame.TIME_SECONDS;
		MainGame.time="-1";
		MainGame.isShooted=false;
		MainGame.jumped=false;
		MainGame.isSettingsDialogOpened=false;
		MainGame.current_player_score=0;
		MainGame.enemy_score=0;
		CURRENT_PLAYER.amountBullets=MainGame.AMOUNT_BULLETS;
		JoystickLeft.checkAngleLeft=true;
		JoystickLeft.isTouchLeft=false;
		JoystickLeft.angleLeft=0;
		JoystickRight.checkAngleRight=true;
		JoystickRight.angleRight=0;
		JoystickRight.isTouchRight=false;
		pl1.useAnim(0.1f,true,pl1.getTextureArray_aim_player_2());
		pl2.useAnim(0.1f,true,pl2.getTextureArray_aim_player_4());
		MainGame.bulletsDamage=5;
		MainGame.timeFromLastKill=-1;
		MainGame.scoreFlag=false;
	}
}