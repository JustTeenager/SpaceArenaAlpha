package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Vector;

public class CoordBox {

    private int playerIdentify;

     Vector2 positionPlayer;
     Animation<TextureRegion> playerAnim;
     Rectangle rectanglePlayer;
     int hp;
     ArrayList<Vector2> bulletsPosition;
     ArrayList<Rectangle> rectangleShoot;
     Texture shootTexture;
     ArrayList<Double> angles;

    CoordBox(int playerIdentify, Vector2 positionPlayer, Animation<TextureRegion> playerAnim, Rectangle rectanglePlayer, int hp,
             ArrayList<Vector2> bulletsPosition,ArrayList<Double> angles, ArrayList<Rectangle> rectangleShoot, Texture shootTexture){
        this.playerIdentify=playerIdentify;
        this.positionPlayer=positionPlayer;
        this.playerAnim=playerAnim;
        this.rectanglePlayer=rectanglePlayer;
        this.hp=hp;


        this.bulletsPosition=bulletsPosition;
        this.angles=angles;
        this.rectangleShoot=rectangleShoot;
        this.shootTexture=shootTexture;
    }

    CoordBox(int playerIdentify){
        this.playerIdentify=playerIdentify;
    }

    CoordBox(){}

    public void decordBox(int Identify) {//если identify=1, то передаём 2 и наоборот

    }


    public int getPlayerIdentify() {
        return playerIdentify;
    }

    public void setPlayerIdentify(int playerIdentify) {
        this.playerIdentify = playerIdentify;
    }
}

