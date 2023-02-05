package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;
import knight.arkham.screens.Box2DScreen;

import static knight.arkham.helpers.Constants.*;

public class PhysicsBall {

    private final Body body;
    private final Rectangle bounds;
    private final Vector2 velocity;
    private final float speed;
    private final Texture ballTexture;

    public PhysicsBall(Box2DScreen gameScreen) {

        bounds = new Rectangle(MID_SCREEN_WIDTH, MID_SCREEN_HEIGHT, 32, 32);
        speed = 8;

// Con mi vector velocity controlo la direcciones de la pelota
        velocity = new Vector2(getRandomDirection(), getRandomDirection());

        ballTexture = new Texture("images/initial.png");

        body = Box2DHelper.createBody(
                    new Box2DBody(bounds, BodyDef.BodyType.DynamicBody, 100, gameScreen.getGameWorld(), ContactType.BALL)
        );
    }

    private float getRandomDirection() {
        return (Math.random() < 0.5) ? 1 : -1;
    }

    public void update() {

        bounds.x = body.getPosition().x * PIXELS_PER_METER - (bounds.width / 2);
        bounds.y = body.getPosition().y * PIXELS_PER_METER - (bounds.height / 2);

//Aqui multiplico las coordenadas de mi vector velocidad con el scalar speed, para indicar la velocidad lineal al body.
        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);
    }


    public void draw(SpriteBatch batch) {
        batch.draw(ballTexture, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public void reverseVelocityX() {
        velocity.x *= -1;
    }

    public void reverseVelocityY() {
        velocity.y *= -1;
    }

    public void incrementXVelocity() {
        velocity.x *= 1.1f;
    }

    public void incrementYVelocity() {
        velocity.y *= 1.1f;
    }

    public Texture getBallTexture() {return ballTexture;}
}
