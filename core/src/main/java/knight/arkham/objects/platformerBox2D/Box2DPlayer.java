package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DPlayer extends GameObject {

    public Box2DPlayer(Rectangle rectangle, World world, ContactType contactType) {
        super(
                new Box2DBody(rectangle, BodyDef.BodyType.DynamicBody,10, world, contactType),
                new Texture("images/ghost.png")
        );
    }

    public void update() {

        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getLinearVelocity().x <= 7)
            body.applyLinearImpulse(new Vector2(1, 0), body.getWorldCenter(), true);

        else if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getLinearVelocity().x >= -7)
            body.applyLinearImpulse(new Vector2(-1, 0), body.getWorldCenter(), true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && body.getLinearVelocity().y == 0)
            body.applyLinearImpulse(new Vector2(0, 85), body.getWorldCenter(), true);

//        Seguiré probando para ver que cosas útiles puedo hacer con el fixture del personaje
        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
            fixture.setFriction(1.5f);
    }
}
