package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DStructure extends GameObject {

    public boolean isMovingRight;

    public Box2DStructure(Rectangle rectangle, World world, ContactType contactType, String spritePath) {

        super(
                new Box2DBody(
                        rectangle,
// Determinando si quiero que el body sea kinematic o static
                        contactType == ContactType.MOVINGFLOOR ?
                                BodyDef.BodyType.KinematicBody
                                : BodyDef.BodyType.StaticBody,
                        0,
                        world,
                        contactType
                ),

                new Texture(spritePath)
        );

        isMovingRight = false;
    }

    public void update(){

//        Para poder mover un cuerpo kinematic debemos de indicar su velocidad lineal.
        if (isMovingRight)
            body.setLinearVelocity(1,0);

        else
            body.setLinearVelocity(-1,0);
    }
}
