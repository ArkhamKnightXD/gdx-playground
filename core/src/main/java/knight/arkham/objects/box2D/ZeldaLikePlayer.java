package knight.arkham.objects.box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class ZeldaLikePlayer extends Box2DGameObject {

    public ZeldaLikePlayer(Rectangle rectangle, World world) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody, 10, world, ContactType.PLAYER),
                new TextureRegion(new Texture("images/ghost.png"))
        );
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 2)
            body.applyLinearImpulse(new Vector2(0.5f, 0), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -2)
            body.applyLinearImpulse(new Vector2(-0.5f, 0), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.W) && body.getLinearVelocity().y <= 2)
            body.applyLinearImpulse(new Vector2(0, 0.5f), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.S) && body.getLinearVelocity().y >= -2)
            body.applyLinearImpulse(new Vector2(0, -0.5f), body.getWorldCenter(), true);
    }
}
