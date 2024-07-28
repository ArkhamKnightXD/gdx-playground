package knight.arkham.objects.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

    public boolean isPlayerGrounded = false;
    private float impulse = 20000;
    private float gravity = 0;
    private float gravityIncrement = -400;
//    private final Vector2 velocity = new Vector2(0,0);

    public Player(Rectangle bounds) {
        super(bounds, new Texture("images/initial.png"), 350);
    }

    public void update(float deltaTime) {

        if (!isPlayerGrounded || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            bounds.y += gravity * deltaTime;
            gravity += gravityIncrement * deltaTime;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            bounds.x += speed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            bounds.x -= speed * deltaTime;

        if (isPlayerGrounded && Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
            gravity = impulse * deltaTime;
    }
}
