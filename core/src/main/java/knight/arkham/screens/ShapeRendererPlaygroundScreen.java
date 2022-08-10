package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

public class ShapeRendererPlaygroundScreen extends ScreenAdapter {

//    Ejerce una funcion parecida al spritebatch, pero con este puede dibujar formas geometricas
    private final ShapeRenderer shapeRenderer;

    public ShapeRendererPlaygroundScreen() {

        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0,0,0,1);

//        Es necesario pasarle al shaperenderer este parametro inicial, para indicar si las figuras, estaran
//        llenas del color blanca o solo se mostraran las lineas de las figuras
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.circle(50, 50, 50);

        shapeRenderer.end();

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
