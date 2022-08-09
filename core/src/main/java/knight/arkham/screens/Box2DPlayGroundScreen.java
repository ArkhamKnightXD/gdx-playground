package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;

public class Box2DPlayGroundScreen extends ScreenAdapter {

    private final Playground game;


    public Box2DPlayGroundScreen() {

        game = Playground.INSTANCE;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //  El elemento ScreenUtils.clear limpia la pantalla en cada renderizado
        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Welcome To Phisics Playground", 300-100, 250-50);

        game.batch.end();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
