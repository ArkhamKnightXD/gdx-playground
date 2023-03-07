package knight.arkham.objects.platformer;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;
import static knight.arkham.helpers.Constants.FULL_SCREEN_WIDTH;

public class Structure extends GameObject {

    public Structure(Rectangle actualBounds, String spritePath) {
        super(actualBounds, new Texture(spritePath), 1);
    }

    public void floorXAxisMovement(Rectangle playerBody) {

        bounds.x += speed;
        playerBody.x += speed;

        if (bounds.x < 32)
            speed = 1;

        if (bounds.x > FULL_SCREEN_WIDTH - bounds.width)
            speed = -1;
    }

    public void floorYAxisMovement(Rectangle playerBody) {

        bounds.y += speed;
        playerBody.y += speed;

        if (bounds.y < 0)
            speed = 1;

        if (bounds.y > FULL_SCREEN_HEIGHT - bounds.height*2)
            speed = -1;
    }
}
