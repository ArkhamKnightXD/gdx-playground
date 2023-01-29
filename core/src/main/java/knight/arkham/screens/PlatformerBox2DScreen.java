package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.objects.Box2DPlayer;
import knight.arkham.objects.Box2DStructure;

import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.FULL_SCREEN_WIDTH;

public class PlatformerBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final Box2DStructure floor;
    private final Box2DPlayer player;
    private final OrthographicCamera camera;

    private final Box2DDebugRenderer debugRenderer;

    private final World world;

    public PlatformerBox2DScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, -10), true);
        debugRenderer = new Box2DDebugRenderer();

        player = new Box2DPlayer(new Rectangle(200, 300, 32, 32), world);

        floor = new Box2DStructure(25, 200, 600, 32, ContactType.WALL, world);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update();

        resetGamePositions();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);

        camera.update();
    }

    private void resetGamePositions() {
        if (Gdx.input.isKeyPressed(Input.Keys.F1))
            player.getBody().getPosition().set(200, 600);
    }

    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);

        floor.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();
    }
}
