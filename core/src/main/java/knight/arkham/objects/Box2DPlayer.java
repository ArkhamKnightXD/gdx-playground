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

    public Box2DPlayer(Rectangle rectangle, World world) {
        sprite = new Texture("images/initial.png");

        body = Box2DHelper.createBody(new Box2DBody(rectangle, false,5,world, ContactType.PLAYER));
        bounds = rectangle;
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }

    public void draw(Batch batch) {
        batch.draw(sprite, body.getPosition().x - (bounds.width / 2), body.getPosition().y - (bounds.height / 2),bounds.width  , bounds.height);
    }

    public Body getBody() {
        return body;
    }

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
