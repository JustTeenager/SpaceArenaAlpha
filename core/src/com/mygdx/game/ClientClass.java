package com.mygdx.game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.net.InetAddress;

import static com.mygdx.game.ArenaGame.ENEMY;


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
        adr=InetAddress.getByName("46.39.242.26");
        client = new Client();
        //Регистрируем пакеты
        client.getKryo().register(MessageBox.class);
        client.getKryo().register(CoordBox.class);
        client.getKryo().register(com.badlogic.gdx.math.Vector2.class);
        client.getKryo().register(com.badlogic.gdx.math.Rectangle.class);
        client.getKryo().register(PlayersWaitingBox.class);
        client.start();

        //Клиент подключается к серверу
        client.connect(5000, adr, tcpPort, udpPort);
        client.addListener(new ClientClass());
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        System.out.println("Вы подключились к серверу!");
    }

    public static void sendBox(CoordBox box){
        client.sendTCP(box);
    }
    public static void sendPlayersWaitingBox(PlayersWaitingBox box){
        client.sendUDP(box);
    }

    public void received(Connection c, Object p){
        //Проверяем какой отправляется пакет
        if (p instanceof PlayersWaitingBox){
            PlayersWaitingBox box=(PlayersWaitingBox) p;
            if (box.count==-1){
                playerNUM=box.count;
            }


            MainGame.playersNum=box.count;
        }


        if(p instanceof MessageBox){
            //Если мы получили PacketMessage .
            MessageBox box = (MessageBox) p;
            System.out.println("Ответ от сервера: "+box.message);
            messageReceived = true;
        }

        if (p instanceof CoordBox){
            if (MainGame.getPlayerIdentify()==0) {
                box = (CoordBox) p;
                MainGame.enemy_name=box.BplayerName;
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
        try {
            ENEMY.position = box.BpositionPlayer;
            ENEMY.setX(box.BpositionPlayer.x);
            ENEMY.setY(box.BpositionPlayer.y);
            ENEMY.hp = box.Bhp;
            ENEMY.flip = box.flipped;
            ENEMY.rectangle = box.BrectanglePlayer;

            switch (box.BplayerAnimNumber) {
                case 11: {
                    ENEMY.useAnim(0.1f, true, ENEMY.getTextureArray_aim_player_2());
                }
                break;
                case 12: {
                    ENEMY.useAnim(0.1f, true, ENEMY.getTextureArray_move_player_2());
                }
                break;
                case 13: {
                    ENEMY.useAnim(0.1f, false, ENEMY.getTextureArray_jump_player_2());
                }
                break;
                case 14: {
                    ENEMY.useAnim(0.1f, false, ENEMY.getTextureArray_dead_player_2());
                }
                break;
                case 21: {
                    ENEMY.useAnim(0.1f, true, ENEMY.getTextureArray_aim_player_4());
                }
                break;
                case 22: {
                    ENEMY.useAnim(0.1f, true, ENEMY.getTextureArray_move_player_4());
                }
                break;
                case 23: {
                    ENEMY.useAnim(0.1f, false, ENEMY.getTextureArray_jump_player_4());
                }
                break;
                case 24: {
                    ENEMY.useAnim(0.1f, false, ENEMY.getTextureArray_dead_player_4());
                }
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Shooting.addEnemyShootingArray(box);
        }catch (Exception e){}
    }
    public static void boxNumDeploy(CoordBox box){
        playerNUM = box.getPlayerIdentify();
        MainGame.setPlayerIdentify(playerNUM);
        System.out.println(MainGame.getPlayerIdentify() + " IS IDENTY FROM THE CLIENT");
    }

    public static boolean isConnected(){
        return client.isConnected();
    }
    public static void close(){
        client.close();
    }
}

