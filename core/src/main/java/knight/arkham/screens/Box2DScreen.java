package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.helpers.GameContactListener;
import knight.arkham.objects.PhysicsBall;
import knight.arkham.objects.PhysicsWall;
import static knight.arkham.helpers.Constants.*;

public class Box2DScreen extends ScreenAdapter {
    private final Playground game;
    private final World gameWorld;
    private final PhysicsBall ball;
    private final PhysicsWall bottomWall;
    private final PhysicsWall topWall;
    private final PhysicsWall rightWall;
    private final PhysicsWall leftWall;

    public Box2DScreen() {

        game = Playground.INSTANCE;

        GameContactListener gameContactListener = new GameContactListener(this);

        gameWorld = new World(new Vector2(0, 0), false);
        gameWorld.setContactListener(gameContactListener);

        ball = new PhysicsBall( this);
        topWall = new PhysicsWall(FULL_SCREEN_HEIGHT, MID_SCREEN_WIDTH,
                FULL_SCREEN_WIDTH, 64, ContactType.WALL, gameWorld);

        bottomWall = new PhysicsWall(0, MID_SCREEN_WIDTH,
                FULL_SCREEN_WIDTH, 64,ContactType.WALL, gameWorld);

        rightWall = new PhysicsWall(MID_SCREEN_HEIGHT, FULL_SCREEN_WIDTH, 64,
                FULL_SCREEN_HEIGHT, ContactType.SIDEWALL, gameWorld);

        leftWall = new PhysicsWall(MID_SCREEN_HEIGHT, 0, 64,
                FULL_SCREEN_HEIGHT, ContactType.SIDEWALL, gameWorld);
    }


    private void update(){

// el 1/60 es porque el juego corre a 60fps, esta línea es fundamental para que haya movimiento en nuestra scene
        gameWorld.step(1/60f, 6, 2);

        game.manageExitTheGame();

        ball.update();
    }

    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Press F12 For Main Menu", 50, FULL_SCREEN_HEIGHT-40);

        bottomWall.draw(game.batch);

        ball.draw(game.batch);

        topWall.draw(game.batch);

        rightWall.draw(game.batch);
        leftWall.draw(game.batch);

        game.batch.end();
    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        ball.getBallTexture().dispose();
        topWall.getWallTexture().dispose();
        bottomWall.getWallTexture().dispose();
        leftWall.getWallTexture().dispose();
        rightWall.getWallTexture().dispose();
    }

    public PhysicsBall getBall() {return ball;}

    public World getGameWorld() {
        return gameWorld;
    }
}
