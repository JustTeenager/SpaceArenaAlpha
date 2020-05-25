package com.mygdx.game.Client;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.mygdx.game.Core.MainGame;
import com.mygdx.game.GameObjects.Shooting;

import java.net.InetAddress;
import static com.mygdx.game.Core.ArenaGame.ENEMY;

//класс,отвечающий за обмен данных с сервером и обработку входящих данных
public class ClientClass extends Listener {

    private static Client client;
    private static CoordBox box;
    private static int tcpPort = 54555, udpPort = 54555;
    private static InetAddress adr;

    //номер,по которому определяется персонаж
    private static int safeID;

    //для обработки потери соединения одним из игроков
    public static int playerNUM;

    public static void startClient() throws Exception {
        playerNUM=0;
        System.out.println("Подключаемся к серверу");
        adr=InetAddress.getByName("46.39.242.26");
        client = new Client();
        //Регистрируем пакеты
        client.getKryo().register(MessageBox.class);
        client.getKryo().register(CoordBox.class);
        client.getKryo().register(Vector2.class);
        client.getKryo().register(Rectangle.class);
        client.getKryo().register(PlayersWaitingBox.class);
        client.getKryo().register(PlayerNameBox.class);
        client.start();

        //Клиент подключается к серверу
        client.connect(5000, adr, tcpPort, udpPort);
        client.addListener(new ClientClass());
    }

    @Override
    public void connected(Connection connection) {
        super.connected(connection);
        safeID=connection.getID()%2;
        System.out.println("Вы подключились к серверу!");
    }

    public static void sendBox(CoordBox box){
        client.sendTCP(box);
    }
    public static void sendBox(PlayerNameBox box){
        client.sendTCP(box);
    }
    public static void sendBox(MessageBox box){
        client.sendTCP(box);
    }
    public static void sendPlayersWaitingBox(PlayersWaitingBox box){
        client.sendUDP(box);
    }

    public void received(Connection c, Object p){
        //Проверяем какой отправляется пакет

        if (p instanceof PlayerNameBox){
            PlayerNameBox box= (PlayerNameBox) p;
            MainGame.enemy_name=box.name;
        }

        if (p instanceof PlayersWaitingBox){
            PlayersWaitingBox box=(PlayersWaitingBox) p;
            if (box.count==-1){
                playerNUM=box.count;
            }


           MainGame.playersNum=box.count;
        }


        if(p instanceof MessageBox){
            MessageBox box = (MessageBox) p;
            MainGame.needEnemyReanimate=box.message;
        }

        if (p instanceof CoordBox){
            if (MainGame.getPlayerIdentify()==-1) {
                box = (CoordBox) p;
                boxNumDeploy(box);
            }
            else {
                box = (CoordBox) p;
                boxDeploy(box);
            }
        }
    }

    public static void boxDeploy(CoordBox box){
        try {
            ENEMY.position = box.boxPositionPlayer;
            ENEMY.setX(box.boxPositionPlayer.x);
            ENEMY.setY(box.boxPositionPlayer.y);
            ENEMY.hp = box.boxHp;
            ENEMY.flip = box.flipped;
            ENEMY.rectangle = box.boxRectanglePlayer;
            MainGame.time=box.boxTime;
            MainGame.seconds=box.seconds;

            switch (box.boxPlayerAnimNumber) {
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

        //если был произведен выстрел - ресурсы для создания пули выгружаются,дальше пуля обрабатывается как и все остальные,на стороне клиента
        try {
            Shooting.addEnemyShootingArray(box);
        }catch (Exception e){}
    }
    public static void boxNumDeploy(CoordBox box){
        playerNUM = box.getPlayerIdentify();
        MainGame.setPlayerIdentify(safeID);
        MainGame.time=box.boxTime;
        MainGame.seconds=box.seconds;
        System.out.println(MainGame.getPlayerIdentify() + " IS IDENTY FROM THE CLIENT");
    }

    public static boolean isConnected(){
        return client.isConnected();
    }
    public static void close(){
        client.close();
    }
}

