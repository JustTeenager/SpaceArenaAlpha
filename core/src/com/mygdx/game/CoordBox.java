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

    private ArrayList<Shooting> bullets;
    private Vector2 positionBullet;
    private Texture bulletTexture;
    private Sprite bulletSprite;
    private Rectangle bulletRectangle;

    CoordBox(int playerIdentify, Vector2 positionPlayer, Texture playerTexture, Player.JumpState jumpState, ArrayList<Shooting> bullets){
        this.playerIdentify=playerIdentify;
        this.positionPlayer=positionPlayer;
        this.playerTexture=playerTexture;
        this.jumpState=jumpState;
        this.bullets=bullets;
    }

    public void decordBox(int Identify) {//если identify=1, то передаём 2 и наоборот

    }
}

