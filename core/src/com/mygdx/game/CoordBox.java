package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class CoordBox {
    private int playerIdentify=0;

    private Vector2 positionPlayer;
    private Texture playerTexture;
    private Player.JumpState jumpState;
    private int hp;

    private ArrayList<Shooting> bullets;


    CoordBox(int playerIdentify, Vector2 positionPlayer, Texture playerTexture, Player.JumpState jumpState,int hp, ArrayList<Shooting> bullets){
        this.playerIdentify=playerIdentify;
        this.positionPlayer=positionPlayer;
        this.playerTexture=playerTexture;
        this.jumpState=jumpState;
        this.hp=hp;
        this.bullets=bullets;
    }

    public void decordBox(int Identify) {//если identify=1, то передаём 2 и наоборот

    }
}

