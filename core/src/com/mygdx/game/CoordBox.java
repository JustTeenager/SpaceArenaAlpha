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

     Vector2 BpositionPlayer;
     Animation<TextureRegion> BplayerAnim;
     int BplayerAnimHash;
     Rectangle BrectanglePlayer;
     int Bhp;
     ArrayList<Vector2> BbulletsPosition;
     ArrayList<Rectangle> BrectangleShoot;
     Texture BshootTexture;
     int BshootTextureHash;
     ArrayList<Double> Bangles;

    CoordBox(int playerIdentify, Vector2 positionPlayer ,int playerAnimHash /*Animation<TextureRegion> playerAnim*/, Rectangle rectanglePlayer, int hp,
             ArrayList<Vector2> bulletsPosition,ArrayList<Double> angles, ArrayList<Rectangle> rectangleShoot,int  shootTextureHash /*Texture shootTexture*/){
        this.playerIdentify=playerIdentify;
        BpositionPlayer=positionPlayer;
        BplayerAnimHash=playerAnimHash;
        //BplayerAnim=playerAnim;
        BrectanglePlayer=rectanglePlayer;
        Bhp=hp;


        BbulletsPosition=bulletsPosition;
        Bangles=angles;
        BrectangleShoot=rectangleShoot;
        BshootTextureHash=shootTextureHash;
        //BshootTexture=shootTexture;
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

