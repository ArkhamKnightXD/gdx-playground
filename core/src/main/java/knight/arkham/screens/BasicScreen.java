package knight.arkham.screens;

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
import knight.arkham.Playground;

import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.FULL_SCREEN_WIDTH;

public class BasicScreen extends ScreenAdapter {

    private final Playground game;
    private int screenClickCounter;

    private final Texture playerTexture;

    private final Rectangle playerBody;

    private float positionX;
    private float positionY;

    private float mousePositionX;
    private float mousePositionY;

    private int playerSpeed;

    private final Sound clickSound;

    private final Array<Rectangle> randomRectangles;

    private boolean isGoingUp;

    private boolean isRandomMovementActive;


//	private final OrthographicCamera camera;


    public BasicScreen(/*OrthographicCamera globalCamera*/) {

        game = Playground.INSTANCE;

        //		camera = globalCamera;

        positionX = 400;
        positionY = 300;
        playerTexture = new Texture("images/initial.png");

        screenClickCounter = 0;
        playerSpeed = 400;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("fx/drop.wav"));

        randomRectangles = new Array<>();

        isGoingUp = true;

        isRandomMovementActive = false;
        playerBody = new Rectangle(positionX, positionY, 32, 32);
    }


    private void update(float deltaTime) {

        playerBody.x = positionX;
        playerBody.y = positionY;

        manageUserInput();

        playerMovement(deltaTime);

        if (isRandomMovementActive) {

//            xAxisMovement();

            randomMovement(deltaTime);
        }
    }

    //	La variable delta es mi deltaTime, osea el tiempo que hay entre el frame actual y el frame anterior
    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

//		camera.update();

        game.batch.begin();

        game.batch.draw(playerTexture, playerBody.x, playerBody.y, playerBody.width, playerBody.height);

        game.font.draw(game.batch, "Screen Touched: " + screenClickCounter,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 20);

        game.font.draw(game.batch, "Mouse Position: " + "X: " + mousePositionX + " Y: "
                + mousePositionY, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 40);

        game.font.draw(game.batch, "Screen Height: " + game.getScreenHeight(),
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 60);

        game.font.draw(game.batch, "Screen Width: " + game.getScreenWidth(),
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 80);

        game.font.draw(game.batch, "Player Position: " + "X: " + positionX + " Y: "
                + positionY, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 100);

        game.font.draw(game.batch, "Player Size: " + "Width: " + playerBody.width + " Height: "
                + playerBody.height, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 120);

        game.font.draw(game.batch, "Player Speed: " + playerSpeed,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 140);


        for (Rectangle rectangle : randomRectangles) {

            game.batch.draw(playerTexture, rectangle.x, rectangle.y, rectangle.width, rectangle.height);

            removeRectangles(randomRectangles, rectangle);
        }

        game.batch.end();
    }


    private void removeRectangles(Array<Rectangle> rectangles, Rectangle actualRectangle) {

        if (playerBody.overlaps(actualRectangle))
            rectangles.pop();
    }

    private void manageUserInput() {

        game.goBackToMenu();

        if (Gdx.input.justTouched()) {

            clickSound.play(0.1f);

            screenClickCounter++;
        }


        if (Gdx.input.isTouched()) {

//			Las posiciones del mouse empiezan en la esquina superior izquierda, al contrario,
//			de cuando dibujo con el batch que su punto de origen es la esquina inferior izquierda
            mousePositionX = Gdx.input.getX();
            mousePositionY = Gdx.input.getY();

			positionX = mousePositionX;
			positionY = mousePositionY;
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isRandomMovementActive = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {

            isRandomMovementActive = false;
            playerSpeed = 400;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3))
            generateRandomRectangles();


        if (Gdx.input.isKeyPressed(Input.Keys.F4)) {

            playerBody.width++;
            playerBody.height++;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            Gdx.app.exit();
            dispose();
        }
    }


    private void generateRandomRectangles() {

        Rectangle rectangle = new Rectangle();

        rectangle.width = 16;
        rectangle.height = 16;
        rectangle.x = MathUtils.random(0, 800 - 16);
        rectangle.y = MathUtils.random(0, 600 - 16);

        randomRectangles.add(rectangle);
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

    private void randomMovement(float deltaTime) {

        if (positionY < game.getScreenHeight() - playerBody.height && isGoingUp) {

            positionY += playerSpeed * deltaTime;

            if (positionY > game.getScreenHeight() - playerBody.height) {

                isGoingUp = false;

//				Cada vez que el bloque choque con la parte superior o inferior aumentare la velocidad de este
                playerSpeed *= 1.05;
            }
        }

        if (positionY > 0 && !isGoingUp) {

            positionY -= playerSpeed * deltaTime;

            if (positionY < 0) {

                isGoingUp = true;

                playerSpeed *= 1.05;
            }
        }

    }

    private void xAxisMovement() {

//        La forma mas facil de hacer que un elemento cambie para la direccion contraria es darle un numero negativo
//        a la velocidad, en pocas palabras con la velocidad controlo la direccion de mi objeto
        positionX += playerSpeed;

        if (positionX > game.getScreenWidth() - playerBody.width)
            playerSpeed = -10;

        if (positionX < 0)
            playerSpeed = 10;

    }

    private void screenBoundary() {

//		En las posiciones superiores debo de resta el width y el height de la texture,
//		para que la figura no salga de la pantalla
        if (positionX > game.getScreenWidth() - playerBody.width)
            positionX = game.getScreenWidth() - playerBody.width;

        if (positionX < 0)
            positionX = 0;

        if (positionY > game.getScreenHeight() - playerBody.height)
            positionY = game.getScreenHeight() - playerBody.height;

        if (positionY < 0)
            positionY = 0;
    }


    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        playerTexture.dispose();
        clickSound.dispose();
    }
}