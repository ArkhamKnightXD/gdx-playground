package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//Probaré aplicar herencias
public abstract class GameObject {

    protected final Body body;
    private final Rectangle bounds;
    private final Texture sprite;

    protected GameObject(Box2DBody gameObjectStructure, Texture sprite) {
        this.body =  Box2DHelper.createBody(gameObjectStructure);
        this.bounds = gameObjectStructure.bounds;
        this.sprite = sprite;
    }

    private Rectangle getBoundsWithPPMCalculation(){

        return new Rectangle(body.getPosition().x - (bounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (bounds.height / 2 / PIXELS_PER_METER), bounds.width / PIXELS_PER_METER,
                bounds.height /PIXELS_PER_METER);
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = getBoundsWithPPMCalculation();

        batch.draw(sprite, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    public Texture getSprite() {return sprite;}
}
