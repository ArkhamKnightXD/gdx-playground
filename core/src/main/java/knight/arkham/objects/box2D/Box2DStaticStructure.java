package knight.arkham.objects.box2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class Box2DStaticStructure extends Box2DGameObject {

    public Box2DStaticStructure(Rectangle rectangle, World world, ContactType contactType, String spritePath) {

        super(
                new Box2DBody(rectangle, world, contactType),
                new TextureRegion(new Texture(spritePath))
        );
    }
}
