package knight.arkham.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.FULL_SCREEN_WIDTH;

public class Structure implements Disposable {

    private final Rectangle body;
    private final Texture sprite;

    private int speed;

    public Structure(Rectangle rectangle) {
        body = rectangle;
        sprite = new Texture("images/wall.png");
        speed = 1;
    }

    public void draw(Batch batch){
        batch.draw(sprite, body.x, body.y, body.width, body.height);
    }

    public void floorXAxisMovement(Rectangle playerBody) {

        body.x += speed;
        playerBody.x += speed;

        if (body.x < 0)
            speed = 1;

        if (body.x > FULL_SCREEN_WIDTH - body.width)
            speed = -1;
    }

    public void floorYAxisMovement(Rectangle playerBody) {

        body.y += speed;
        playerBody.y += speed;

        if (body.y < 0)
            speed = 1;

        if (body.y > FULL_SCREEN_HEIGHT - body.height)
            speed = -1;
    }

    public Rectangle getBody() {return body;}

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
