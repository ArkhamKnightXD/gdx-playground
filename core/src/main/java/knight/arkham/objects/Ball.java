package knight.arkham.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import static knight.arkham.helpers.Constants.*;

public class Ball {

    private final Vector2 position;
    private final Vector2 velocity;
    private final int radius;

    public Ball(Vector2 position, Vector2 velocity, int radius) {

        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void update() {

//        Le sumo a la posición la velocidad positiva tanto en X como en Y
        position.x += velocity.x;
        position.y += velocity.y;

//        Si la pelota llega en algún momento a los límites de la pantalla vuelvo la velocidad negativa
//        para que la pelota cambie de dirección
        if (position.x < 0 || position.x > FULL_SCREEN_WIDTH)
            velocity.x = -velocity.x;

        if (position.y < 0 || position.y > FULL_SCREEN_HEIGHT)
            velocity.y = -velocity.y;
    }

    public void render(ShapeRenderer shape) {
        shape.circle(position.x, position.y, radius);
    }
}
