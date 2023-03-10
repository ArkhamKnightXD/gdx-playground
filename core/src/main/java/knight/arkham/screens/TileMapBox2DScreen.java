package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.platformerBox2D.Box2DEnemy;
import knight.arkham.objects.platformerBox2D.Box2DPlayer;

public class TileMapBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Box2DPlayer player;
    private final Array<Box2DEnemy> enemies;


    public TileMapBox2DScreen() {
        game = Playground.INSTANCE;

        world = game.globalWorld;

        TextureAtlas textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        player = new Box2DPlayer(new Rectangle(100, 75, 32, 32), world, playerRegion);

        TextureRegion enemyRegion = textureAtlas.findRegion("goomba");

        enemies = new Array<>();

        mapRenderer = new TileMapHelper(world, enemyRegion, enemies).setupMap("maps/test.tmx");

        camera = game.globalCamera;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private void update(float deltaTime){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update(deltaTime);

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){


        MapProperties prop = mapRenderer.getMap().getProperties();

        int mapWidth = prop.get("width", Integer.class);
        int mapHeight = prop.get("height", Integer.class);
        int tilePixelWidth = prop.get("tilewidth", Integer.class);
        int tilePixelHeight = prop.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        Gdx.app.log("map width", String.valueOf(mapPixelWidth));
        Gdx.app.log("map height", String.valueOf(mapPixelHeight));

        boolean isPlayerInsideMapBounds = player.getActualPixelPosition().x > 75 && player.getActualPixelPosition().x < mapPixelWidth-510;

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getBody().getPosition().x,9.5f, 0);

        camera.update();

        mapRenderer.setView(camera);
    }


    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);

//        Agregar el new Array. ArraIterator resuelve el molesto warning, pero hace el código mós complejo de leer,
//        investigaré si vale la pena, de todas formas el foreach sigue funcionando igual
        for (Box2DEnemy enemy : new Array.ArrayIterator<>(enemies))
            enemy.draw(game.batch);

        game.batch.end();

        game.debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.getSprite().dispose();

        for (Box2DEnemy enemy : new Array.ArrayIterator<>(enemies))
            enemy.getSprite().dispose();
    }
}
