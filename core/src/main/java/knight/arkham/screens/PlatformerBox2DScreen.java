package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.helpers.PlatformerContactListener;
import knight.arkham.objects.Box2DPlayer;
import knight.arkham.objects.Box2DStructure;
import knight.arkham.objects.Enemy;

import static knight.arkham.helpers.Constants.*;

public class PlatformerBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final Box2DStructure floor;
    private final Box2DStructure floor2;
    private final Box2DStructure floor3;
    private final Box2DStructure floor4;
    private final Box2DStructure warpPipe;
    private final Box2DStructure warpPipe2;
    private final Box2DPlayer player;
    private final Enemy enemy;
    private final OrthographicCamera camera;

    private final Box2DDebugRenderer debugRenderer;

    private final World world;

    public PlatformerBox2DScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, -10), true);

        PlatformerContactListener contactListener = new PlatformerContactListener(this);

        world.setContactListener(contactListener);

        debugRenderer = new Box2DDebugRenderer();

        player = new Box2DPlayer(new Rectangle(200, 600, 32, 32), world);
        enemy = new Enemy(new Rectangle(0,32, 32, 32), world);


        floor = new Box2DStructure(new Rectangle(120,300, 200, 32), world, ContactType.FLOOR, "images/wall.png");
        floor2 = new Box2DStructure(new Rectangle(400,200, 200, 32), world,ContactType.TRAMPOLINE, "images/wall.png");
        floor3 = new Box2DStructure(new Rectangle(-110,120, 200, 32), world, ContactType.FLOOR, "images/wall.png");
        floor4 = new Box2DStructure(new Rectangle(0,0, FULL_SCREEN_WIDTH, 32), world, ContactType.FLOOR, "images/wall.png");

        warpPipe = new Box2DStructure(new Rectangle(-300,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");
        warpPipe2 = new Box2DStructure(new Rectangle(80,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");

//Debo indicarle a mi camara las dimensiones de mi pantalla divididas por mi PPM sino se veria muy pequeño
        camera = new OrthographicCamera(FULL_SCREEN_WIDTH / PIXELS_PER_METER,
                FULL_SCREEN_HEIGHT/PIXELS_PER_METER);
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        updateCameraPosition();

        player.update();
        enemy.update();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBody().getPosition().x, player.getBody().getPosition().y, 0);

        camera.update();
    }


    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        player.draw(game.batch);
        enemy.draw(game.batch);

        floor.draw(game.batch);
        floor2.draw(game.batch);
        floor3.draw(game.batch);
        floor4.draw(game.batch);
        warpPipe.draw(game.batch);
        warpPipe2.draw(game.batch);

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
        floor.dispose();
        floor2.dispose();
        floor3.dispose();
        floor4.dispose();
        warpPipe.dispose();
        warpPipe2.dispose();
    }

    public Box2DPlayer getPlayer() {return player;}

    public Enemy getEnemy() {return enemy;}
}
