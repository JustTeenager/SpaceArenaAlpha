package com.mygdx.game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.net.InetAddress;


public class ClientClass extends Listener {
    static Client client;
    //Порт к которому мы будем подключатся
    static int tcpPort = 54555, udpPort = 54555;
    static InetAddress adr;

    static boolean messageReceived;

    public static void startClient() throws Exception {
        messageReceived=false;
        System.out.println("Подключаемся к серверу");
        adr=InetAddress.getByName("46.39.242.26");//Адрес сервера? Надо тестить
        client = new Client();
        //Регистрируем пакет
        client.getKryo().register(MessageBox.class);
        client.start();

        //Клиент подключается к серверу
        client.connect(5000, adr, tcpPort, udpPort);
        client.addListener(new ClientClass());

        System.out.println("Вы подключились к серверу!");

        //Отправляем на сервер сообщение
        if (client.isConnected()) client.sendTCP("abc");

        while (!messageReceived){
            Thread.sleep(1000);
        }

        System.out.println("Клиент покидает сервер");
        //System.exit(0);//окончание работы клиента
    }


    public void received(Connection c, Object p){
        //Проверяем какой отправляется пакет
        if(p instanceof MessageBox){
            //Если мы получили PacketMessage .
            MessageBox box = (MessageBox) p;
            System.out.println("Ответ от сервера: "+box.message);
            messageReceived = true;
            //Мы получили сообщение
        }
    }
}

