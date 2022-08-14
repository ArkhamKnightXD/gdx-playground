package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import static knight.arkham.helpers.Constants.MID_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.MID_SCREEN_WIDTH;

public class Box2DScreen extends ScreenAdapter {

    private final Playground game;


    public Box2DScreen() {

        game = Playground.INSTANCE;
    }

    @Override
    public void show() {

    }


    private void update(){

        game.goBackToMenu();
    }

    @Override
    public void render(float delta) {

        update();

        ScreenUtils.clear(0,0,0,1);

        game.batch.begin();

        game.font.draw(game.batch, "Welcome To Physics Playground", MID_SCREEN_WIDTH-100, MID_SCREEN_HEIGHT);

        game.batch.end();

    }




    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
