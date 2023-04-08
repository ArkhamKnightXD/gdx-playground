package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.GameDataHelper;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.box2D.Box2DEnemy;
import knight.arkham.objects.box2D.Box2DPlayer;
import knight.arkham.objects.sprites.SpritePlayer;

public class TileMapBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Array<Box2DEnemy> enemies;
    private final Box2DPlayer player;

    private final SpritePlayer mario;

    private final TileMapHelper tileMap;

    public static final String GAME_DATA_FILENAME = "tiled-player";


    public TileMapBox2DScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, -10), true);

        TextureAtlas textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");

        mario = new SpritePlayer(new Rectangle(500, 75, 32, 32), world, playerRegion);
        player = new Box2DPlayer(new Rectangle(450, 75, 32, 32), world, playerRegion);

        GameDataHelper.savePlayerPosition(player.toString(), GAME_DATA_FILENAME);

        TextureRegion enemyRegion = textureAtlas.findRegion("goomba");

        enemies = new Array<>();

        tileMap = new TileMapHelper(world, enemyRegion, enemies);

        mapRenderer = tileMap.setupMap("maps/test.tmx");

        camera = game.globalCamera;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private void update(float deltaTime) {

        world.step(1 / 60f, 6, 2);

        if (Gdx.input.isKeyPressed(Input.Keys.F3))
            player.setActualPosition(450, 75);

        updateCameraPosition();

        mario.update(deltaTime);
        player.update(deltaTime);

        manageGameData();

        game.manageExitTheGame();
    }

    private void updateCameraPosition() {

        boolean isPlayerInsideMapBounds = tileMap.checkIfPlayerIsInsideMapBounds(player.getActualPixelPosition());

        if (isPlayerInsideMapBounds)
            camera.position.set(player.getBody().getPosition().x, 9.5f, 0);

        camera.update();

        mapRenderer.setView(camera);
    }

    private void manageGameData() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F1))
            player.getBody().setTransform(GameDataHelper.loadPlayerPosition(GAME_DATA_FILENAME), 0);

        if (Gdx.input.isKeyJustPressed(Input.Keys.F2))
            GameDataHelper.savePlayerPosition(player.toString(), GAME_DATA_FILENAME);
    }


    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0, 0, 0, 0);

        mapRenderer.render();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        mario.draw(game.batch);
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
