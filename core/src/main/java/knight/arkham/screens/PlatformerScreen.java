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
    private final Player player;
    private final OrthographicCamera camera;
    private final Array<Structure> structures;

    public PlatformerScreen() {

        game = Playground.INSTANCE;

        player = new Player(new Rectangle(400, 200, 32, 32));

        floor = new Structure(new Rectangle(100, 400, 200, 32), "images/wall.png");
        floor2 = new Structure(new Rectangle(400, 175, 200, 32), "images/wall.png");
        floor3 = new Structure(new Rectangle(225, 85, 200, 32), "images/wall.png");
        Structure floor4 = new Structure(new Rectangle(700, 30, 150, 32), "images/wall.png");
        Structure floor5 = new Structure(new Rectangle(0, 0, FULL_SCREEN_WIDTH, 32), "images/wall.png");

        structures = new Array<>();

        structures.add(floor);
        structures.add(floor2);
        structures.add(floor3);
        structures.add(floor4);
        structures.add(floor5);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);
    }

    private void update(float delta){

        camera.position.set(player.getBounds().x, player.getBounds().y, 0);

        camera.update();

        if (Gdx.input.isKeyPressed(Input.Keys.F1)){

            player.getBounds().setPosition(200, 600);

            floor2.getBounds().x = 400;
            floor3.getBounds().y = 75;
        }

        player.update(delta);

        managePlayerFloorCollision(delta);

        manageCollisionBetweenFloors();

        game.manageExitTheGame();
    }

    private boolean checkCollisionInX(Rectangle player, Rectangle platform) {
        return player.x + player.width > platform.x && player.x < platform.x + platform.width;
    }

    private boolean checkCollisionInY(Rectangle player, Rectangle platform) {
        return player.y + player.height > platform.y && player.y < platform.y + platform.height;
    }


    private void managePlayerFloorCollision(float deltaTime) {

        for (Structure platform : structures) {

            if (player.getBounds().overlaps(platform.getBounds())) {

                if (checkCollisionInX(player.getPreviousPosition(), platform.getBounds())) {

//                    Player was falling downwards. Resolve upwards.
                    if (player.velocity.y < 0)
                        player.bounds.y = platform.bounds.y + player.bounds.height;

//                     Player was moving upwards. Resolve downwards
                    else
                        player.bounds.y = platform.bounds.y - player.bounds.height;

                    player.velocity.y = 0;
                }
                else if (checkCollisionInY(player.getPreviousPosition(), platform.getBounds())) {

//                     Player was traveling right. Resolve to the left
                    if (player.velocity.x > 0)
                        player.bounds.x = platform.bounds.x - player.bounds.width;

//                     Player was traveling left. Resolve to the right
                    else
                        player.bounds.x = platform.bounds.x + platform.bounds.width;

                    player.velocity.x = 0;
                }

                if (player.velocity.y == 0 && Gdx.input.isKeyPressed(Input.Keys.SPACE))
                    player.velocity.y = 500 * deltaTime;

                // Handle specific floor movements or actions
//                if (currentFloor == floor2)
//                    floor2.floorXAxisMovement(player.getBounds());
//
//                 else if (currentFloor == floor3)
//                    floor3.floorYAxisMovement(player.getBounds());
//
//                 else if (currentFloor == warpPipe && Gdx.input.isKeyPressed(Input.Keys.S))
//                    player.getBounds().setPosition(currentFloor.getBounds().x, currentFloor.getBounds().y + player.getBounds().height);
            }
        }
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

        player.draw(game.batch);

        for (Structure structure : structures)
            structure.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        player.dispose();

        for (Structure structure : structures)
            structure.dispose();
    }
}
