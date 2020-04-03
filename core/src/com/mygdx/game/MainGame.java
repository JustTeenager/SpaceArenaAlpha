package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MainGame extends Game {
    static final float GRAVITY=300;
    static final int WORLD_SIZE_X=1020;
    static final int WORLD_SIZE_Y=800;
    static boolean jumped=false;
    //static final int HUD_X=1020;
    //static final int HUD_Y=1000;

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

    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}
