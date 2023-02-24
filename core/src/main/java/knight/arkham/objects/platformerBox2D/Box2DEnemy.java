package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DEnemy extends GameObject {

    private final Animation<TextureRegion> runningAnimation;
    private float animationTimer;
    public boolean isMovingRight;

    public Box2DEnemy(Rectangle rectangle, World world, ContactType contactType, TextureAtlas textureAtlas) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10, world, contactType),
                new TextureRegion(textureAtlas.findRegion("goomba") ,0, 0, 16, 16)
        );

        TextureRegion characterRegion = textureAtlas.findRegion("goomba");

        isMovingRight = true;

        animationTimer = 0;

        runningAnimation = makeAnimationByFrameRange(characterRegion, 0, 1, 0.4f);
    }

    public void update(float deltaTime) {

        setActualRegion(runningAnimation.getKeyFrame(animationTimer, true));

        animationTimer += deltaTime;

        if (isMovingRight && body.getLinearVelocity().x <= 3)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if(!isMovingRight && body.getLinearVelocity().x >= -3)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
    }
}
