package ece651.duke.edu.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import ece651.duke.edu.RiskGameController;

public abstract class BaseScreen implements Screen {
    protected RiskGameController game;
    protected Stage stage;
    protected Skin soldierSkin = new Skin(Gdx.files.internal("soldierSkin/star-soldier-ui.json"));
    protected Skin terraSkin = new Skin(Gdx.files.internal("terraSkin/terra-mother-ui.json"));
    protected Skin tracerSkin = new Skin(Gdx.files.internal("tracerSkin/tracer-ui.json"));
    protected Stage backgroundStage;
    private Texture backgroundImage = new Texture(Gdx.files.internal("WelcomePage/welcomeBackground.jpeg"));
    /**
     * View Port Reference
     * fitViewPort: Fit ratio of pictures (adj win then black)
     * fillViewPort: Fit ratio of pictures (adj win then cover)
     * extendViewPort: span pictures when adj win
     * screenViewPort: Idea for UI (won't change of Menu when adj win)
     */
    public BaseScreen(RiskGameController game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        soldierSkin.get(Label.LabelStyle.class).font.getData().markupEnabled = true;

        this.backgroundStage =  new Stage(new FillViewport(1920,1080));

    }

    @Override
    public void show() {


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //backgroundStage.getViewport().apply();
        backgroundStage.act(Gdx.graphics.getDeltaTime());
        backgroundStage.getViewport().apply();
        backgroundStage.getBatch().begin();
        backgroundStage.getBatch().draw(backgroundImage,0,0);
        backgroundStage.getBatch().end();
        backgroundStage.draw();

        stage.getViewport().apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        backgroundStage.getViewport().update(width,height,true);
        stage.getViewport().update(width,height,true);//true while centering

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
        stage.dispose();
        backgroundStage.dispose();
    }

    protected Table createTable(Boolean fillParent, Integer X, Integer Y, Stage stage) {
        Table table = new Table();
        table.setFillParent(fillParent);
        table.setDebug(false);
        if (X != null) table.setX(X);
        if (Y != null) table.setY(Y);
        stage.addActor(table);
        return table;
    }

    protected void addLabelToTable(Table table, String content, Skin skin, Integer X, Integer Y, Integer align, float fontScale, String textToolContent, Skin textToolSkin) {
        Label label = new Label(content, skin);
        if (X != null) label.setX(X);
        if (Y != null) label.setY(Y);
        label.setAlignment(align);
        label.setFontScale(fontScale);
        if(textToolContent!=null){
            TextTooltip textTooltip = new TextTooltip(textToolContent,textToolSkin);
            textTooltip.setInstant(true);
            label.addListener(textTooltip);
        }
        table.add(label).left();

    }

    protected void addTextFieldToTable(Table table, String content, Skin skin, Integer width, Integer height, Integer space, String textToolContect, Skin textToolSkin){
        TextField textField = new TextField(content,skin);
        if(textToolContect!=null){
            TextTooltip textTooltip = new TextTooltip(textToolContect,textToolSkin);
            textTooltip.setInstant(true);
            textField.addListener(textTooltip);
        }
        Cell cell = table.add(textField).right();
        if(width!=null) cell.width(width);
        if(height!=null) cell.height(height);
        if(space!=null) cell.space(space);
    }

}
