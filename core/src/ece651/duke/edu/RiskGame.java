package ece651.duke.edu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//Game Implement an ApplicationListener inside
public class RiskGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    MainMenuScreen mainMenuScreen;

    //Run Once
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(); // use libGDX's default Arial font
        this.mainMenuScreen = new MainMenuScreen(this);
        this.setScreen(this.mainMenuScreen);
    }

    //Run EveryFrame
    public void render() {
        //Call ApplicationListener's render function
        super.render(); // important!
    }

    public void dispose() {
        batch.dispose();
        font.dispose();
        this.mainMenuScreen.gameScreen.dispose();
    }
}
