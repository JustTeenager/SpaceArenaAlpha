package com.mygdx.game.GameObjects;

import com.badlogic.gdx.graphics.Camera;

//класс игровой камеры
public class ChaseCam {
    public Camera camera;

    private Player player;

    public ChaseCam(Camera camera, Player player) {
        this.camera = camera;
        this.player = player;
    }

    public void update() {
        camera.position.x = player.position.x;
        camera.position.y = player.position.y+200;
    }
}
