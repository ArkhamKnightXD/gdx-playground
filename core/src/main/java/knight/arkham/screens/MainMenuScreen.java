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
        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Press F1 Basic Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT);
        game.font.draw(game.batch, "Press F2 Shape Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-20);
        game.font.draw(game.batch, "Press F3 Box2D Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT-40);

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
    }


    @Override
    public void hide() {

        dispose();
    }


    @Override
    public void dispose() {

    }
}
