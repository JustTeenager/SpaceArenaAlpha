package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

public class CoordBox {

    private int playerIdentify;
    boolean flipped;
     Vector2 BpositionPlayer;
     int BplayerAnimNumber;
     Rectangle BrectanglePlayer;
     int Bhp;
     ArrayList<Vector2> BbulletsPosition;
     ArrayList<Rectangle> BrectangleShoot;
     ArrayList<Double> Bangles;

    CoordBox(int playerIdentify, Vector2 positionPlayer ,int playerAnimNumber ,boolean flipped, Rectangle rectanglePlayer, int hp,
             ArrayList<Vector2> bulletsPosition,ArrayList<Double> angles, ArrayList<Rectangle> rectangleShoot){
        this.playerIdentify=playerIdentify;
        this.flipped=flipped;
        BpositionPlayer=positionPlayer;
        BplayerAnimNumber=playerAnimNumber;
        BrectanglePlayer=rectanglePlayer;
        Bhp=hp;


        BbulletsPosition=bulletsPosition;
        Bangles=angles;
        BrectangleShoot=rectangleShoot;
    }

    CoordBox(int playerIdentify){
        this.playerIdentify=playerIdentify;
    }

    CoordBox(){}

    public int getPlayerIdentify() {
        return playerIdentify;
    }

    public void setPlayerIdentify(int playerIdentify) {
        this.playerIdentify = playerIdentify;
    }
}

