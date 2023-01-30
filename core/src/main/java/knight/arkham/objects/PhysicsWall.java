package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;

public class PhysicsWall implements Disposable {
    private final float positionX;
    private final float positionY;
    private final int width;
    private final int height;
    private final Texture wallTexture;

    public PhysicsWall(float positionY, float positionX, int width, int height, ContactType contactType, World world) {

        this.positionY = positionY;
        this.positionX = positionX;
        this.width = width;
        this.height = height;
        wallTexture = new Texture("images/initial.png");

        Box2DHelper.createBody(new Box2DBody(new Rectangle(positionX, positionY, width, height),
                true, 0, world, contactType));
    }

    public void draw(SpriteBatch batch){
        batch.draw(wallTexture, positionX - (width / 2), positionY - (height / 2), width, height);
    }

    public Texture getWallTexture() {return wallTexture;}

    @Override
    public void dispose() {
        wallTexture.dispose();
    }
}
