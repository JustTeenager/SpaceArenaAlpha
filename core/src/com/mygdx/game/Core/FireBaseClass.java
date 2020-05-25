package com.mygdx.game.Core;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.mygdx.game.Dialogs.AuthorizationDialog;


import java.util.HashMap;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.functional.Function;

//Класс,отвечающий за взаимодействия с базой данных FireBase
public class FireBaseClass {
    //ключ пользователя,инициализируется при входе в аккаунт
    private static String uID="0";

    private static float kills=-1;
    private static float death=-1;

    // функция входа в аккаунт
    public static void signIn(final String playerEmail, final char[] playerPassword, final AuthorizationDialog dialog) {
        //отключение кнопок на время авторизации
        FireBaseClass.disableAutoButtons(dialog);
        GdxFIRAuth.instance()
                .signInWithEmailAndPassword(playerEmail, playerPassword)
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        uID=gdxFirebaseUser.getUserInfo().getUid();
                        getUserName(dialog);
                    }
                }).fail(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                //включение кнопок при неудачной авторизации
                enableAutoButtons(dialog);
                try {
                    throw throwable;
                } catch (Throwable e) {
                    e.printStackTrace();
                    dialog.setErrorText("Something with email or pass");
                }
            }

        });
    }

    //функция создания нового аккаунта
    public static void register(final String playerEmail, final char[] playerPassword, final AuthorizationDialog dialog){
        synchronized (GdxFIRDatabase.class) {
            FireBaseClass.disableAutoButtons(dialog);
            GdxFIRAuth.instance()
                    .createUserWithEmailAndPassword(playerEmail, playerPassword).then(new Consumer<GdxFirebaseUser>() {
                @Override
                public void accept(GdxFirebaseUser gdxFirebaseUser) {
                    MainGame.current_player_name = "player";
                    uID = gdxFirebaseUser.getUserInfo().getUid();
                    enableAutoButtons(dialog);
                    successRegister();
                    dialog.setErrorText("Now log in!");
                }
            })
                    .fail(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) {
                            enableAutoButtons(dialog);
                            try {
                                throw throwable;
                            } catch (Throwable e) {
                                e.printStackTrace();
                                dialog.setErrorText("Something with email or pass");
                            }
                        }
                    });
        }
    }

    //функция выхода из аккаунта
    public static void signOut( final AuthorizationDialog dialog){
        FireBaseClass.disableAutoButtons(dialog);
        GdxFIRAuth.instance().signOut()
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void o) {
                        enableAutoButtons(dialog);
                        successSignOut();
                    }
                });
    }

    private static void successLogin(){
        MainGame.authorized=true;
    }

    private static void successSignOut(){
        MainGame.authorized=false;
        MainGame.current_player_name=null;
    }

    private static void successRegister(){
        MainGame.registered=true;
    }

    //функция смены имени
    public static void updatePLayerName(final String nameActual){
        GdxFIRDatabase.instance().inReference(uID+"/Name")
                .transaction(String.class, new Function<String, String>() {
                    @Override
                    public String apply(String name) {
                        return nameActual;
                    }
                });
    }

    //функция обновления данных (в конце матча)
    public static void updateStatInDataBase(final int addKills,final int addDeath) {
        synchronized (GdxFIRDatabase.class) {
            GdxFIRDatabase.instance().inReference(uID + "/Kills")
                    .transaction(Long.class, new Function<Long, Long>() {
                        @Override
                        public Long apply(Long i) {
                            kills = i + addKills;
                            return (long) kills;
                        }
                    });
            GdxFIRDatabase.instance().inReference(uID + "/Death")
                    .transaction(Long.class, new Function<Long, Long>() {
                        @Override
                        public Long apply(Long i) {
                            death = i + addDeath;
                            return (long) death;
                        }
                    });
            GdxFIRDatabase.instance().inReference(uID + "/KD")
                    .transaction(String.class, new Function<String, String>() {
                        @Override
                        public String apply(String kdLast) {
                            if (kills != -1 && death != -1) {
                                float kd=kills/death;
                                kills = -1;
                                death = -1;
                                return String.format("%.2f", kd);
                            }
                            else {
                                return String.valueOf(kills);
                            }
                        }
                    });
        }
    }
    //функция создания путей в базе для хранения необходимых данных
    public static void addKDInDataBase() {
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin, MainGame.playerPassword.toCharArray()).then(
                GdxFIRDatabase.instance()
                        .inReference(uID+"/Death").setValue(0));
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin, MainGame.playerPassword.toCharArray()).then(
                GdxFIRDatabase.instance()
                        .inReference(uID+"/Kills").setValue(0));
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin, MainGame.playerPassword.toCharArray()).then(
                GdxFIRDatabase.instance()
                        .inReference(uID+"/Name").setValue("player"));
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin, MainGame.playerPassword.toCharArray()).then(
                GdxFIRDatabase.instance()
                        .inReference(uID+"/KD").setValue(String.valueOf(0)));
    }

    private static void disableAutoButtons(AuthorizationDialog dialog){
        dialog.getLogInButton().setTouchable(Touchable.disabled);
        dialog.getRegisterButton().setTouchable(Touchable.disabled);
    }
    private static void enableAutoButtons(AuthorizationDialog dialog){
        dialog.getLogInButton().setTouchable(Touchable.enabled);
        dialog.getRegisterButton().setTouchable(Touchable.enabled);
    }

    //функция считывания имени аккаунта при входе
    public static void getUserName(final AuthorizationDialog dialog) {
        GdxFIRDatabase.instance()
                .inReference(uID+"/Name")
                .readValue(String.class)
                .then(new Consumer<String>() {
                    @Override
                    public void accept(String string) {
                        MainGame.current_player_name=string;
                        enableAutoButtons(dialog);
                        successLogin();
                    }
                });
    }

    //функция считывания данных для составления таблицы лидеров
    public static void actionListener() {
        GdxFIRDatabase.inst()
                .inReference("")
                .onDataChange(HashMap.class)
                .after(GdxFIRAuth.inst().signInWithEmailAndPassword(MainGame.playerLogin, MainGame.playerPassword.toCharArray()))
                .then(new Consumer<HashMap>() {
                    @Override
                    public void accept(HashMap s) {
                        MainGame.leaderMap=s;
                    }
                });
    }
}