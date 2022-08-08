package knight.arkham;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class FirstScreen extends ScreenAdapter {

	private final Playground game = Playground.INSTANCE;
	private final OrthographicCamera camera;


	public FirstScreen(OrthographicCamera globalCamera) {

		camera = globalCamera;
	}

	@Override
	public void show() {
		// Prepare your screen here.
	}

	@Override
	public void render(float delta) {

		ScreenUtils.clear(0,0,0,0);

		camera.update();

		game.batch.begin();

		//dibujo las imagenes en las posiciones ya indicadas, tambien muestro un texto con la cantidad de gotas conseguidas

		game.font.draw(game.batch, "Player Lives: ", 0, 0);
//		game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);


		game.batch.end();
	}


	@Override
	public void hide() {
		// This method is called when another screen replaces this one.
	}

	@Override
	public void dispose() {
		// Destroy screen's assets here.
	}
}