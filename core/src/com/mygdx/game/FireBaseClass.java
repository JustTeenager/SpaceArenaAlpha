package com.mygdx.game;

import com.badlogic.gdx.Gdx;


import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;

import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.functional.Function;

public class FireBaseClass {

    public static void signIn(final String playerEmail, final char[] playerPassword) {
        // Sign in via username/email and password
        GdxFIRAuth.instance()
                .signInWithEmailAndPassword(playerEmail, playerPassword)
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        //if (gdxFirebaseUser.getUserInfo()!=null)
                        successLogin();
                    }
                });
    }


    public static void register(final String playerEmail, final char[] playerPassword){
            GdxFIRAuth.instance()
                    .createUserWithEmailAndPassword(playerEmail, playerPassword).then(new Consumer<GdxFirebaseUser>() {
                        @Override
                        public void accept(GdxFirebaseUser gdxFirebaseUser) {
                            successRegister();
                        }
                    })
                    .fail(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) {
                                GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
                                System.out.println("ERROR DURING REG");
                        }
                    });
    }

    public static void signOut(){
        GdxFIRAuth.instance().signOut()
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void o) {
                        MainGame.authorized=false;
                    }
                });
    }

    private static void successLogin(){
        MainGame.authorized=true;
        System.out.println("LOGGED");
    }

    private static void successRegister(){
        MainGame.registered=true;
        System.out.println("REGISTERED");
    }

    public static void updateKDInDataBase(final int addKills,final int addDeath) {
        GdxFIRDatabase.instance().inReference("/Kills/"+MainGame.playerPassword)
                .transaction(Long.class, new Function<Long, Long>() {
                    @Override
                    public Long apply(Long i) {
                        return i + addKills;
                    }
                }).then( GdxFIRDatabase.inst().inReference("/Death/"+MainGame.playerPassword)
                .transaction(Long.class, new Function<Long, Long>() {
                    @Override
                    public Long apply(Long i) {
                        return i + addDeath;
                    }
                }));

    }

    public static void addKDInDataBase() {
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin,MainGame.playerPassword.toCharArray()).then(
                        GdxFIRDatabase.instance()
                                .inReference("/Death/"+MainGame.playerPassword).setValue(0));
        GdxFIRAuth.instance().signInWithEmailAndPassword(MainGame.playerLogin,MainGame.playerPassword.toCharArray()).then(
                GdxFIRDatabase.instance()
                        .inReference("/Kills/"+MainGame.playerPassword).setValue(0));
        System.out.println("added kd");
    }

    /*public static void updateChild(){
        System.out.println("CHILD GAVNINA");
        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/Kills").onChildChange(Long.class, ChildEventType.ADDED))
                .then(new Consumer<Long>() {
                    @Override
                    public void accept(Long lon) {
                        //lon=(long)10;
                        Gdx.app.log("ChildEventTest", "added");
                        System.out.println("FIREBASE GOVNINA!!!");
                        System.out.println(lon);
                    }
                }).fail(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println("ERROR DURING REG");
                System.out.println(throwable.getMessage());
                try {
                    throw throwable;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/Kills").onChildChange(Long.class, ChildEventType.CHANGED))
                .then(new Consumer<Long>() {
                    @Override
                    public void accept(Long str) {
                        Gdx.app.log("ChildEventTest", "changed");
                        System.out.println("FIREBASE GOVNINA!!!");
                        System.out.println(str);
                    }
                }).fail(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println("ERROR DURING REG");
                System.out.println(throwable.getMessage());
                try {
                    throw throwable;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
