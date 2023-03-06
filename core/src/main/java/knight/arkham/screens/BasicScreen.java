package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

    private final Vector2 mousePosition;

    private int playerSpeed;

    private final Sound clickSound;

    private final Array<Rectangle> randomRectangles;

    private boolean isGoingUp;

    private boolean isUpAndDownMovementActive;


    public BasicScreen() {

        game = Playground.INSTANCE;

        playerTexture = new Texture("images/initial.png");

        screenClickCounter = 0;
        playerSpeed = 400;
        clickSound = Gdx.audio.newSound(Gdx.files.internal("fx/drop.wav"));

        randomRectangles = new Array<>();

        isGoingUp = true;

        isUpAndDownMovementActive = false;
        playerBody = new Rectangle(400, 300, 32, 32);
        mousePosition = new Vector2(0,0);
    }


    private void update(float deltaTime) {

        manageUserInput();

        playerMovement(deltaTime);

        screenBoundary();

        if (isUpAndDownMovementActive) {

            upAndDownMovement(deltaTime);
        }
    }

    //	La variable delta es mi deltaTime, osea el tiempo que hay entre el frame actual y el frame anterior
    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

        game.batch.begin();

        game.batch.draw(playerTexture, playerBody.x, playerBody.y, playerBody.width, playerBody.height);

        game.font.draw(game.batch, "Screen Touched: " + screenClickCounter,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 20);

        game.font.draw(game.batch, "Mouse Position: " + "X: " + mousePosition.x + " Y: "
                + mousePosition.y, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 40);

        game.font.draw(game.batch, "Screen Height: " + FULL_SCREEN_HEIGHT,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 60);

        game.font.draw(game.batch, "Screen Width: " + FULL_SCREEN_WIDTH,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 80);

        game.font.draw(game.batch, "Player Position: " + "X: " + playerBody.x + " Y: "
                + playerBody.y, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 100);

        game.font.draw(game.batch, "Player Size: " + "Width: " + playerBody.width + " Height: "
                + playerBody.height, FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 120);

        game.font.draw(game.batch, "Player Speed: " + playerSpeed,
                FULL_SCREEN_WIDTH - 300, FULL_SCREEN_HEIGHT - 140);


        for (Rectangle rectangle :  new Array.ArrayIterator<>(randomRectangles)) {

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

        game.manageExitTheGame();

        if (Gdx.input.justTouched()) {

            clickSound.play(0.1f);

            screenClickCounter++;
        }


        if (Gdx.input.isTouched()) {

//			Las posiciones del mouse empiezan en la esquina superior izquierda, al contrario,
//			de cuando dibujo con el batch que su punto de origen es la esquina inferior izquierda
            mousePosition.set(Gdx.input.getX(), Gdx.input.getY());

            playerBody.x = mousePosition.x;
            playerBody.y = mousePosition.y;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            isUpAndDownMovementActive = true;

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2)) {

            isUpAndDownMovementActive = false;
            playerSpeed = 400;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F3))
            generateRandomRectangles();

        if (Gdx.input.isKeyPressed(Input.Keys.F4)) {

            playerBody.width++;
            playerBody.height++;
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
            playerBody.x += playerSpeed * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.A))
            playerBody.x -= playerSpeed * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
            playerBody.y += playerSpeed * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.S))
            playerBody.y -= playerSpeed * deltaTime;
    }

    private void upAndDownMovement(float deltaTime) {

        if (playerBody.y < game.getScreenHeight() - playerBody.height && isGoingUp) {

            playerBody.y += playerSpeed * deltaTime;

            if (playerBody.y > game.getScreenHeight() - playerBody.height) {

                isGoingUp = false;

//				Cada vez que el bloque choque con la parte superior o inferior aumentare la velocidad de este
                playerSpeed *= 1.05;
            }
        }

        if (playerBody.y > 0 && !isGoingUp) {

            playerBody.y -= playerSpeed * deltaTime;

            if (playerBody.y < 0) {

                isGoingUp = true;

                playerSpeed *= 1.05;
            }
        }

    }

    private void xAxisMovement() {

//        La forma mas facil de hacer que un elemento cambie para la direccion contraria es darle un numero negativo
//        a la velocidad, en pocas palabras con la velocidad controlo la direccion de mi objeto
        playerBody.x += playerSpeed;

        if (playerBody.x > FULL_SCREEN_WIDTH - playerBody.width)
            playerSpeed = -10;

        if (playerBody.x < 0)
            playerSpeed = 10;
    }

    private void yAxisMovement(float deltaTime) {

        playerBody.y += playerSpeed;

        if (playerBody.y > FULL_SCREEN_HEIGHT - playerBody.height)
            playerSpeed = -10;

        if (playerBody.y < 0)
            playerSpeed = 10;
    }

    private void screenBoundary() {

//		En las posiciones superiores debo de resta el width y el height de la texture,
//		para que la figura no salga de la pantalla
        if (playerBody.x > FULL_SCREEN_WIDTH - playerBody.width)
            playerBody.x = FULL_SCREEN_WIDTH - playerBody.width;

        if (playerBody.x < 0)
            playerBody.x = 0;

        if (playerBody.y > FULL_SCREEN_HEIGHT - playerBody.height)
            playerBody.y = FULL_SCREEN_HEIGHT - playerBody.height;

        if (playerBody.y < 0)
            playerBody.y = 0;
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