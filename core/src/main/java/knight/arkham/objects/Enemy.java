package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class Enemy implements Disposable {
    private final Body body;
    private final Rectangle bounds;
    private final Texture sprite;
    public boolean isMovingRight;


    public Enemy(Rectangle rectangle, World world) {

        bounds = rectangle;

        isMovingRight = true;

        sprite = new Texture("images/enemy.png");

        body = Box2DHelper.createBody(new Box2DBody(rectangle, false,10,world, ContactType.ENEMY));
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

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
