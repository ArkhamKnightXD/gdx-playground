package knight.arkham.objects.platformerBox2D;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import knight.arkham.helpers.Box2DBody;
import knight.arkham.helpers.Box2DHelper;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

//Probaré aplicar herencias
public abstract class GameObject {

    protected final Body body;
    protected final Fixture fixture;
    private final Rectangle bounds;

//    Debido a que en mi textureRegion se puede comportar como un texture normal, utilizaré el region mejor,
//    ya que el region me brinda la posibilidad de hacer animaciones para mis personajes.
    private TextureRegion actualRegion;

    protected GameObject(Box2DBody gameObjectStructure, TextureRegion region) {
        fixture =  Box2DHelper.createBody(gameObjectStructure);
        body = fixture.getBody();
        bounds = gameObjectStructure.bounds;
        actualRegion = region;
    }

    private Rectangle getBoundsWithPPMCalculation(){

        return new Rectangle(
                body.getPosition().x - (bounds.width / 2 / PIXELS_PER_METER),
                body.getPosition().y - (bounds.height / 2 / PIXELS_PER_METER),
                bounds.width / PIXELS_PER_METER,
                bounds.height / PIXELS_PER_METER
        );
    }

    public void draw(Batch batch) {

        Rectangle actualBounds = getBoundsWithPPMCalculation();

        batch.draw(actualRegion, actualBounds.x, actualBounds.y, actualBounds.width, actualBounds.height);
    }

    public Body getBody() {return body;}

    public TextureRegion getActualRegion() {return actualRegion;}

    protected void setActualRegion(TextureRegion actualRegion) {this.actualRegion = actualRegion;}
}
