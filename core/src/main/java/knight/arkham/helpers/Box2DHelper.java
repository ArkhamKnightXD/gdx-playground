package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static void createStaticBody(Rectangle floorBounds, World world) {

        Box2DBody box2DBody = new Box2DBody(

                new Rectangle(
                        floorBounds.x + floorBounds.width / 2,
                        floorBounds.y + floorBounds.height / 2,
                        floorBounds.width, floorBounds.height
                ), world, ContactType.FLOOR
        );

        createBody(box2DBody);
    }

    public static Fixture createBody(Box2DBody box2DBody) {

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

        Fixture fixture = body.createFixture(fixtureDef);

        fixture.setUserData(box2DBody.contactType);

        shape.dispose();

//        Tiene más utilidad tener el fixture, pues este contiene el body y
//        más propiedades para modificar como por ejemplo la friction
        return fixture;
    }

    private static Body createBox2DBodyByType(Box2DBody box2DBody) {

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = box2DBody.bodyType;

        bodyDef.position.set(box2DBody.bounds.x / PIXELS_PER_METER, box2DBody.bounds.y / PIXELS_PER_METER);

        bodyDef.fixedRotation = true;

        return box2DBody.world.createBody(bodyDef);
    }
}
