package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.ContactType;

public class Box2DStructure implements Disposable {
    private final Body body;
    private final Rectangle bounds;
    private final Texture wallTexture;

    public Box2DStructure(Rectangle bounds, World world, ContactType contactType, String spritePath) {

        this.bounds = bounds;

        wallTexture = new Texture(spritePath);

        body = Box2DHelper.createBody(new Box2DBody(bounds, true, 0, world, contactType));
    }

    public void draw(SpriteBatch batch){
        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(wallTexture, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    @Override
    public void dispose() {
        wallTexture.dispose();
    }
}
