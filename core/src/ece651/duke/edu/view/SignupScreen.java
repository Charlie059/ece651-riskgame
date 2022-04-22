package ece651.duke.edu.view;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import ece651.duke.edu.RiskGameController;
public class SignupScreen extends BaseScreen {

    public SignupScreen(RiskGameController game) {
        super(game);
    }


    @Override
    public void show() {
        Table topicTable = createTable(true,null,200,stage);
        Table textFieldTable = createTable(true,null,0,stage);
        Table buttonTable = createTable(true,null,-200,stage);

        addLabelToTable(textFieldTable,"Email",soldierSkin,null,null, Align.left,1f,null,null);
        addTextFieldToTable(textFieldTable,"",tracerSkin,400,null,20,"Please Enter an \n valid Email Address",soldierSkin);
        textFieldTable.row().pad(10,0,10,0);
        addLabelToTable(textFieldTable,"Password",soldierSkin,null,null,Align.left,1f,null,null);
        addTextFieldToTable(textFieldTable,"",tracerSkin,400,null,20,"Please Enter Your Password, \n contains at least a capitol letter \n and a special symbol",soldierSkin);
        textFieldTable.row().pad(10,0,10,0);
        addLabelToTable(textFieldTable,"ReEnter Password",soldierSkin,null,null,Align.left,1f,null,null);
        addTextFieldToTable(textFieldTable,"",tracerSkin,400,null,20,"Please Enter Your Password,\n contains at least a capitol letter \n and a special symbol",soldierSkin);
        textFieldTable.row().pad(10,0,10,0);
        addLabelToTable(textFieldTable,"Verification Code",soldierSkin,null,null,Align.left,1f,null,null);
        addTextFieldToTable(textFieldTable,"",tracerSkin,400,null,20,"Please Enter Your Password, \n contains at least a capitol letter \n and a special symbol",soldierSkin);
    }


    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
