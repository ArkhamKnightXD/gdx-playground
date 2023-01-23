package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
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
    private final Player player;

    public PlatformerScreen() {
        game = Playground.INSTANCE;

        player = new Player(new Rectangle(200, 600, 32, 32));

        floor = new Structure(new Rectangle(100, 400, 200, 32));
        floor2 = new Structure(new Rectangle(400, 200, 200, 32));
        floor3 = new Structure(new Rectangle(200, 0, 200, 32));
    }

    private void update(){

        player.update();

        resetGamePositions();

        managePlayerFloorCollision();

        game.manageExitTheGame();
    }

    private void resetGamePositions() {
        if (Gdx.input.isKeyPressed(Input.Keys.F1)){

            player.getBody().setPosition(200, 600);

            floor2.getBody().x = 400;
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

    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,0);

        game.batch.begin();

        game.font.draw(game.batch, "Is player Grounded? : " + player.isPlayerGrounded(), MID_SCREEN_WIDTH-100, FULL_SCREEN_HEIGHT-10);

        player.draw(game.batch);

        floor.draw(game.batch);
        floor2.draw(game.batch);
        floor3.draw(game.batch);

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
    }
}
