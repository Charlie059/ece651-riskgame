package ece651.duke.edu.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import ece651.duke.edu.RiskGameController;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;


public class LoginScreen extends  BaseScreen {



    public LoginScreen(RiskGameController game) {
        super(game);


    }
    @Override
    public void show() {
        //Background



        //Everything go inside the table
        Table welcomeTable = createTable(true,null,200,stage);
        Table textTable =createTable(true,null,0,stage);
        Table buttonTable = createTable(true,null,-200,stage);

        addLabelToTable(welcomeTable,"Welcome to Blue Devil Battle",soldierSkin,0,300,Align.center,1.5f,null,null);

        //Label and TextField
        addLabelToTable(textTable,"[RED]UserName",soldierSkin,null,null,Align.left,1f,null,null);
        addTextFieldToTable(textTable,"",tracerSkin,400,null,10,"Please Entering Your Email",soldierSkin);
        textTable.row().pad(10,0,10,0);
        addLabelToTable(textTable,"[RED]Password",soldierSkin,null,null,Align.left,1f,null,null);
        addTextFieldToTable(textTable,"",tracerSkin,400,null,null,"Please Entering Your Password",soldierSkin);

        //Button
        TextButton signin = new TextButton("Sign In",soldierSkin);
        TextButton signup = new TextButton("Sign Up", soldierSkin);
        signup.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.screenOrchestrator(RiskGameController.SIGNUPSCREEN);
            }
        });
        buttonTable.add(signin);
        buttonTable.add(signup);

    }


    @Override
    public void resize(int width, int height) {

    }

}
