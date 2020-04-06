package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CoordBox {

    private int playerIdentify;

     Vector2 positionPlayer;
     Animation<TextureRegion> playerAnim;
     Rectangle rectanglePlayer;
     int hp;

     ArrayList<Integer> bulletsX;
     ArrayList<Integer> bulletsY;
     ArrayList<Rectangle> rectangleShoot;
     Texture shootTexture;

    CoordBox(int playerIdentify, Vector2 positionPlayer, Animation<TextureRegion> playerAnim, Rectangle rectanglePlayer, int hp,
             ArrayList<Integer> bulletsX, ArrayList<Integer> bulletsY, ArrayList<Rectangle> rectangleShoot, Texture shootTexture){
        this.playerIdentify=playerIdentify;
        this.positionPlayer=positionPlayer;
        this.playerAnim=playerAnim;
        this.rectanglePlayer=rectanglePlayer;
        this.hp=hp;


        this.bulletsX=bulletsX;
        this.bulletsY=bulletsY;
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

