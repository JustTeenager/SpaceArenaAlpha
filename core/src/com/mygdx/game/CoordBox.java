package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CoordBox {
    private int playerIdentify;

    private Vector2 positionPlayer;
    private Texture playerTexture;
    private Rectangle rectanglePlayer;
    private int hp;

    private ArrayList<Integer> bulletsX;
    private ArrayList<Integer> bulletsY;
    private ArrayList<Rectangle> rectangleShoot;
    private Texture shootTexture;

    CoordBox(int playerIdentify, Vector2 positionPlayer, Texture playerTexture,Rectangle rectanglePlayer,int hp,
             ArrayList<Integer> bulletsX,ArrayList<Integer> bulletsY,ArrayList<Rectangle> rectangleShoot,Texture shootTexture){
        this.playerIdentify=playerIdentify;
        this.positionPlayer=positionPlayer;
        this.playerTexture=playerTexture;
        this.rectanglePlayer=rectanglePlayer;
        this.hp=hp;
        this.bulletsX=bulletsX;
        this.bulletsY=bulletsY;
        this.rectangleShoot=rectangleShoot;
        this.shootTexture=shootTexture;
    }

    public void decordBox(int Identify) {//если identify=1, то передаём 2 и наоборот

    }
}

