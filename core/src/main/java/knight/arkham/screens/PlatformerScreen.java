package knight.arkham.screens;

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
    private final Player player;

    public PlatformerScreen() {
        game = Playground.INSTANCE;
        floor = new Structure(new Rectangle(0, 0, FULL_SCREEN_WIDTH, 32));
        player = new Player(new Rectangle(200, 400, 32, 32));
    }

    private void update(float deltaTime){

        player.update(deltaTime);

        if (player.getBounds().overlaps(floor.getBounds()))
            player.getBounds().y = 32;
    }

    @Override
    public void render(float delta) {

        update(delta);

        ScreenUtils.clear(0,0,0,0);

        game.batch.begin();

        player.draw(game.batch);

        floor.draw(game.batch);

        game.batch.end();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
