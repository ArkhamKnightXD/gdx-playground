package knight.arkham;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class FirstScreen extends ScreenAdapter {

	private final Playground game = Playground.INSTANCE;
	private int screenClickCounter;
	
	private final Texture initialTexture;

	private float positionX;
	private float positionY;

	private float mousePositionX;
	private float mousePositionY;

	private int textureWidth;
	private int textureHeight;

	private final int playerSpeed;
	
	private final Sound clickSound;

	private final Array<Rectangle> randomRectangles;
	
//	private final OrthographicCamera camera;


	public FirstScreen(/*OrthographicCamera globalCamera*/) {

		//		camera = globalCamera;

		positionX = 275;
		positionY = 300;
		textureWidth = 32;
		textureHeight = 32;
		initialTexture = new Texture("images/initial.png");

		screenClickCounter = 0;
		playerSpeed = 250;
		clickSound = Gdx.audio.newSound(Gdx.files.internal("fx/drop.wav"));

		randomRectangles = new Array<>();
	}

	@Override
	public void show() {
		// Prepare your screen here.
	}

//	La variable delta es mi deltaTime, osea el tiempo que hay entre el frame actual y el frame anterior
	@Override
	public void render(float delta) {

		ScreenUtils.clear(0,0,0,0);

//		camera.update();

		game.batch.begin();

		game.batch.draw(initialTexture, positionX, positionY, textureWidth, textureHeight);

		game.font.draw(game.batch, "Playground", 275, 250);
		game.font.draw(game.batch, "Screen Touched: " + screenClickCounter, 275, 220);
		game.font.draw(game.batch, "Mouse Position: " + "X: " +mousePositionX+ " Y: " +mousePositionY, 275, 200);
		game.font.draw(game.batch, "Screen Height: " + game.getScreenHeight(), 275, 180);
		game.font.draw(game.batch, "Screen Width: " + game.getScreenWidth(), 275, 150);

		for (Rectangle rectangle: randomRectangles) {

			game.batch.draw(initialTexture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}


		game.batch.end();

		if (Gdx.input.justTouched()){

			clickSound.play(0.1f);

			screenClickCounter++;
		}

		if (Gdx.input.isKeyJustPressed(Input.Keys.R))
			generateRandomRectangles();


		if (Gdx.input.isTouched()){

//			Las posiciones del mouse empiezan en la esquina superior izquierda, al contrario, de cuando dibujo con el
//			batch que su punto de origen es la esquina inferior izquierda
			mousePositionX = Gdx.input.getX();
			mousePositionY = Gdx.input.getY();

//			positionX = mousePositionX;
//			positionY = mousePositionY;

			textureWidth++;
			textureHeight++;

		}

		playerMovement(delta);

		closeGame();

	}


	private void generateRandomRectangles(){

		Rectangle rectangle = new Rectangle();

		rectangle.width = 16;
		rectangle.height = 16;
		rectangle.x = MathUtils.random(0, 640-16);
		rectangle.y = MathUtils.random(0, 480-16);

		randomRectangles.add(rectangle);
	}

	private void closeGame() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){

			Gdx.app.exit();
			dispose();
		}
	}

	private void playerMovement(float deltaTime) {

//		Multiplicamos mi variable de velocidad por el tiempo que hubo entre el frame actual y el anterior,
//		para obtener un mejor movimiento
		if (Gdx.input.isKeyPressed(Input.Keys.D))
			positionX += playerSpeed * deltaTime;

		if (Gdx.input.isKeyPressed(Input.Keys.A))
			positionX -= playerSpeed * deltaTime;

		if (Gdx.input.isKeyPressed(Input.Keys.W))
			positionY += playerSpeed * deltaTime;

		if (Gdx.input.isKeyPressed(Input.Keys.S))
			positionY -= playerSpeed * deltaTime;

		screenBoundary();
	}

	private void screenBoundary() {

//		En las posiciones superiores debo de resta el width y el height de la texture,
//		para que la figura no salga de la pantalla
		if (positionX > game.getScreenWidth() - textureWidth)
			positionX = game.getScreenWidth() - textureWidth;

		if (positionX < 0)
			positionX = 0;

		if (positionY > game.getScreenHeight() - textureHeight)
			positionY = game.getScreenHeight() - textureHeight;

		if (positionY < 0)
			positionY = 0;
	}


	@Override
	public void hide() {

		dispose();
	}

	@Override
	public void dispose() {

		initialTexture.dispose();
		clickSound.dispose();
	}
}