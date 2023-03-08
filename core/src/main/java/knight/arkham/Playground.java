package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.screens.MainMenuScreen;
import knight.arkham.screens.TileMapBox2DScreen;

import static knight.arkham.helpers.Constants.BOX2D_FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.BOX2D_FULL_SCREEN_WIDTH;

//En esta clase indico mi boilerplate code que normalmente definiría en cada game screen
public class Playground extends Game {
	public static Playground INSTANCE;

	public SpriteBatch batch;
	public BitmapFont font;

	public  OrthographicCamera globalCamera;

	public Viewport viewport;

	public Box2DDebugRenderer debugRenderer;

	public World globalWorld;


	private int screenWidth;
	private int screenHeight;

	public Playground() {

		INSTANCE = this;
	}

	@Override
	public void create() {

		globalWorld = new World(new Vector2(0, -10), true);

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		font = new BitmapFont();

//Debo indicarle a mi camara las dimensiones de mi pantalla divididas por mi PPM si no se vería muy pequeño
		globalCamera = new OrthographicCamera();

		// StretchViewport es el viewport más fácil de implementar, este es el que más fácil se adapta a todas las posibles
// resoluciones, debido a que defino las dimensiones de mi viewport, no tengo que definirle las dimensiones a la cámara.
		viewport = new FitViewport(BOX2D_FULL_SCREEN_WIDTH, BOX2D_FULL_SCREEN_HEIGHT, globalCamera);

		debugRenderer = new Box2DDebugRenderer();

		setScreen(new TileMapBox2DScreen());
	}


	public void manageExitTheGame() {

		if (Gdx.input.isKeyPressed(Input.Keys.F12))
			setScreen(new MainMenuScreen());

		else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	public int getScreenWidth() { return screenWidth; }

	public int getScreenHeight() { return screenHeight; }
}