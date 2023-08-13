package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.objects.platformer.Player;
import knight.arkham.objects.platformer.Structure;

import static knight.arkham.helpers.Constants.*;

public class PlatformerScreen extends ScreenAdapter {
    private final Playground game;
    private final Structure floor;
    private final Structure floor2;
    private final Structure floor3;
    private final Structure floor4;
    private final Structure warpPipe;
    private final Player player;
    private final OrthographicCamera camera;

    private final Array<Structure> structures;

    public PlatformerScreen() {
        game = Playground.INSTANCE;

        player = new Player(new Rectangle(200, 600, 32, 32));

        floor = new Structure(new Rectangle(100, 400, 200, 32), "images/wall.png");
        floor2 = new Structure(new Rectangle(400, 200, 200, 32), "images/wall.png");
        floor3 = new Structure(new Rectangle(225, 75, 200, 32), "images/wall.png");
        floor4 = new Structure(new Rectangle(0, 0, FULL_SCREEN_WIDTH, 32), "images/wall.png");

        warpPipe = new Structure(new Rectangle(80, 30, 128, 128), "images/pipe.png");

        structures = getStructures();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);
    }

    private Array<Structure> getStructures() {

        final Array<Structure> structures;

        structures = new Array<>();

        structures.add(floor);
        structures.add(floor2);
        structures.add(floor3);
        structures.add(floor4);
        structures.add(warpPipe);

        return structures;
    }

    private void update(float delta){

        updateCameraPosition();

        player.update(delta);

        resetGamePositions();

        managePlayerFloorCollision();

        manageCollisionBetweenFloors();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBounds().x, player.getBounds().y, 0);

        camera.update();
    }

    private void resetGamePositions() {
        if (Gdx.input.isKeyPressed(Input.Keys.F1)){

            player.getBounds().setPosition(200, 600);

            floor2.getBounds().x = 400;
            floor3.getBounds().y = 75;
        }
    }

    private void managePlayerFloorCollision() {

        if (player.getBounds().overlaps(floor.getBounds()))
            player.isPlayerGrounded = true;

        else if (player.getBounds().overlaps(floor2.getBounds())){

            player.isPlayerGrounded = true;

            floor2.floorXAxisMovement(player.getBounds());
        }

        else if (player.getBounds().overlaps(floor3.getBounds())){

            player.isPlayerGrounded = true;

            floor3.floorYAxisMovement(player.getBounds());
        }

        else if (player.getBounds().overlaps(floor4.getBounds()))
            player.isPlayerGrounded = true;

        else if (player.getBounds().overlaps(warpPipe.getBounds())){
            player.isPlayerGrounded = true;

            if (Gdx.input.isKeyPressed(Input.Keys.S))
                player.getBounds().setPosition(floor.getBounds().x,floor.getBounds().y + player.getBounds().height);
        }

        else
            player.isPlayerGrounded = false;
    }

    private void manageCollisionBetweenFloors() {

        if (floor3.getBounds().overlaps(floor.getBounds()))
            floor3.getBounds().y = floor.getBounds().y - floor.getBounds().height;

        if (floor3.getBounds().overlaps(floor2.getBounds()))
            floor3.getBounds().y = floor2.getBounds().y - floor2.getBounds().height;
    }


    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "Is player Grounded? " + player.isPlayerGrounded,
                player.getBounds().x, player.getBounds().y + 64);

        player.draw(game.batch);

        for (Structure structure : new Array.ArrayIterator<>(structures))
            structure.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        player.getSprite().dispose();

        for (Structure structure : new Array.ArrayIterator<>(structures))
            structure.getSprite().dispose();
    }
}
