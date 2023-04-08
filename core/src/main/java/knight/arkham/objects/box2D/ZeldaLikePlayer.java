package knight.arkham.objects.box2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class ZeldaLikePlayer extends Box2DGameObject {

    private final Vector2 velocity;

    private final float speed;

    private final World world;

    public ZeldaLikePlayer(Rectangle rectangle, World world) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody, 10, world, ContactType.PLAYER),
                new TextureRegion(new Texture("images/ghost.png"))
        );
        velocity = new Vector2(0,0);
        speed = 2;

        this.world = world;
    }

    private void playerMovement() {

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 2)
            velocity.x = 2;

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -2)
            velocity.x = -2;

        else if (Gdx.input.isKeyPressed(Input.Keys.W) && body.getLinearVelocity().y <= 2)
            velocity.y = 2;

        else if (Gdx.input.isKeyPressed(Input.Keys.S) && body.getLinearVelocity().y >= -2)
            velocity.y = -2;

        body.setLinearVelocity(velocity.x * speed, velocity.y * speed);

        velocity.set(0,0);
    }


    public void update() {

        playerMovement();

        playerShootControl();
    }

    private void playerShootControl() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT))
            shootBulletByDirection(new Vector2(5,0));

        else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))
            shootBulletByDirection(new Vector2(-5,0));

        else if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            shootBulletByDirection(new Vector2(0,5));

        else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))
            shootBulletByDirection(new Vector2(0,-5));
    }

    private void shootBulletByDirection(Vector2 direction){

        Body bullet =  Box2DHelper.createBullet(
                new Box2DBody(
                        new Rectangle(
                                getActualPixelPosition().x,
                                getActualPixelPosition().y,
                                0, 0
                        ),
                        world
                )
        );

        bullet.setBullet(true);

        bullet.setLinearVelocity(direction);
    }
}
