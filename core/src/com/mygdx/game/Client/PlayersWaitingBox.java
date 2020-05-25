package com.mygdx.game.Client;

//класс,отвечающий за реализацию ожидания второго игрока
public class PlayersWaitingBox {
    int count;
    public PlayersWaitingBox(){}
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
