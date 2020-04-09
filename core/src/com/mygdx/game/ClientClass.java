package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.net.InetAddress;

import static com.mygdx.game.ArenaGame.TextureArray_aim_player_2_HASH;
import static com.mygdx.game.ArenaGame.TextureArray_aim_player_4_HASH;
import static com.mygdx.game.ArenaGame.TextureArray_jump_player_2_HASH;
import static com.mygdx.game.ArenaGame.TextureArray_jump_player_4_HASH;
import static com.mygdx.game.ArenaGame.TextureArray_move_player_2_HASH;
import static com.mygdx.game.ArenaGame.TextureArray_move_player_4_HASH;
import static com.mygdx.game.Shooting.createEnemyShootingArray;


public class ClientClass extends Listener {

    static Client client;
    static CoordBox box;
    //Порт к которому мы будем подключатся
    static int tcpPort = 54555, udpPort = 54555;
    static InetAddress adr;

    static boolean messageReceived;

    static int playerNUM;

    public static void startClient() throws Exception {
        messageReceived=false;
        playerNUM=0;
        System.out.println("Подключаемся к серверу");
        adr=InetAddress.getByName("46.39.242.26");//Адрес сервера? Надо тестить
        client = new Client();
        //Регистрируем пакет
        client.getKryo().register(MessageBox.class);
        client.getKryo().register(CoordBox.class);
        client.getKryo().register(java.util.ArrayList.class);
        client.getKryo().register(com.badlogic.gdx.math.Vector2.class);
        client.getKryo().register(com.badlogic.gdx.math.Rectangle.class);
        client.getKryo().register(com.badlogic.gdx.graphics.g2d.Animation.class);
        client.getKryo().register(Object[].class);
        client.getKryo().register(com.badlogic.gdx.graphics.g2d.TextureRegion.class);
        client.getKryo().register(com.badlogic.gdx.graphics.Texture.class);
        client.getKryo().register(com.badlogic.gdx.graphics.glutils.FileTextureData.class);
        client.getKryo().register(com.badlogic.gdx.assets.AssetManager.class);
        client.start();

        //Клиент подключается к серверу
        client.connect(5000, adr, tcpPort, udpPort);
        client.addListener(new ClientClass());

        //Отправляем на сервер сообщениеЩ
        /*if (client.isConnected()) {
            System.out.println("Вы подключились к серверу!");
        }*/
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Вы подключились к серверу!");
    }

    public static void sendBox(CoordBox box){
        client.sendTCP(box);
    }

    public void received(Connection c, Object p){
        //Проверяем какой отправляется пакет
        if(p instanceof MessageBox){
            //Если мы получили PacketMessage .
            MessageBox box = (MessageBox) p;
            System.out.println("Ответ от сервера: "+box.message);
            messageReceived = true;
        }

        if (p instanceof CoordBox){
            if (MainGame.getPlayerIdentify()==0) {
                box = (CoordBox) p;
                boxNumDeploy(box);
            }
            else {
                box = (CoordBox) p;
                boxDeploy(box);
            }
        }
    }
    public static void setNumToIdentify(){
       MainGame.setPlayerIdentify(playerNUM);
    }
    public static void boxDeploy(CoordBox box){
        ArenaGame.ENEMY.position=box.BpositionPlayer;
        //ArenaGame.ENEMY.setX(box.BpositionPlayer.x);
        //ArenaGame.ENEMY.setY(box.BpositionPlayer.y);
        ArenaGame.ENEMY.hp=box.Bhp;

        if (box.BplayerAnimHash==TextureArray_aim_player_2_HASH) ArenaGame.ENEMY.useAnim(0.1f,true,ArenaGame.ENEMY.getTextureArray_aim_player_2());
        if (box.BplayerAnimHash==TextureArray_move_player_2_HASH) ArenaGame.ENEMY.useAnim(0.1f,true,ArenaGame.ENEMY.getTextureArray_move_player_2());
        if (box.BplayerAnimHash==TextureArray_jump_player_2_HASH) ArenaGame.ENEMY.useAnim(0.1f,false,ArenaGame.ENEMY.getTextureArray_jump_player_2());
        if (box.BplayerAnimHash==TextureArray_aim_player_4_HASH) ArenaGame.ENEMY.useAnim(0.1f,true,ArenaGame.ENEMY.getTextureArray_aim_player_4());
        if (box.BplayerAnimHash==TextureArray_move_player_4_HASH) ArenaGame.ENEMY.useAnim(0.1f,true,ArenaGame.ENEMY.getTextureArray_move_player_4());
        if (box.BplayerAnimHash==TextureArray_jump_player_4_HASH) ArenaGame.ENEMY.useAnim(0.1f,false,ArenaGame.ENEMY.getTextureArray_jump_player_4());



        //ArenaGame.ENEMY.anim=box.BplayerAnim;
        //ArenaGame.ENEMY.animation=box.BplayerAnim;
        ArenaGame.ENEMY.rectangle=box.BrectanglePlayer;

        ArenaGame.shootingsEnemy=createEnemyShootingArray(box);
    }
    public static void boxNumDeploy(CoordBox box){
        playerNUM = box.getPlayerIdentify();
        MainGame.setPlayerIdentify(playerNUM);
        System.out.println(MainGame.getPlayerIdentify() + " IS IDENTY FROM THE CLIENT");
    }
}

