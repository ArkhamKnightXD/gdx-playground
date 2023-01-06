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
    private final PhysicsBall physicsBall;
    private final PhysicsWall bottomWall;
    private final PhysicsWall topWall;
    private final PhysicsWall rightWall;
    private final PhysicsWall leftWall;

    public Box2DScreen() {

        game = Playground.INSTANCE;

        GameContactListener gameContactListener = new GameContactListener(this);

        gameWorld = new World(new Vector2(0, 0), false);
        gameWorld.setContactListener(gameContactListener);

        physicsBall = new PhysicsBall( this);
        topWall = new PhysicsWall(FULL_SCREEN_HEIGHT, MID_SCREEN_WIDTH,
                FULL_SCREEN_WIDTH, 64, ContactType.WALL, this);

        bottomWall = new PhysicsWall(0, MID_SCREEN_WIDTH,
                FULL_SCREEN_WIDTH, 64,ContactType.WALL, this);

        rightWall = new PhysicsWall(MID_SCREEN_HEIGHT, FULL_SCREEN_WIDTH, 64,
                FULL_SCREEN_HEIGHT, ContactType.SIDEWALL, this);

        leftWall = new PhysicsWall(MID_SCREEN_HEIGHT, 0, 64,
                FULL_SCREEN_HEIGHT, ContactType.SIDEWALL, this);
    }


    private void update(){

// el 1/60 es porque el juego corre a 60fps, esta linea es fundamental para que haya movimiento en nuestra scene
        gameWorld.step(1/60f, 6, 2);

        game.goBackToMenu();

        physicsBall.update();
    }

    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Press F12 For Main Menu", 50, FULL_SCREEN_HEIGHT-40);

        bottomWall.render(game.batch);

        physicsBall.render(game.batch);

        topWall.render(game.batch);

        rightWall.render(game.batch);
        leftWall.render(game.batch);

        game.batch.end();

    }


    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }

    public PhysicsBall getPhysicsBall() {return physicsBall;}

    public World getGameWorld() {
        return gameWorld;
    }
}
