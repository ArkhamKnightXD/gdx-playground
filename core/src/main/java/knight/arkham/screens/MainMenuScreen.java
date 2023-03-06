package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import static knight.arkham.helpers.Constants.MID_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.MID_SCREEN_WIDTH;

public class MainMenuScreen extends ScreenAdapter {

    private final Playground game;

    public MainMenuScreen() {

        game = Playground.INSTANCE;
    }

    @Override
    public void render(float delta) {

//        El elemento ScreenUtils.clear limpia la pantalla en cada renderizado
        ScreenUtils.clear(0,0,0,0);

        game.batch.begin();

        game.font.draw(game.batch, "Press F1 Basic Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT);
        game.font.draw(game.batch, "Press F2 Shape Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-20);
        game.font.draw(game.batch, "Press F3 Box2D Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-40);
        game.font.draw(game.batch, "Press F4 Platformer Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-60);
        game.font.draw(game.batch, "Press F5 Box2D Platformer Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-80);
        game.font.draw(game.batch, "Press F6 TiledMap Platformer Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-100);
        game.font.draw(game.batch, "Press ESC To Close The App", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-120);

        game.batch.end();

        manageScreenSelection();
    }

    private void manageScreenSelection() {

        if (Gdx.input.isKeyPressed(Input.Keys.F1))
            game.setScreen(new BasicScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.F2))
            game.setScreen(new ShapeRendererScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.F3))
            game.setScreen(new Box2DScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.F4))
            game.setScreen(new PlatformerScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.F5))
            game.setScreen(new PlatformerBox2DScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.F6))
            game.setScreen(new TileMapBox2DScreen());

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }
}
