package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class BodyHelper {

    public static Body createBody(Box2DBody box2DBody){

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = !box2DBody.isStatic ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody;

        bodyDef.position.set(box2DBody.xPosition / Constants.PIXELS_PER_METER,
                box2DBody.yPosition /Constants.PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        Body body = box2DBody.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        shape.setAsBox(box2DBody.width / 2 / Constants.PIXELS_PER_METER,
                box2DBody.height /2 /Constants.PIXELS_PER_METER);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = box2DBody.density;


        body.createFixture(fixtureDef).setUserData(box2DBody.contactType);

        shape.dispose();

        return body;
    }
}
