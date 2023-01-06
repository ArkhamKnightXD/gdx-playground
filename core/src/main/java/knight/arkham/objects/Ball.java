package knight.arkham.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import static knight.arkham.helpers.Constants.*;

public class Ball {
    float positionX;
    float positionY;
    int speedX;
    int speedY;
    int radius;


    public Ball(float positionX, float positionY, int radius, int speedX, int speedY) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.radius = radius;
    }

    public void update() {

//        Le sumo a la posición la velocidad positiva tanto en X como en Y
        positionX += speedX;
        positionY += speedY;

//        Si la pelota llega en algún momento a los límites de la pantalla vuelvo la velocidad negativa
//        para que la pelota cambie de dirección
        if (positionX < 0 || positionX > FULL_SCREEN_WIDTH)
            speedX = -speedX;

        if (positionY < 0 || positionY > FULL_SCREEN_HEIGHT)
            speedY = -speedY;
    }

    public void render(ShapeRenderer shape) {

        shape.circle(positionX, positionY, radius);
    }
}
