package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;

public class MainMenuScreen extends ScreenAdapter {

    private final Playground game;


    public MainMenuScreen() {

        game = Playground.INSTANCE;
    }


    @Override
    public void show() {


    }


    @Override
    public void render(float delta) {

//        El elemento ScreenUtils.clear limpia la pantalla en cada renderizado
        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Press Enter Basic Playground", 300-100, 250-50);
        game.font.draw(game.batch, "Press Space Box2D Playground", 300-100, 250-100);

        game.batch.end();

        manageScreenSelection();
    }

    private void manageScreenSelection() {

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){

            game.setScreen(new BasicPlaygroundScreen());
            dispose();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){

            game.setScreen(new Box2DPlayGroundScreen());
            dispose();
        }
    }


    @Override
    public void hide() {

        dispose();
    }


    @Override
    public void dispose() {

    }
}
