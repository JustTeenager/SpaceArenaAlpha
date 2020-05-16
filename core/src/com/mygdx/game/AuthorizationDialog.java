package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;

public class AuthorizationDialog extends Actor {
    private Window window;
    private BitmapFont font;
    private TextField emailField;
    private TextField passwordField;
    private Texture backTxt;

    private Buttons registerButton;
    private Buttons logInButton;

    public AuthorizationDialog(int size, Stage st){
        backTxt=new Texture("settingsPanel.png");
        font=new BitmapFont(Gdx.files.internal("registerLit.fnt"));
        font.getData().setScale(size);
        Drawable drawable=new Image(backTxt).getDrawable();

        Window.WindowStyle windowstyle=new Window.WindowStyle(font,new Color(0,0,0,0.55f),drawable);
        window=new Window("Log in or Sign up",windowstyle);
        window.padTop(150);
        window.padLeft(340);
        window.setSize(backTxt.getWidth(),backTxt.getHeight());
        window.setPosition(350,100);


        TextField.TextFieldStyle style=new TextField.TextFieldStyle(font,new Color(0,0,0,0.55f),null,null,drawable);
        emailField=new TextField("",style);
        emailField.setAlignment(Align.center);
        emailField.setMessageText("email");
       // emailField.setText("alexey.e.kotov@gmail.com");
        passwordField=new TextField("",style);
        passwordField.setAlignment(Align.center);
        passwordField.setMessageText("password");


        emailField.setPosition(window.getX()+100,600);
        passwordField.setPosition(window.getX()+100,400);
        emailField.setWidth(window.getWidth()-300);
        emailField.setHeight(170);
        passwordField.setWidth(window.getWidth()-300);
        passwordField.setHeight(175);

        registerButton=new Buttons(window.getX()+140,220,"Register","Register",2.5f,st);
        logInButton=new Buttons(window.getWidth()-250,220,"Log in","Log in",2.5f,st);

        st.addActor(this);
        st.addActor(emailField);
        st.addActor(passwordField);
        st.addActor(registerButton);
        st.addActor(logInButton);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        window.draw(batch,parentAlpha);
        emailField.draw(batch,parentAlpha);
        passwordField.draw(batch,parentAlpha);
        registerButton.btn.draw(batch,parentAlpha);
        logInButton.btn.draw(batch,parentAlpha);
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Buttons getRegisterButton() {
        return registerButton;
    }

    public Buttons getLogInButton() {
        return logInButton;
    }

    public void becomeInvisible(){
        setVisible(false);
        registerButton.setVisible(false);
        logInButton.setVisible(false);
        emailField.setVisible(false);
        passwordField.setVisible(false);
    }

    public void becomeVisible(){
        setVisible(true);
        registerButton.setVisible(true);
        logInButton.setVisible(true);
        emailField.setVisible(true);
        passwordField.setVisible(true);
    }
}
