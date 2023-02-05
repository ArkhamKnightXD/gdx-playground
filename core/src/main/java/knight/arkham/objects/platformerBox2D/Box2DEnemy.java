package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class Box2DEnemy {
    private final Body body;
    private final Rectangle bounds;
    private final Texture sprite;
    public boolean isMovingRight;

    public Box2DEnemy(Rectangle rectangle, World world) {

        bounds = rectangle;

        isMovingRight = true;

        sprite = new Texture("images/enemy.png");

        body = Box2DHelper.createBody(new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10,world, ContactType.ENEMY));
    }

    public void update() {

        if (isMovingRight && body.getLinearVelocity().x <= 3)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if(!isMovingRight && body.getLinearVelocity().x >= -3)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    public Texture getSprite() {return sprite;}
}
