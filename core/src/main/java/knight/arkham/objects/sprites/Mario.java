package knight.arkham.objects.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.objects.box2D.PlayerAnimationState;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Mario extends Sprite {

    private PlayerAnimationState currentState;
    private PlayerAnimationState previousState;

    private float stateTimer;

    private Animation<TextureRegion> playerRunning;
    private TextureRegion playerJumping;
    private final Body body;

    private final TextureRegion playerStand;

    private TextureRegion dyingPlayer;

    private final boolean isMarioDead;

    private boolean isPlayerRunningRight;



    public Mario(World world, TextureRegion textureRegion) {

        super(textureRegion);

        currentState = PlayerAnimationState.STANDING;
        previousState = PlayerAnimationState.STANDING;
        stateTimer = 0;

        isPlayerRunningRight = true;
        isMarioDead = false;

        body = Box2DHelper.createPlayerBody(

                new Box2DBody(new Rectangle(450, 75, 32, 32), world,0.5f, this)
        );

        playerStand = new TextureRegion(getTexture(), 0, 10, 16, 16);

        setRegion(playerStand);

        setBounds(0, 0, 32 / PIXELS_PER_METER, 32 / PIXELS_PER_METER);

        makePlayerAnimations();
    }

    private void makePlayerAnimations() {

        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = 1; i < 4; i++)
            animationFrames.add(new TextureRegion(getTexture(), i * 16, 10, 16, 16));

        playerRunning = new Animation<>(0.1f, animationFrames);

        animationFrames.clear();

        playerJumping = new TextureRegion(getTexture(), 80, 10, 16, 16);
        dyingPlayer = new TextureRegion(getTexture(), 96, 0, 16, 32);
    }


    public void update(float deltaTime) {

        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

        setRegion(getActualRegion(deltaTime));

        playerMovement();
    }

    private void playerMovement() {

        if (currentState != PlayerAnimationState.DEAD) {

            if (Gdx.input.isKeyJustPressed(Input.Keys.UP) && body.getLinearVelocity().y == 0)
                body.applyLinearImpulse(new Vector2(0, 4.3f), body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && body.getLinearVelocity().x <= 4)
                body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && body.getLinearVelocity().x >= -4)
                body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);
        }
    }

    private PlayerAnimationState getPlayerCurrentState() {

        if (isMarioDead)
            return PlayerAnimationState.DEAD;

        else if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == PlayerAnimationState.JUMPING))
            return PlayerAnimationState.JUMPING;

        else if (body.getLinearVelocity().y < 0)
            return PlayerAnimationState.FALLING;

        else if (body.getLinearVelocity().x != 0)
            return PlayerAnimationState.RUNNING;

        else
            return PlayerAnimationState.STANDING;
    }


    private TextureRegion getActualRegion(float deltaTime) {

        currentState = getPlayerCurrentState();

        TextureRegion region;

        switch (currentState) {

            case DEAD:
                region = dyingPlayer;
                break;

            case JUMPING:
                region = playerJumping;
                break;
            case RUNNING:
                region = playerRunning.getKeyFrame(stateTimer, true);
                break;

            case FALLING:
            case STANDING:
            default:
                region = playerStand;
        }

        flipPlayerOnXAxis(region);

        stateTimer = currentState == previousState ? stateTimer + deltaTime : 0;
        previousState = currentState;

        return region;
    }

    private void flipPlayerOnXAxis(TextureRegion region) {

        if ((body.getLinearVelocity().x < 0 || !isPlayerRunningRight) && !region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = false;
        } else if ((body.getLinearVelocity().x > 0 || isPlayerRunningRight) && region.isFlipX()) {

            region.flip(true, false);
            isPlayerRunningRight = true;
        }
    }


    public Body getBody() {return body;}

    public Vector2 getActualPixelPosition() {

        return new Vector2(body.getPosition().x * PIXELS_PER_METER, body.getPosition().y * PIXELS_PER_METER);
    }
}
