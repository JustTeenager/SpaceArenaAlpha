package com.mygdx.game.Core;

import com.badlogic.gdx.Game;
import com.mygdx.game.Menu.MainMenu;

import java.util.HashMap;
import pl.mk5.gdx.fireapp.GdxFIRApp;

//Класс игрового контектса,содержит также большую часть общих переменных
public class MainGame extends Game {
    public static final float GRAVITY=300;
    public static final int WORLD_SIZE_X=1020;
    public static final int WORLD_SIZE_Y=800;
    public static final int AMOUNT_BULLETS=30;
    public static final int TIME_SECONDS=120;
    public static final float VELOCITY_BULLETS=1200;
    public static final float MAX_HP=100;
    public static final int PL1_X=-100;
    public static final int PL2_X=700;
    public static final int PL_Y=100;
    public static int bulletsDamage;
    public static final String GAME_OVER="Game Over!";
    public static final String GAME_SETTINGS="Game Settings";
    public static final String LEADERBOARD="Leaderboard";
    public static boolean isShooted=false;
    //переменная для реализация прыжка с платформы
    public static boolean jumped=false;
    public static boolean isSettingsDialogOpened=false;
    public static boolean needEnemyReanimate=false;
    public static boolean authorized=false;
    public static boolean registered=false;
    public static int playersNum;
    private static int playerIdentify;

    public static String current_player_name;
    public static String enemy_name;
    public static String playerLogin;
    public static String playerPassword;

    public static float volume=1;
    public static float volButtonX=-1;

    //переменные для установки кнопок
    public static int buttonDistancefromPanel=75;
    public static int buttonDistanceFromEachOther=140;
    public static int current_player_score=0;
    public static int enemy_score=0;

    //переменные для отсчета времени
    public static String time="-1";
    public static int seconds=TIME_SECONDS;

    public static long timeFromLastKill=-1;
    //обьект,получаемыйу
    public static HashMap leaderMap;

    //переменная проверки отображения окна счета
    public static boolean scoreFlag=false;



    public static final String[] Aim_4 = {
            "Aim 4(1).png", "Aim 4(2).png",
            "Aim 4(3).png", "Aim 4(4).png",
            "Aim 4(5).png", "Aim 4(6).png", "Aim 4(7).png", "Aim 4(8).png",
            "Aim 4(9).png", "Aim 4(10).png"};

    public static final String[] RunShoot_4 = {
            "RunShoot 4(1).png", "RunShoot 4(2).png",
            "RunShoot 4(3).png", "RunShoot 4(4).png",
            "RunShoot 4(5).png", "RunShoot 4(6).png", "RunShoot 4(7).png", "RunShoot 4(8).png",
            "RunShoot 4(9).png", "RunShoot 4(10).png"};

    public static final String[] JumpShoot_4 = {"JumpShoot 4(1).png","JumpShoot 4(2).png","JumpShoot 4(3).png",
            "JumpShoot 4(4).png","JumpShoot 4(5).png",
            "JumpShoot 4(6).png","JumpShoot 4(7).png",
            "JumpShoot 4(8).png","JumpShoot 4(9).png","JumpShoot 4(10).png"};

    public static final String[] Dead_4 = {
            "Dead 4(1).png", "Dead 4(2).png",
            "Dead 4(3).png", "Dead 4(4).png",
            "Dead 4(5).png", "Dead 4(6).png", "Dead 4(7).png", "Dead 4(8).png",
            "Dead 4(9).png", "Dead 4(10).png"};

    public static final String[] Aim_2 = {
            "Aim (1).png", "Aim (2).png",
            "Aim (3).png", "Aim (4).png",
            "Aim (5).png", "Aim (6).png", "Aim (7).png", "Aim (8).png",
            "Aim (9).png", "Aim (10).png"};

    public static final String[] RunShoot_2 = {
            "RunShoot (1).png", "RunShoot (2).png",
            "RunShoot (3).png", "RunShoot (4).png",
            "RunShoot (5).png", "RunShoot (6).png", "RunShoot (7).png", "RunShoot (8).png",
            "RunShoot (9).png", "RunShoot (10).png"};

    public static final String[] JumpShoot_2 = {"JumpShoot (1).png","JumpShoot (2).png","JumpShoot (3).png",
            "JumpShoot (4).png","JumpShoot (5).png",
            "JumpShoot (6).png","JumpShoot (7).png",
            "JumpShoot (8).png","JumpShoot (9).png","JumpShoot (10).png"};

    public static final String[] Dead_2 = {
            "Dead (1).png", "Dead (2).png",
            "Dead (3).png", "Dead (4).png",
            "Dead (5).png", "Dead (6).png", "Dead (7).png", "Dead (8).png",
            "Dead (9).png", "Dead (10).png"};

    @Override
    public void create() {
        GdxFIRApp.inst().configure();
        setScreen(new MainMenu(this));
    }

    public static int getPlayerIdentify() {
        return playerIdentify;
    }

    public static void setPlayerIdentify(int playerIdentify) {
        MainGame.playerIdentify = playerIdentify;
    }
}
