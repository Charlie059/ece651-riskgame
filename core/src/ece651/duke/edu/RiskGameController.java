package ece651.duke.edu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import ece651.duke.edu.view.LoginScreen;
import ece651.duke.edu.view.MainMenuScreen;
import ece651.duke.edu.view.SignupScreen;

/**
 * GAME ORCHESTRATOR
 */

//Game Implement an ApplicationListener inside
public class RiskGameController extends Game {
    /**
     * VIEW
     */
    private LoginScreen loginScreen;
    public final static int LOGINSCREEN = 0;
    private SignupScreen signupScreen;
    public final static int SIGNUPSCREEN = 1;
    private MainMenuScreen mainMenuScreen;
    public final static int MAINMENUSCREEN = 2;

    public SpriteBatch batch;
    public BitmapFont font;


    /**
     * RUN ONCE
     */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.loginScreen = new LoginScreen(this);
        this.mainMenuScreen = new MainMenuScreen(this);
        this.setScreen(this.loginScreen);
    }

    /**
     * RUN EACH FRAME
     */
    public void render() {
        //Call ApplicationListener's render function
        super.render(); // important!
    }

    /**
     * NEED DISPOSE VIEW SCREEN SPECIFICALLY
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
        this.mainMenuScreen.gameScreen.dispose();
    }

    //Screen Controlling
    public void screenOrchestrator(int voice){
        switch(voice){
            case LOGINSCREEN:
                if(loginScreen == null) loginScreen = new LoginScreen(this);
                this.setScreen(loginScreen);
                break;
            case MAINMENUSCREEN:
                if(mainMenuScreen ==null) mainMenuScreen = new MainMenuScreen(this);
                this.setScreen(mainMenuScreen);
                break;
            case SIGNUPSCREEN:
                if(signupScreen == null) signupScreen = new SignupScreen(this);
                this.setScreen(signupScreen);
                break;
        }
    }
}
