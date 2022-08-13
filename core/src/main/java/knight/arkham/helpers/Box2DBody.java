package knight.arkham.helpers;

import com.badlogic.gdx.physics.box2d.World;

//Clase encargada de manejar la estructura de mis elementos box2d
public class Box2DBody {

    public float xPosition;
    public float yPosition;
    public float width;
    public float height;
    public boolean isStatic;
    public float density;
    public World world;
    public ContactType contactType;

    public Box2DBody(float xPosition, float yPosition, float width, float height,
                     boolean isStatic, float density, World world, ContactType contactType) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.isStatic = isStatic;
        this.density = density;
        this.world = world;
        this.contactType = contactType;
    }
}
