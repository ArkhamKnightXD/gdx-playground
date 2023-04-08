package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

//Clase encargada de manejar la estructura de mis elementos box2D
public class Box2DBody {

    public Rectangle bounds;
    public BodyDef.BodyType bodyType;
    public float density;
    public World world;
    public ContactType contactType;
    public Object userData;


    public Box2DBody(Rectangle bounds, BodyDef.BodyType bodyType, float density, World world, ContactType contactType) {

        this.bounds = bounds;
        this.bodyType = bodyType;
        this.density = density;
        this.world = world;
        this.contactType = contactType;
    }

    public Box2DBody(Rectangle bounds, World world, ContactType contactType) {

        this.bounds = bounds;
        this.bodyType = BodyDef.BodyType.StaticBody;
        this.density = 0;
        this.world = world;
        this.contactType = contactType;
    }

    public Box2DBody(Rectangle bounds, World world) {

        this.bounds = bounds;
        this.bodyType = BodyDef.BodyType.DynamicBody;
        this.world = world;
        this.contactType = ContactType.BULLET;
    }

    public Box2DBody(Rectangle bounds, World world, float density, Object userData) {

        this.bodyType = BodyDef.BodyType.DynamicBody;
        this.density = density;
        this.bounds = bounds;
        this.world = world;
        this.userData = userData;
    }
}
