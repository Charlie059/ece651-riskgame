package ece651.duke.edu.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import ece651.duke.edu.RiskGameController;

import java.util.Iterator;

public class GameScreen implements Screen {
	final RiskGameController game;
	//Reference Assets
	//Texture represent image stored in video ram
	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;
	//Camera and SpriteBatch
	private OrthographicCamera camera;
	private SpriteBatch batch;

	//Rectangle represent Bucket
	private Rectangle bucket;//Note that it should import from gdx.math

	//Add RainDrop
	//Note Array is gdx's own ArrayList, minumum garbage as much as possible
	private Array<Rectangle> raindrops;
	private long lastDropTime;//in nanoseconds, use long



	public GameScreen(final RiskGameController game){
		this.game = game;
//		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
		dropImage = new Texture(Gdx.files.internal("drop.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("waterdrop24.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		// start the playback of the background music immediately
		rainMusic.setLooping(true);
		//rainMusic.play(); Has been put into Show() function

		//Create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);

		//Instantiate The Rectangle and specify the initial values
		bucket = new Rectangle();
		//Rendering the bottom left corner of the texture
		//rendering x-axis rightwards
		bucket.x = 800/2 - 64/2;
		//rendering y-axis upwards
		bucket.y = 20;
		bucket.width = 64;
		bucket.height = 64;

		// create the raindrops array and spawn the first raindrop
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}


	/**
	 * Render
	 */
	@Override
	public void render (float delta) {
		//clear screen with dark blue color .clear(r,g,b,a)
		ScreenUtils.clear(0,0,0.2f,1);

		//Recompute Camera Matrix
		camera.update();

		//Render the bucket
		//Use projection matrix of coordinate system of camera
		game.batch.setProjectionMatrix(camera.combined);

		//OpenGL want as many images as possible to render at a time
		game.batch.begin();
		game.batch.draw(bucketImage,bucket.x,bucket.y);
		for(Rectangle raindrop: raindrops) {
			game.batch.draw(dropImage, raindrop.x, raindrop.y);

		}
		//...
		//Here drawing more requests
		game.batch.end();


		//Move of Bucket
		//When touch the screen
		if(Gdx.input.isTouched()) {
			//Get Touch Position in Vector3
			//Note that Vector3 should be minimum created in Android App
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

			//Transfrom touchPos to camera projection
			camera.unproject(touchPos);
			bucket.x = touchPos.x - 64 / 2;
		}

		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200 * Gdx.graphics.getDeltaTime();
		if(bucket.x < 0) bucket.x = 0;
		if(bucket.x > 800 - 64) bucket.x = 800 - 64;
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnRaindrop();
		for (Iterator<Rectangle> iter = raindrops.iterator(); iter.hasNext(); ) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if(raindrop.y + 64 < 0) iter.remove();
			if(raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}


	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800-64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}


	@Override
	public void show() {
		rainMusic.play();

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

	//Close the App textures sound and music
	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
//		game.batch.dispose();
	}
}
