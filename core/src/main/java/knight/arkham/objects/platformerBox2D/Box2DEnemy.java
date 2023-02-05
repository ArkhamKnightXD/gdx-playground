package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DEnemy extends GameObject {
    public boolean isMovingRight;

    public Box2DEnemy(Rectangle rectangle, World world, ContactType contactType) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10, world, contactType),
                new Texture("images/enemy.png")
        );

        isMovingRight = true;
    }

    public void update() {

        if (isMovingRight && body.getLinearVelocity().x <= 3)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if(!isMovingRight && body.getLinearVelocity().x >= -3)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }
}
