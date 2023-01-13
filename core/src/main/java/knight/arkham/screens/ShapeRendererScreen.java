package knight.arkham.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import knight.arkham.Playground;
import knight.arkham.objects.Ball;

public class ShapeRendererScreen extends ScreenAdapter {

    private final Playground game;

    //Ejerce una función parecida al spritebatch, pero con este puede dibujar formas geometricas
    private final ShapeRenderer shapeRenderer;

    private final Vector2 position;
    private final float radius;
    private float velocityX;
    
    private final Ball ball;


    public ShapeRendererScreen() {

        game = Playground.INSTANCE;

        position = new Vector2(50, 50);
        radius = 50;
        velocityX = 5;

        shapeRenderer = new ShapeRenderer();
        ball = new Ball(new Vector2(150, 200), new Vector2(12, 5), 50);
    }
    
    private void update(){

        game.goBackToMenu();

        randomMovement();

        ball.update();
    }

    @Override
    public void render(float delta) {
        
        update();

        ScreenUtils.clear(0,0,0,0);

//        Es necesario pasarle al shapeRenderer este parametro inicial, para indicar si las figuras, estarán
//        llenas del color blanca o solo se mostraran las líneas de las figuras
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.circle(position.x, position.y, radius);

        ball.render(shapeRenderer);

        shapeRenderer.end();
    }

    private void randomMovement(){

        position.x += velocityX;

        if (position.x > game.getScreenWidth()-radius)
            velocityX = -5;

        if (position.x < radius)
            velocityX = 5;
    }


    @Override
    public void hide() {

        dispose();
    }

    @Override
    public void dispose() {

//        shapeRenderer.dispose();
    }
}
