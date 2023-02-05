package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class Box2DStructure {
    private final Body body;
    private final Rectangle bounds;
    private final Texture sprite;

    public boolean isMovingRight;

    public Box2DStructure(Rectangle rectangle, World world, ContactType contactType, String spritePath) {

        isMovingRight = false;

        this.bounds = rectangle;

        sprite = new Texture(spritePath);

        if (contactType == ContactType.MOVINGFLOOR)
            body = Box2DHelper.createBody(new Box2DBody(rectangle, BodyDef.BodyType.KinematicBody, 0, world, contactType));

        else
            body = Box2DHelper.createBody(new Box2DBody(rectangle, BodyDef.BodyType.StaticBody, 0, world, contactType));
    }

    public void update(){

//        Para poder mover un cuerpo kinematic debemos de indicar su velocidad lineal.
        if (isMovingRight)
            body.setLinearVelocity(1,0);
    }

    public void draw(SpriteBatch batch){
        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    public Texture getSprite() {return sprite;}
}
