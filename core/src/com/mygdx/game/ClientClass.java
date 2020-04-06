package com.mygdx.game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import java.net.InetAddress;



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
            box = (CoordBox) p;
            if (MainGame.getPlayerIdentify()==0) {
                boxNumDeploy(box);
            }
            /*else {
                boxDeploy(box);
            }*/
        }
    }
    public static void setNumToIdentify(){
       MainGame.setPlayerIdentify(playerNUM);
    }
    public static void boxDeploy(CoordBox box){
        ArenaGame.ENEMY.position=box.positionPlayer;
        ArenaGame.ENEMY.setX(box.positionPlayer.x);
        ArenaGame.ENEMY.setY(box.positionPlayer.y);
        ArenaGame.ENEMY.hp=box.hp;
        ArenaGame.ENEMY.anim=box.playerAnim;
        ArenaGame.ENEMY.rectangle=box.rectanglePlayer;
    }
    public static void boxNumDeploy(CoordBox box){
        playerNUM = box.getPlayerIdentify();
        MainGame.setPlayerIdentify(playerNUM);
        System.out.println(MainGame.getPlayerIdentify() + " IS IDENTY FROM THE CLIENT");
    }
}

