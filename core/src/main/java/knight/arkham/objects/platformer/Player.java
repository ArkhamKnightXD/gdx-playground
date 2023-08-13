package knight.arkham.objects.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {

    public boolean isPlayerGrounded;

    public Player(Rectangle bounds) {
        super(bounds, new Texture("images/initial.png"), 350);

        isPlayerGrounded = false;
    }

    public void update(float delta) {

        if (!isPlayerGrounded)
            bounds.y -= speed * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            bounds.x += speed * delta;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            bounds.x -= speed * delta;

        if (isPlayerGrounded && Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            bounds.y += 4000 * delta;
    }
}
