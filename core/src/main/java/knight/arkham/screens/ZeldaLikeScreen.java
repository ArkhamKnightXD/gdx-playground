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
import knight.arkham.helpers.GameData;
import knight.arkham.helpers.GameDataPreferencesHelper;
import knight.arkham.helpers.TileMapHelper;
import knight.arkham.objects.box2D.Box2DEnemy;
import knight.arkham.objects.box2D.ZeldaLikePlayer;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class ZeldaLikeScreen extends ScreenAdapter {
    private final Playground game;
    private final OrthographicCamera camera;
    private final World world;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final ZeldaLikePlayer player;
    private final Array<Box2DEnemy> enemies;
    public static final String GAME_DATA_FILENAME = "zelda-like";


    public ZeldaLikeScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, 0), true);

        player = new ZeldaLikePlayer(new Rectangle(500, 500, 32, 32), world);

        TextureAtlas textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        enemies = new Array<>();

        TextureRegion enemyRegion = textureAtlas.findRegion("goomba");

        TileMapHelper tileMapHelper = new TileMapHelper(world, enemyRegion, enemies, "maps/zelda-like/testing.tmx");

        mapRenderer = tileMapHelper.setupMap();

        camera = game.globalCamera;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update();

        manageGameData();

        game.manageExitTheGame();
    }

    private void manageGameData() {

        if (Gdx.input.isKeyJustPressed(Input.Keys.F1)){

            GameData gameDataToSave = new GameData("ZeldaLikeScreen", player.getPixelPosition());

            GameDataPreferencesHelper.saveGameData(GAME_DATA_FILENAME, gameDataToSave);
        }

        else if (Gdx.input.isKeyJustPressed(Input.Keys.F2)){

            Vector2 position = GameDataPreferencesHelper.loadGameData(GAME_DATA_FILENAME).position;

            player.setActualPosition(position.x, position.y);
        }
    }

    private void updateCameraPosition(){

        boolean isPlayerXPositionInsideMapBounds = player.getPixelPosition().x > 400;

        if (isPlayerXPositionInsideMapBounds)
            camera.position.set(player.getWorldPosition(), 0);

        else
            camera.position.set(400/PIXELS_PER_METER, player.getWorldPosition().y, 0);

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

//        player.draw(game.batch);

//        for (Box2DEnemy enemy : new Array.ArrayIterator<>(enemies))
//            enemy.draw(game.batch);

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
