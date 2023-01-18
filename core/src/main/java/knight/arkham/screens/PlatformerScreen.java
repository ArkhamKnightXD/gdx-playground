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

    private void update(float deltaTime){

        player.update(deltaTime);

        if (Gdx.input.isKeyPressed(Input.Keys.F1)){
            player.getBounds().x = 200;
            player.getBounds().y = 600;
        }

        checkIfPlayerIsGrounded();

        game.goBackToMenu();
    }

    private void checkIfPlayerIsGrounded() {

        if (player.getBounds().overlaps(floor.getBounds()))
            player.setPlayerGrounded(true);

        else if (player.getBounds().overlaps(floor2.getBounds()))
            player.setPlayerGrounded(true);

        else player.setPlayerGrounded(player.getBounds().overlaps(floor3.getBounds()));
    }

    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        game.batch.begin();

        game.font.draw(game.batch, "Is player Grounded: " + player.isPlayerGrounded(), MID_SCREEN_WIDTH-100, FULL_SCREEN_HEIGHT-10);

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
