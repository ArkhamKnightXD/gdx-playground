package knight.arkham.objects.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class Player extends GameObject {

    public boolean isPlayerGrounded;

    public Player(Rectangle bounds) {
        super(bounds, new Texture("images/initial.png"), 3);

        isPlayerGrounded = false;
    }

    public void update() {

        if (!isPlayerGrounded)
            bounds.y -= speed;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            bounds.x += speed;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            bounds.x -= speed;

        if (isPlayerGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            bounds.y += 100;
    }
}
