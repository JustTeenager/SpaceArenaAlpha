package com.mygdx.game;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class FireBaseClass {

    public static void signIn(String playerEmail,char[] playerPassword) {
        // Sign in via username/email and password
        GdxFIRAuth.inst()
                .signInWithEmailAndPassword(playerEmail, playerPassword)
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        if (gdxFirebaseUser.getUserInfo()!=null)
                        success();
                    }
                });
    }


    public static void register(String playerEmail,char[] playerPassword){
            GdxFIRAuth.inst()
                    .createUserWithEmailAndPassword(playerEmail, playerPassword)
                    .then(new Consumer<GdxFirebaseUser>() {
                        @Override
                        public void accept(GdxFirebaseUser gdxFirebaseUser) {
                            success();
                        }
                    })
                    .fail(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) {
                            if( s.contains("The email address is already in use by another account") ){
                                GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
                                System.out.println("error reg");
                            }
                        }
                    });
    }

    public static void signOut(){
        GdxFIRAuth.inst().signOut()
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void o) {
                        success();
                    }
                });
    }

    private static void success(){
        MainGame.authorized=true;
        System.out.println("LIBGDX GOVNINA");
    }

}
