package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.objects.Player;
import knight.arkham.objects.Structure;

import static knight.arkham.helpers.Constants.*;

public class PlatformerScreen extends ScreenAdapter {
    private final Playground game;
    private final Structure floor;
    private final Structure floor2;
    private final Structure floor3;
    private final Structure wall;
    private final Structure wall2;
    private final Player player;
    private final OrthographicCamera camera;

    public PlatformerScreen() {
        game = Playground.INSTANCE;

        player = new Player(new Rectangle(200, 600, 32, 32));

        floor = new Structure(new Rectangle(100, 400, 200, 32), "images/wall.png");
        floor2 = new Structure(new Rectangle(400, 200, 200, 32), "images/wall.png");
        floor3 = new Structure(new Rectangle(200, 0, 200, 32), "images/wall.png");
        wall = new Structure(new Rectangle(FULL_SCREEN_WIDTH, 0, 32, FULL_SCREEN_HEIGHT), "images/initial.png");
        wall2 = new Structure(new Rectangle(0, 0, 32, FULL_SCREEN_HEIGHT), "images/initial.png");

        camera = new OrthographicCamera();
        camera.setToOrtho(false, FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);
    }

    private void update(){

        updateCameraPosition();

        player.update();

        resetGamePositions();

        managePlayerFloorCollision();

        manageCollisionBetweenFloors();

        game.manageExitTheGame();
    }

    private void updateCameraPosition(){

        camera.position.set(player.getBody().x, player.getBody().y, 0);

        camera.update();
    }

    private void resetGamePositions() {
        if (Gdx.input.isKeyPressed(Input.Keys.F1)){

            player.getBody().setPosition(200, 600);

            floor2.getBody().x = 400;
            floor3.getBody().y = 0;
        }
    }

    private void managePlayerFloorCollision() {

        if (player.getBody().overlaps(floor.getBody()))
            player.setPlayerGrounded(true);

        else if (player.getBody().overlaps(floor2.getBody())){

            player.setPlayerGrounded(true);

            floor2.floorXAxisMovement(player.getBody());
        }

        else if (player.getBody().overlaps(floor3.getBody())){
            player.setPlayerGrounded(true);

            floor3.floorYAxisMovement(player.getBody());
        }

        else
            player.setPlayerGrounded(false);
    }

    private void manageCollisionBetweenFloors() {

        if (floor3.getBody().overlaps(floor.getBody()))
            floor3.getBody().y = floor.getBody().y - floor.getBody().height;

        if (floor3.getBody().overlaps(floor2.getBody()))
            floor3.getBody().y = floor2.getBody().y - floor2.getBody().height;
    }


    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "Is player Grounded? " + player.isPlayerGrounded(),
                player.getBody().x, player.getBody().y + 64);

        player.draw(game.batch);

        floor.draw(game.batch);
        floor2.draw(game.batch);
        floor3.draw(game.batch);

        wall.draw(game.batch);
        wall2.draw(game.batch);

        game.batch.end();
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
        wall.dispose();
        wall2.dispose();
    }
}
