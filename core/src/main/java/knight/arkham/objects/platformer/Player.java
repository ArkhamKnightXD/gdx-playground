package knight.arkham.objects.platformer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player extends GameObject {

    public final Vector2 velocity = new Vector2(0,0);

    public Player(Rectangle bounds) {
        super(bounds, new Texture("images/initial.png"), 50);
    }

    public void update(float deltaTime) {

        //gravity
        velocity.y -= 20.8f * deltaTime;

        //        -- Update the player's position
        bounds.y = bounds.y + velocity.y;
        bounds.x = bounds.x + velocity.x;

        //        To avoid that my player keep going forward infinitely, I multiply the velocity, by my coefficient of friction 0.9
//        This will subtract 10% of the player's speed every frame, eventually bringing the player to a stop.
        velocity.x *= 0.9f;

//                -- Increase the player's x speed
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            velocity.x += speed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            velocity.x -= speed * deltaTime;


        if(bounds.y < 0) {

            bounds.y = 600 - bounds.height;
            bounds.x = 200;
            velocity.y = 0;
        }
    }

    public Rectangle getPreviousPosition() {

        float positionX = bounds.x - velocity.x;
        float positionY = bounds.y - velocity.y;

        return new Rectangle(positionX, positionY, bounds.width, bounds.height);
    }
}
