package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class Box2DStructure implements Disposable {
    private final Body body;
    private final Rectangle bounds;
    private final Texture wallTexture;

    public boolean isMovingRight;

    public Box2DStructure(Rectangle bounds, World world, ContactType contactType, String spritePath) {

        isMovingRight = false;

        this.bounds = bounds;

        wallTexture = new Texture(spritePath);

        if (contactType == ContactType.MOVINGFLOOR)
            body = Box2DHelper.createKinematicBody(new Box2DBody(bounds, world, contactType));

        else
            body = Box2DHelper.createBody(new Box2DBody(bounds, true, 0, world, contactType));
    }

    public void update(){

//        Para poder mover un cuerpo kinematic debemos de indicar su velocidad lineal.
        if (isMovingRight)
            body.setLinearVelocity(1,0);
    }

    public void draw(SpriteBatch batch){
        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(wallTexture, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    @Override
    public void dispose() {
        wallTexture.dispose();
    }
}
