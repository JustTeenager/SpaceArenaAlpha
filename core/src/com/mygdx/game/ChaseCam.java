package com.mygdx.game;

import com.badlogic.gdx.graphics.Camera;

public class ChaseCam {
    private Camera camera;

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

