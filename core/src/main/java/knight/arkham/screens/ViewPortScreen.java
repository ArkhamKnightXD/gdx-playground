package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Playground;

import static knight.arkham.helpers.Constants.*;

public class ViewPortScreen extends ScreenAdapter {

    private final Playground game;

//    ScreenViewPort es ideal para pantallas de menu
    private final ScreenViewport viewport;

    private final float PPM = 32;

    private final Texture img;

    public ViewPortScreen() {

        game = Playground.INSTANCE;

        viewport = new ScreenViewport();

        viewport.setUnitsPerPixel(1/PPM);

        viewport.update(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);

        img = new Texture("images/pipe.png");
    }

    @Override
    public void resize(int width, int height) {

        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,0);

        game.batch.setProjectionMatrix(viewport.getCamera().combined);

        game.batch.begin();

        game.batch.draw(img, 0, 0, 64/PPM, 64/PPM);
        game.batch.end();

    }

    @Override
    public void hide() {

        dispose();
    }


    @Override
    public void dispose() {

    }
}
