package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    static final float GRAVITY=300;
    //gravity=425
    static final int WORLD_SIZE_X=1020;
    static final int WORLD_SIZE_Y=800;
    static final int AMOUNT_BULLETS=40;
    static final float VELOCITY_BULLETS=1000;
    static final float MAX_HP=100;
    static int bulletsDamage;
    static final String GAME_OVER="Game Over!";
    static boolean isShooted=false;
    static boolean jumped=false;
    static boolean isSettingsDialogOpened=false;

    static int playersNum;
    static int playerIdentify;
    static String current_player_name="player";
    static String enemy_name;

    static float volume=1;
    static float volButtonX=-1;

    static int buttonDistancefromPanel=75;
    static int buttonDistanceFromEachOther=140;
    static int current_player_score=0;
    static int enemy_score=0;

    static String time="-1";
    static int seconds=60;

    static long timeFromLastKill=-1;


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
        setScreen(new MainMenu(this));
    }

    public static int getPlayerIdentify() {
        return playerIdentify;
    }

    public static void setPlayerIdentify(int playerIdentify) {
        MainGame.playerIdentify = playerIdentify;//
    }
}
