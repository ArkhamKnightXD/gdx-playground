package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import knight.arkham.helpers.Box2DHelper;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.ContactType;
import knight.arkham.screens.Box2DScreen;

public class PhysicsWall {
    private final float positionX;
    private final float positionY;
    private final int width;
    private final int height;
    private final Texture wallTexture;

    public PhysicsWall(float positionY, float positionX, int width, int height, ContactType contactType, Box2DScreen gameScreen) {

        this.positionY = positionY;
        this.positionX = positionX;
        this.width = width;
        this.height = height;
        wallTexture = new Texture("images/initial.png");

        Box2DHelper.createBody(new Box2DBody(new Rectangle(positionX, positionY, width, height),
                true, 0, gameScreen.getGameWorld(), contactType));
    }

    public void draw(SpriteBatch batch){
        batch.draw(wallTexture, positionX - (width / 2), positionY - (height / 2), width, height);
    }

    public Texture getWallTexture() {return wallTexture;}
}
