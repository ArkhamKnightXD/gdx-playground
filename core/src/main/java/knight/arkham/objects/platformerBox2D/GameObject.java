package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.helpers.Box2DHelper;

//Probaré aplicar herencias
public abstract class GameObject {

    private final Body body;
    private final Rectangle bounds;
    private final Texture sprite;

    protected GameObject(Body body, Rectangle bounds, Texture sprite) {
        this.body = body;
        this.bounds = bounds;
        this.sprite = sprite;
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = Box2DHelper.getBoundsWithPPMCalculation(body, bounds);

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    public Texture getSprite() {return sprite;}
}
