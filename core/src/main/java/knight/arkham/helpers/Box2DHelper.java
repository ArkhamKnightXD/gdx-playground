package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static knight.arkham.helpers.Constants.*;

public class Box2DHelper {

    public static Body createPlayerBody(Box2DBody box2DBody){

        Body body = createBox2DBodyByType(box2DBody);

        CircleShape circleShape = new CircleShape();

        FixtureDef fixtureDef = new FixtureDef();

        circleShape.setRadius(12 / PIXELS_PER_METER);

        fixtureDef.shape = circleShape;

        fixtureDef.density = box2DBody.density;
        fixtureDef.friction = 0.1f;

        fixtureDef.filter.categoryBits = MARIO_BIT;

        fixtureDef.filter.maskBits = (short) (GROUND_BIT | ITEM_BIT | COIN_BIT | BRICK_BIT | OBJECT_BIT | ENEMY_BIT | ENEMY_HEAD_BIT);

        circleShape.dispose();

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        EdgeShape headCollider = makePlayerHeadCollider(fixtureDef);

        body.createFixture(fixtureDef).setUserData(box2DBody.userData);

        headCollider.dispose();

        return body;
    }


    private static EdgeShape makePlayerHeadCollider(FixtureDef fixtureDefinition) {

        EdgeShape headCollider = new EdgeShape();

        headCollider.set(new Vector2(-2 / PIXELS_PER_METER, 7 / PIXELS_PER_METER),
                new Vector2(2 / PIXELS_PER_METER, 7 / PIXELS_PER_METER));

        fixtureDefinition.shape = headCollider;

        fixtureDefinition.isSensor = true;

        fixtureDefinition.filter.categoryBits = MARIO_HEAD_BIT;

        return headCollider;
    }

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

    public static Vector2 getSimplifiedCurrentPosition(Body body) {

        return new Vector2(body.getPosition().x * PIXELS_PER_METER, body.getPosition().y * PIXELS_PER_METER);
    }
}
