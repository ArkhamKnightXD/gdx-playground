package knight.arkham;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import knight.arkham.screens.MainMenuScreen;
import knight.arkham.screens.PlatformerBox2DScreen;

public class Playground extends Game {
	public static Playground INSTANCE;

	public SpriteBatch batch;
	public BitmapFont font;

	private int screenWidth;
	private int screenHeight;

	public Playground() {

		INSTANCE = this;
	}

	@Override
	public void create() {

		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new PlatformerBox2DScreen());
	}


	public void manageExitTheGame() {

		if (Gdx.input.isKeyPressed(Input.Keys.F12))
			setScreen(new MainMenuScreen());

		else if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
			Gdx.app.exit();
	}

	public int getScreenWidth() { return screenWidth; }

	public int getScreenHeight() { return screenHeight; }
}