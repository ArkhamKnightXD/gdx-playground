package knight.arkham.objects.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject {

    protected final Rectangle bounds;
    private final Texture sprite;
    protected int speed;

    protected GameObject(Rectangle actualBounds, Texture actualSprite, int actualSpeed) {

        bounds = actualBounds;
        sprite = actualSprite;
        speed = actualSpeed;
    }

    public void draw(Batch batch){
        batch.draw(sprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {return bounds;}

    public Texture getSprite() {return sprite;}
}
