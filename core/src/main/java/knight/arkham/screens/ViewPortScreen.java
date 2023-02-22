package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import knight.arkham.Playground;

import static knight.arkham.helpers.Constants.*;

public class ViewPortScreen extends ScreenAdapter {

//    Mediante los viewport puedo mantener la relación de aspectos de mi juego independientemente de la resolución
//    del dispositivo en el que se esté ejecutando mi juego.
    private final Playground game;

//ScreenViewPort es ideal para pantallas de menu.
    private final ScreenViewport viewport;

    //en las pantallas de menu, no hay necesidad de cámaras, pues en su mayoría son statics.
    private final Texture img;

    public ViewPortScreen() {

        game = Playground.INSTANCE;

        viewport = new ScreenViewport();

        viewport.setUnitsPerPixel(1/PIXELS_PER_METER);

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

        game.batch.draw(img, 0, 0, 64/PIXELS_PER_METER, 64/PIXELS_PER_METER);

        game.batch.end();
    }

    @Override
    public void hide() {

        dispose();
    }


    @Override
    public void dispose() {

        img.dispose();
    }
}
