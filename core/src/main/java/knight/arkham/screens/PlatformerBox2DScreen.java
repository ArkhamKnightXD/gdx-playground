package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.helpers.PlatformerContactListener;
import knight.arkham.objects.platformerBox2D.Box2DPlayer;
import knight.arkham.objects.platformerBox2D.Box2DStructure;
import knight.arkham.objects.platformerBox2D.Box2DEnemy;

import static knight.arkham.helpers.Constants.*;

public class PlatformerBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final Box2DStructure floor;
    private final Box2DStructure floor2;
    private final Box2DStructure floor3;
    private final Box2DStructure floor4;
    private final Box2DStructure slowSnowFloor;
    private final Box2DStructure dontJumpSnowFloor;
    private final Box2DStructure slipperySnowFloor;
    private final Box2DStructure movingFloor;
    private final Box2DStructure warpPipe;
    private final Box2DStructure warpPipe2;
    private final Box2DPlayer player;
    private final Box2DEnemy enemy;
    private final Box2DEnemy movingBlock;
    private final OrthographicCamera camera;
    private final Box2DDebugRenderer debugRenderer;
    private final World world;
    private final Texture background;

    public PlatformerBox2DScreen() {
        game = Playground.INSTANCE;

        world = new World(new Vector2(0, -10), true);

        PlatformerContactListener contactListener = new PlatformerContactListener(this);

        world.setContactListener(contactListener);

        debugRenderer = new Box2DDebugRenderer();

        player = new Box2DPlayer(new Rectangle(200, 600, 32, 32), world, ContactType.PLAYER);
        enemy = new Box2DEnemy(new Rectangle(0,32, 32, 32), world, ContactType.ENEMY);
        movingBlock = new Box2DEnemy(new Rectangle(-100,256, 32, 32), world, ContactType.ENEMY);

        floor = new Box2DStructure(new Rectangle(120,300, 200, 32), world, ContactType.FLOOR,  "images/wall.png");
        floor2 = new Box2DStructure(new Rectangle(400,200, 200, 32), world,ContactType.TRAMPOLINE, "images/wall.png");
        floor3 = new Box2DStructure(new Rectangle(-110,120, 200, 32), world, ContactType.FLOOR, "images/wall.png");
        floor4 = new Box2DStructure(new Rectangle(0,-16, FULL_SCREEN_WIDTH, 64), world, ContactType.FLOOR, "images/wall.png");

        warpPipe = new Box2DStructure(new Rectangle(-300,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");
        warpPipe2 = new Box2DStructure(new Rectangle(80,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");

        slowSnowFloor = new Box2DStructure(new Rectangle(1250,-50, 281, 283), world, ContactType.SNOWFLOOR, "images/big-snow-floor.png");
        dontJumpSnowFloor = new Box2DStructure(new Rectangle(1500,0, 186, 182), world, ContactType.HEAVYGRAVITYFLOOR, "images/medium-snow-floor.png");
        slipperySnowFloor = new Box2DStructure(new Rectangle(850,-25, 374, 96), world, ContactType.SLIPPERYFLOOR, "images/cold-floor.png");
        movingFloor = new Box2DStructure(new Rectangle(750,175, 96, 91), world, ContactType.MOVINGFLOOR, "images/little-floor.png");


//Debo indicarle a mi camara las dimensiones de mi pantalla divididas por mi PPM si no se vería muy pequeño
        camera = new OrthographicCamera(FULL_SCREEN_WIDTH / PIXELS_PER_METER,
                FULL_SCREEN_HEIGHT/PIXELS_PER_METER);
        background = new Texture("images/background.jpg");
    }

    private void update(){

        world.step(1 / 60f, 6, 2);

        if (Gdx.input.isKeyPressed(Input.Keys.F1)){
            player.getBody().setTransform(200/ PIXELS_PER_METER, 330 / PIXELS_PER_METER, 0);
            movingBlock.getBody().setTransform(-100/ PIXELS_PER_METER, 150 / PIXELS_PER_METER, 0);
            movingFloor.getBody().setTransform(750/ PIXELS_PER_METER, 200 / PIXELS_PER_METER, 0);
        }

        updateCameraPosition();

        player.update();

        enemy.update();

        movingFloor.update();

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

        game.batch.draw(background, -28, -12, background.getWidth()/ PIXELS_PER_METER, background.getHeight() / PIXELS_PER_METER);

        player.draw(game.batch);
        enemy.draw(game.batch);
        movingBlock.draw(game.batch);

        floor.draw(game.batch);
        floor2.draw(game.batch);
        floor3.draw(game.batch);
        floor4.draw(game.batch);

        warpPipe.draw(game.batch);
        warpPipe2.draw(game.batch);
        slowSnowFloor.draw(game.batch);
        slipperySnowFloor.draw(game.batch);
        dontJumpSnowFloor.draw(game.batch);
        movingFloor.draw(game.batch);

        game.batch.end();

        debugRenderer.render(world, camera.combined);
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        background.dispose();
        player.getSprite().dispose();
        enemy.getSprite().dispose();
        floor.getSprite().dispose();
        floor2.getSprite().dispose();
        floor3.getSprite().dispose();
        floor4.getSprite().dispose();
        warpPipe.getSprite().dispose();
        warpPipe2.getSprite().dispose();
        slipperySnowFloor.getSprite().dispose();
        slowSnowFloor.getSprite().dispose();
        dontJumpSnowFloor.getSprite().dispose();
        movingFloor.getSprite().dispose();
        movingBlock.getSprite().dispose();
    }

    public Box2DPlayer getPlayer() {return player;}

    public Box2DEnemy getEnemy() {return enemy;}

    public Box2DStructure getMovingFloor() {return movingFloor;}
}
