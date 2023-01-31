package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class Box2DPlayer implements Disposable {

    private final Body body;
    private final Rectangle bounds;
    private final Texture sprite;

    public boolean isTrampolineModeActive;

    public Box2DPlayer(Rectangle rectangle, World world) {
        bounds = rectangle;

        sprite = new Texture("images/ghost.png");

        body = Box2DHelper.createBody(new Box2DBody(rectangle, false,10,world, ContactType.PLAYER));
    }

    public void update() {

        if (isTrampolineModeActive && body.getLinearVelocity().y == 0)
            body.applyLinearImpulse(new Vector2(0, 85), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 7)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -7)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && body.getLinearVelocity().y == 0)
            body.applyLinearImpulse(new Vector2(0, 85), body.getWorldCenter(), true);
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
