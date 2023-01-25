package knight.arkham.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;

public class Player implements Disposable {

    private final Rectangle body;
    private final Texture sprite;
    private final int speed;
    private boolean isPlayerGrounded;
    private boolean canPlayerMoveRight;
    private boolean canPlayerMoveLeft;

    public Player(Rectangle rectangle) {
        body = rectangle;
        speed = 3;
        sprite = new Texture("images/initial.png");

        isPlayerGrounded = false;
        canPlayerMoveRight = true;
        canPlayerMoveLeft = true;
    }

    public void update() {

        if (!isPlayerGrounded)
            body.y -= speed;

        if (canPlayerMoveRight && Gdx.input.isKeyPressed(Input.Keys.D))
            body.x += speed;

        else if (canPlayerMoveLeft && Gdx.input.isKeyPressed(Input.Keys.A))
            body.x -= speed;

        if (isPlayerGrounded && Gdx.input.isKeyPressed(Input.Keys.SPACE))
            body.y += 100;
    }

    public void draw(Batch batch) {
        batch.draw(sprite, body.x, body.y, body.width, body.height);
    }

    public Rectangle getBody() {
        return body;
    }

    public boolean isPlayerGrounded() {
        return isPlayerGrounded;
    }

    public void setPlayerGrounded(boolean playerGrounded) {
        isPlayerGrounded = playerGrounded;
    }

    public void setCanPlayerMoveRight(boolean canPlayerMoveRight) {
        this.canPlayerMoveRight = canPlayerMoveRight;
    }

    public void setCanPlayerMoveLeft(boolean canPlayerMoveLeft) {
        this.canPlayerMoveLeft = canPlayerMoveLeft;
    }

    @Override
    public void dispose() {
        sprite.dispose();
    }
}
