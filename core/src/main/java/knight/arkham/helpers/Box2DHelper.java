package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class Box2DHelper {

    public static Body createBody(Box2DBody box2DBody) {

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height / 2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        if (box2DBody.contactType == ContactType.SLIPPERYFLOOR)
            fixtureDef.friction = 0.001f;

        if (box2DBody.contactType == ContactType.SNOWFLOOR)
            fixtureDef.friction = 1.5f;

        if (box2DBody.contactType == ContactType.TRAMPOLINE)
            fixtureDef.restitution = 1;

//        Si mi personaje tiene un dynamic body puedo jugar con la densidad, para que se mueva o caiga
//        más rápido o más lento
        fixtureDef.density = box2DBody.density;

        Body body = createBox2DBodyByType(box2DBody);

        body.createFixture(fixtureDef).setUserData(box2DBody.contactType);

        shape.dispose();

        return body;
    }

    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }

    public static Rectangle getBoundsWithPPMCalculation(Body body, Rectangle bounds){

        return new Rectangle(body.getPosition().x - (bounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (bounds.height / 2 / PIXELS_PER_METER), bounds.width / PIXELS_PER_METER,
                bounds.height /PIXELS_PER_METER);
    }
}
