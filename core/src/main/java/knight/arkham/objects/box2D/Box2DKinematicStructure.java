package knight.arkham.objects.box2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DKinematicStructure extends Box2DGameObject {

    public boolean isMovingRight;

    public Box2DKinematicStructure(Rectangle rectangle, World world, String spritePath) {

        super(
                new Box2DBody(rectangle, BodyDef.BodyType.KinematicBody, 0, world, ContactType.MOVINGFLOOR),
                new TextureRegion(new Texture(spritePath))
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
