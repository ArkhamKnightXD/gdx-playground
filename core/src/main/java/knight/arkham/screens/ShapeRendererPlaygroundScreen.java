package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.objects.Ball;

public class ShapeRendererPlaygroundScreen extends ScreenAdapter {

    private final Playground game = Playground.INSTANCE;

    //    Ejerce una funcion parecida al spritebatch, pero con este puede dibujar formas geometricas
    private final ShapeRenderer shapeRenderer;

    private float positionX;
    private final float positionY;

    private final float radius;
    private float xSpeed;
    
    private final Ball ball;


    public ShapeRendererPlaygroundScreen() {

        positionX = 50;
        positionY = 50;
        radius = 50;
        xSpeed = 5;

        shapeRenderer = new ShapeRenderer();
        ball = new Ball(150, 200, 50, 12, 5);
    }

    @Override
    public void show() {

    }
    
    private void update(){

        randomMovement();

        ball.update();
    }

    @Override
    public void render(float delta) {
        
        update();

        ScreenUtils.clear(0,0,0,1);

//        Es necesario pasarle al shaperenderer este parametro inicial, para indicar si las figuras, estaran
//        llenas del color blanca o solo se mostraran las lineas de las figuras
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.circle(positionX, positionY, radius);

        ball.render(shapeRenderer);

        shapeRenderer.end();
        
    }

    private void randomMovement(){

        positionX += xSpeed;

        if (positionX > game.getScreenWidth()-radius) {
            xSpeed = -5;
        }
        if (positionX < radius) {
            xSpeed = 5;
        }
    }


    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

        shapeRenderer.dispose();
    }
}
