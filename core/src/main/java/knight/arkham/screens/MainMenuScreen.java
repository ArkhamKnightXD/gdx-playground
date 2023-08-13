package knight.arkham.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import knight.arkham.Playground;

import static knight.arkham.helpers.Constants.*;
import static knight.arkham.helpers.Constants.FULL_SCREEN_HEIGHT;

public class MainMenuScreen extends ScreenAdapter {

    private final Playground game;
    private final Skin skin;
    private final Stage stage;
    private final Table table;
    private final Viewport viewport;
    public MainMenuScreen() {

        game = Playground.INSTANCE;

        AssetManager assetManager = new AssetManager();

        AssetDescriptor<Skin> uiSkin = new AssetDescriptor<>("ui/uiskin.json", Skin.class, new SkinLoader.SkinParameter("ui/uiskin.atlas"));

        assetManager.load(uiSkin);

        assetManager.finishLoading();

        skin = assetManager.get(uiSkin);

        viewport = new ExtendViewport(FULL_SCREEN_WIDTH, FULL_SCREEN_HEIGHT);

        stage = new Stage(viewport);

        table = new Table();

        table.setFillParent(true);

        Label pauseLabel = new Label("Main Menu", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(pauseLabel).expandX().padBottom(15);
        table.row();

        stage.addActor(table);

        addButton("Basic Screen").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new BasicScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Shape Renderer").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ShapeRendererScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Platformer").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlatformerScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Box2D").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new Box2DScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Box 2D Platformer").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new PlatformerBox2DScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Tile Map Platformer").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new TileMapBox2DScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Basic Zelda Like").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new ZeldaLikeScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        addButton("Quit").addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return super.touchDown(event, x, y, pointer, button);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    private TextButton addButton(String buttonName) {

        TextButton textButton = new TextButton(buttonName, skin);

        table.add(textButton).width(400).height(50).padBottom(15);
        table.row();

        return textButton;
    }
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(0, 0, 0, 0);

        stage.act();
        stage.draw();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        stage.dispose();
        skin.dispose();
    }
}
