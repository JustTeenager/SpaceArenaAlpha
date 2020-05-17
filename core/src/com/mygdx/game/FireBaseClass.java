package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.Touchable;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.functional.Function;

public class FireBaseClass {

    public static void signIn(final String playerEmail, final char[] playerPassword, final AuthorizationDialog dialog) {
        // Sign in via username/email and password
        FireBaseClass.disableAutoButtons(dialog);
        GdxFIRAuth.instance()
                .signInWithEmailAndPassword(playerEmail, playerPassword)
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        //if (gdxFirebaseUser.getUserInfo()!=
                        enableAutoButtons(dialog);
                        successLogin();
                    }
                }).fail(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println("ERROR DURING LOGIN");
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


    public static void register(final String playerEmail, final char[] playerPassword, final AuthorizationDialog dialog){
        FireBaseClass.disableAutoButtons(dialog);
            GdxFIRAuth.instance()
                    .createUserWithEmailAndPassword(playerEmail, playerPassword).then(new Consumer<GdxFirebaseUser>() {
                        @Override
                        public void accept(GdxFirebaseUser gdxFirebaseUser) {
                            enableAutoButtons(dialog);
                            successRegister();
                            dialog.setErrorText("Now log in!");
                        }
                    })
                    .fail(new BiConsumer<String, Throwable>() {
                        @Override
                        public void accept(String s, Throwable throwable) {
                                //GdxFIRAuth.inst().getCurrentUser().delete().subscribe();
                            System.out.println("REGISTRATION ERROR");
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

    //email:
    //com.google.firebase.auth.FirebaseAuthInvalidCredentialsException -- The email is badly formatted
    //com.google.firebase.auth.FirebaseAuthUserCollisionException --  The email is already in use
    //com.google.firebase.auth.FirebaseAuthWeakPasswordException -- The given password is invalid
    //



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
        System.out.println("LOGGED");
    }

    private static void successSignOut(){
        MainGame.authorized=false;
        System.out.println("SIGNED OUT");

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
        System.out.println("updated kd");
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

    private static void disableAutoButtons(AuthorizationDialog dialog){
        dialog.getLogInButton().setTouchable(Touchable.disabled);
        dialog.getRegisterButton().setTouchable(Touchable.disabled);
    }
    private static void enableAutoButtons(AuthorizationDialog dialog){
        dialog.getLogInButton().setTouchable(Touchable.enabled);
        dialog.getRegisterButton().setTouchable(Touchable.enabled);
    }

}
