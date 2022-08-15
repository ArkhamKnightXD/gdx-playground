package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.helpers.BodyHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Constants;
import knight.arkham.helpers.ContactType;
import knight.arkham.screens.Box2DScreen;

public class PhysicsBall {

    private final Body body;
    private float positionX;
    private float positionY;
    private final float speed;
    private float velocityY;
    private float velocityX;
    private final int width;
    private final int height;
    private final Texture ballTexture;

    public PhysicsBall(Box2DScreen gameScreen) {

        positionX = Constants.MID_SCREEN_WIDTH;
        positionY = Constants.MID_SCREEN_HEIGHT;
        speed = 8;
        width = 32;
        height = 32;

//        Con velocity controlo la direcciones de la pelota
        velocityX = getRandomDirection();
        velocityY = getRandomDirection();

        ballTexture = new Texture("images/initial.png");

        body = BodyHelper.createBody(new Box2DBody(positionX, positionY, width, height, false,
                0, gameScreen.getGameWorld(), ContactType.BALL));
    }

    private float getRandomDirection(){

        return (Math.random() < 0.5) ? 1 : -1;
    }

    public void update(){

        positionX = body.getPosition().x * Constants.PIXELS_PER_METER - (width / 2);
        positionY = body.getPosition().y * Constants.PIXELS_PER_METER - (height / 2);

        body.setLinearVelocity(velocityX * speed, velocityY * speed);
    }


    public void render(SpriteBatch batch){

        batch.draw(ballTexture, positionX, positionY, width, height);
    }


    public void reverseVelocityX(){

        velocityX *= -1;
    }


    public void reverseVelocityY(){

        velocityY *= -1;
    }


    public void incrementSpeed(){

        velocityX *= 1.1f;
    }
}
