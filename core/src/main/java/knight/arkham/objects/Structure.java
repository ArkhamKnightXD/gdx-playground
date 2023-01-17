package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Structure {

    private final Rectangle bounds;
    private final Texture sprite;

    public Structure(Rectangle rectangle) {
        bounds = rectangle;
        sprite = new Texture("images/initial.png");
    }

    public void draw(Batch batch){
        batch.draw(sprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {return bounds;}
}
