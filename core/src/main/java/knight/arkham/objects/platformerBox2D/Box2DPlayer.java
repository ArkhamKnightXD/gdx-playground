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
import com.badlogic.gdx.utils.Array;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DPlayer extends GameObject {

    private final TextureRegion characterRegion;

    private Animation<TextureRegion> runningAnimation;

    private float animationTimer;

    private boolean isPlayerRunningRight;


    public Box2DPlayer(Rectangle rectangle, World world, ContactType contactType, TextureAtlas textureAtlas) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10, world, contactType),
                new TextureRegion(textureAtlas.findRegion("little_mario") ,0, 0, 16, 16)
        );
        characterRegion = textureAtlas.findRegion("little_mario");

        animationTimer = 0;

        makePlayerAnimations();
    }

    private void makePlayerAnimations() {

        Array<TextureRegion> animationFrames = new Array<>();

        for (int i = 1; i < 4; i++)
            animationFrames.add(new TextureRegion(characterRegion, i * 16, 0, 16, 16));

        runningAnimation = new Animation<>(0.1f, animationFrames);

        animationFrames.clear();
    }

    public void update(float deltaTime) {

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 7){

            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

            TextureRegion runningRegion = runningAnimation.getKeyFrame(animationTimer, true);

            isPlayerRunningRight = true;

            flipPlayerOnXAxis(runningRegion);

            setActualRegion(runningRegion);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -7){

            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);

            TextureRegion runningRegion = runningAnimation.getKeyFrame(animationTimer, true);

            isPlayerRunningRight = false;

            flipPlayerOnXAxis(runningRegion);

            setActualRegion(runningRegion);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && body.getLinearVelocity().y == 0){

            body.applyLinearImpulse(new Vector2(0, 85), body.getWorldCenter(), true);

            TextureRegion jumpRegion = new TextureRegion(characterRegion, 80, 0, 16, 16);

            setActualRegion(jumpRegion);
        }

//        Seguiré probando para ver que cosas útiles puedo hacer con el fixture del personaje
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            fixture.setFriction(1.5f);

        animationTimer = animationTimer + deltaTime;
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
