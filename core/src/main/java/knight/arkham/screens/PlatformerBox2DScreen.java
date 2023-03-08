package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.helpers.ContactType;
import knight.arkham.helpers.PlatformerContactListener;
import knight.arkham.objects.platformerBox2D.Box2DKinematicStructure;
import knight.arkham.objects.platformerBox2D.Box2DPlayer;
import knight.arkham.objects.platformerBox2D.Box2DStaticStructure;
import knight.arkham.objects.platformerBox2D.Box2DEnemy;

import static knight.arkham.helpers.Constants.*;

public class PlatformerBox2DScreen extends ScreenAdapter {
    private final Playground game;
    private final Box2DStaticStructure floor;
    private final Box2DStaticStructure floor2;
    private final Box2DStaticStructure floor3;
    private final Box2DStaticStructure floor4;
    private final Box2DStaticStructure slowSnowFloor;
    private final Box2DStaticStructure dontJumpSnowFloor;
    private final Box2DStaticStructure slipperySnowFloor;
    private final Box2DKinematicStructure movingFloor;
    private final Box2DStaticStructure warpPipe;
    private final Box2DStaticStructure warpPipe2;

    private final Array<Box2DStaticStructure> structures;
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

        TextureAtlas textureAtlas = new TextureAtlas("images/atlas/Mario_and_Enemies.pack");

        TextureRegion playerRegion = textureAtlas.findRegion("little_mario");
        TextureRegion enemyRegion = textureAtlas.findRegion("goomba");

        player = new Box2DPlayer(new Rectangle(200, 600, 32, 32), world, playerRegion);
        enemy = new Box2DEnemy(new Rectangle(0,32, 32, 32), world, enemyRegion);
        movingBlock = new Box2DEnemy(new Rectangle(-100,256, 32, 32), world, enemyRegion);

        floor = new Box2DStaticStructure(new Rectangle(120,300, 200, 32), world, ContactType.FLOOR,  "images/wall.png");
        floor2 = new Box2DStaticStructure(new Rectangle(400,200, 200, 32), world,ContactType.TRAMPOLINE, "images/wall.png");
        floor3 = new Box2DStaticStructure(new Rectangle(-110,120, 200, 32), world, ContactType.FLOOR, "images/wall.png");
        floor4 = new Box2DStaticStructure(new Rectangle(0,-16, FULL_SCREEN_WIDTH, 64), world, ContactType.FLOOR, "images/wall.png");

        warpPipe = new Box2DStaticStructure(new Rectangle(-300,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");
        warpPipe2 = new Box2DStaticStructure(new Rectangle(80,46, 64, 64), world, ContactType.PIPE, "images/pipe.png");

        slowSnowFloor = new Box2DStaticStructure(new Rectangle(1250,-50, 281, 283), world, ContactType.SNOWFLOOR, "images/big-snow-floor.png");
        dontJumpSnowFloor = new Box2DStaticStructure(new Rectangle(1500,0, 186, 182), world, ContactType.HEAVYGRAVITYFLOOR, "images/medium-snow-floor.png");
        slipperySnowFloor = new Box2DStaticStructure(new Rectangle(850,-25, 374, 96), world, ContactType.SLIPPERYFLOOR, "images/cold-floor.png");

        structures = getStructures();

        movingFloor = new Box2DKinematicStructure(new Rectangle(750,175, 96, 91), world, "images/little-floor.png");

        background = new Texture("images/background.jpg");

        camera = game.globalCamera;
    }

    @Override
    public void resize(int width, int height) {

        game.viewport.update(width, height);
    }

    private Array<Box2DStaticStructure> getStructures() {

        final Array<Box2DStaticStructure> structures;

        structures = new Array<>();

        structures.add(floor);
        structures.add(floor2);
        structures.add(floor3);
        structures.add(floor4);
        structures.add(warpPipe);
        structures.add(warpPipe2);
        structures.add(slowSnowFloor);
        structures.add(slipperySnowFloor);
        structures.add(dontJumpSnowFloor);

        return structures;
    }

    private void update(float deltaTime){

        world.step(1 / 60f, 6, 2);

        if (Gdx.input.isKeyPressed(Input.Keys.F1)){
            player.getBody().setTransform(200/ PIXELS_PER_METER, 330 / PIXELS_PER_METER, 0);
            movingBlock.getBody().setTransform(-100/ PIXELS_PER_METER, 150 / PIXELS_PER_METER, 0);
            movingFloor.getBody().setTransform(750/ PIXELS_PER_METER, 200 / PIXELS_PER_METER, 0);
        }

        updateCameraPosition();

        player.update(deltaTime);

        enemy.update(deltaTime);

        movingFloor.update();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBody().getPosition(), 0);

        camera.update();
    }


    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.batch.draw(background, -28, -12, background.getWidth()/ PIXELS_PER_METER, background.getHeight() / PIXELS_PER_METER);
        player.draw(game.batch);
        enemy.draw(game.batch);
        movingBlock.draw(game.batch);

        for (Box2DStaticStructure structure :new Array.ArrayIterator<>(structures))
            structure.draw(game.batch);

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

        movingFloor.getSprite().dispose();
        movingBlock.getSprite().dispose();

        for (Box2DStaticStructure structure :new Array.ArrayIterator<>(structures))
            structure.getSprite().dispose();
    }

    public Box2DPlayer getPlayer() {return player;}

    public Box2DEnemy getEnemy() {return enemy;}

    public Box2DKinematicStructure getMovingFloor() {return movingFloor;}
}
