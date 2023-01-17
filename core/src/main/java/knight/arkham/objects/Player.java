package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Player {

    private final Rectangle bounds;
    private final Texture sprite;
    private final int speed;

    public Player(Rectangle rectangle) {
        bounds = rectangle;
        speed = 400;
        sprite = new Texture("images/initial.png");
    }

    public void update(float deltaTime){

        bounds.y -= speed * deltaTime;

        if (Gdx.input.isKeyPressed(Input.Keys.D))
            bounds.x += speed * deltaTime;

        else if (Gdx.input.isKeyPressed(Input.Keys.A))
            bounds.x -= speed * deltaTime;

//        else if (Gdx.input.isKeyPressed(Input.Keys.W))
//            bounds.y += speed * deltaTime;
    }

    public void draw(Batch batch){
        batch.draw(sprite, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public Rectangle getBounds() {return bounds;}
}
