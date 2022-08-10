package knight.arkham.objects;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import static knight.arkham.helpers.Constants.*;

public class Ball {
    float positionX;
    float positionY;
    int radius;
    int xSpeed;
    int ySpeed;

    public Ball(float positionX, float positionY, int radius, int xSpeed, int ySpeed) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void update() {

//        Le sumo a la posicion la velocidad positiva tanto en X como en Y
        positionX += xSpeed;
        positionY += ySpeed;

//        Si la pelota llega en algun momento a los limites de la pantalla vuelvo la velocidad negativa
//        para que la pelota cambie de direccion
        if (positionX < 0 || positionX > FULL_SCREEN_WIDTH) {
            xSpeed = -xSpeed;
        }
        if (positionY < 0 || positionY > FULL_SCREEN_HEIGHT) {
            ySpeed = -ySpeed;
        }
    }

    public void render(ShapeRenderer shape) {

        shape.circle(positionX, positionY, radius);
    }
}
