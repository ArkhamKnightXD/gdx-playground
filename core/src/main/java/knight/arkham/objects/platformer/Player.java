package knight.arkham.objects.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

    public boolean isPlayerGrounded = false;
    public final Vector2 velocity = new Vector2(0,0);

    public Player(Rectangle bounds) {
        super(bounds, new Texture("images/initial.png"), 350);

    }

    public void update(float deltaTime) {

        //gravity
        bounds.y = bounds.y + velocity.y;
        velocity.y -= 0.4f;

        if (isPlayerGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            velocity.y = 10;

        if(bounds.y < 0) {

            bounds.y = 600 - bounds.height;
            velocity.y = 0;
        }

//        -- Update the player's position
        bounds.x = bounds.x + velocity.x;
//                -- Increase the player's speed

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velocity.x += 1;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            velocity.x -= 1;

//        To avoid that my player keep going forward infinitely, I multiply the velocity, but my coefficient of friction 0.9
//        This will subtract 10% of the player's speed every frame, eventually bringing the player to a stop.
        velocity.x *= 0.9f;
    }
}
