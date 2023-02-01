package knight.arkham.helpers;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

//Clase encargada de manejar la estructura de mis elementos box2d
public class Box2DBody {

    public Rectangle bounds;
    public boolean isStatic;
    public float density;
    public World world;
    public ContactType contactType;

    public Box2DBody(Rectangle bounds, boolean isStatic, float density, World world, ContactType contactType) {

        this.bounds = bounds;
        this.isStatic = isStatic;
        this.density = density;
        this.world = world;
        this.contactType = contactType;
    }

    public Box2DBody(Rectangle bounds, World world, ContactType contactType) {

        this.bounds = bounds;
        this.world = world;
        this.contactType = contactType;
    }
}
