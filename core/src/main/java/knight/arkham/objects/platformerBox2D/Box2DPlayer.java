package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DPlayer extends GameObject {

    private final TextureRegion jumpingRegion;
    private final TextureRegion standingRegion;
    private PlayerAnimationState currentState;
    private PlayerAnimationState previousState;

    private final Animation<TextureRegion> runningAnimation;

    private float animationTimer;

    private boolean isPlayerRunningRight;


    public Box2DPlayer(Rectangle rectangle, World world, ContactType contactType, TextureAtlas textureAtlas) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10, world, contactType),
                new TextureRegion(textureAtlas.findRegion("little_mario") ,0, 0, 16, 16)
        );

        previousState = PlayerAnimationState.STANDING;
        currentState = PlayerAnimationState.STANDING;
        animationTimer = 0;

        TextureRegion characterRegion = textureAtlas.findRegion("little_mario");

        jumpingRegion = new TextureRegion(characterRegion, 80, 0, 16, 16);
        standingRegion = new TextureRegion(characterRegion, 0, 0, 16, 16);

        runningAnimation = makeAnimationByFrameRange(characterRegion, 1, 3, 0.1f);
    }


    public void update(float deltaTime) {

        setActualRegion(getActualRegion(deltaTime));

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 7){

            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

            isPlayerRunningRight = true;
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -7){

            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);

            isPlayerRunningRight = false;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && body.getLinearVelocity().y == 0)
            body.applyLinearImpulse(new Vector2(0, 85), body.getWorldCenter(), true);

//        Seguiré probando para ver que cosas útiles puedo hacer con el fixture del personaje
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            fixture.setFriction(1.5f);
    }

    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case JUMPING:
                region = jumpingRegion;
                break;

            case RUNNING:
                region = runningAnimation.getKeyFrame(animationTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = standingRegion;
        }

        flipPlayerOnXAxis(region);

        animationTimer = currentState == previousState ? animationTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private PlayerAnimationState getPlayerCurrentState() {

        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == PlayerAnimationState.JUMPING))
            return PlayerAnimationState.JUMPING;

        else if (body.getLinearVelocity().x != 0)
            return PlayerAnimationState.RUNNING;

        else if (body.getLinearVelocity().y < 0)
            return PlayerAnimationState.FALLING;

        else
            return PlayerAnimationState.STANDING;
    }

    private void flipPlayerOnXAxis(TextureRegion region) {

        if (!isPlayerRunningRight && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if (isPlayerRunningRight && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }
    }
}
