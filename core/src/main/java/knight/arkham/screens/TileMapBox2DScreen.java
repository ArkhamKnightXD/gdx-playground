package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.platformerBox2D.Box2DEnemy;
import knight.arkham.objects.platformerBox2D.Box2DPlayer;

import static knight.arkham.helpers.Constants.*;

public class TileMapBox2DScreen extends ScreenAdapter {
    private final Playground game;

    private final OrthographicCamera camera;
    private final Box2DDebugRenderer debugRenderer;
    private final World world;

    private final OrthogonalTiledMapRenderer mapRenderer;

    private final Box2DPlayer player;
    private Array<Box2DEnemy> enemies;


    public TileMapBox2DScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, -10), true);

        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera(FULL_SCREEN_WIDTH / PIXELS_PER_METER,
                FULL_SCREEN_HEIGHT/PIXELS_PER_METER);

        mapRenderer = new TileMapHelper(this).setupMap();
        player = new Box2DPlayer(new Rectangle(100, 75, 32, 32), world, ContactType.PLAYER);
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);

        camera.update();

        mapRenderer.setView(camera);
    }


    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);

        for (Box2DEnemy enemy : enemies)
            enemy.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.getSprite().dispose();

        for (Box2DEnemy enemy :enemies)
            enemy.getSprite().dispose();
    }

    public World getWorld() {return world;}

    public void setEnemies(Array<Box2DEnemy> enemies) {this.enemies = enemies;}
}
