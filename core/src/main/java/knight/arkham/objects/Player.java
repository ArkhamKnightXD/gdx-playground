package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {

    private final Rectangle bounds;
    private final Texture sprite;
    private final int speed;
    private boolean isPlayerGrounded;


    public Player(Rectangle rectangle) {
        bounds = rectangle;
        speed = 400;
        sprite = new Texture("images/initial.png");

        isPlayerGrounded = false;
    }

    public void update(float deltaTime) {

        if (!isPlayerGrounded)
            bounds.y -= speed * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            bounds.x += speed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            bounds.x -= speed * deltaTime;

        if (isPlayerGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            bounds.y += 75;
    }

    public void draw(Batch batch) {
        batch.draw(sprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isPlayerGrounded() {
        return isPlayerGrounded;
    }

    public void setPlayerGrounded(boolean playerGrounded) {
        isPlayerGrounded = playerGrounded;
    }

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
