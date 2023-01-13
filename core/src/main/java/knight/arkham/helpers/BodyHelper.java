package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class BodyHelper {

    public static Body createBody(Box2DBody box2DBody){

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y /PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        Body body = box2DBody.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.bounds.width / 2 / PIXELS_PER_METER, box2DBody.bounds.height /2 / PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = box2DBody.density;

        body.createFixture(fixtureDef).setUserData(box2DBody.contactType);

        shape.dispose();

        return body;
    }
}
