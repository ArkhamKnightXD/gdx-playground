package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import knight.arkham.objects.platformerBox2D.Box2DPlayer;
import knight.arkham.screens.TileMapBox2DScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final TileMapBox2DScreen gameScreen;

    public TileMapHelper(TileMapBox2DScreen gameScreen) {

        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/test.tmx");

        parseMapObjectsToBox2DBodies(tiledMap, "ground");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (mapObject.getName() != null && mapObject.getName().equals("player")) {

                Box2DPlayer actualPlayer = new Box2DPlayer(
                        new Rectangle(
                                rectangle.x + rectangle.width / 2,
                                rectangle.y + rectangle.height / 2,
                                rectangle.width, rectangle.height
                        ),
                        gameScreen.getWorld(), ContactType.PLAYER
                );

                gameScreen.setPlayer(actualPlayer);

            } else {
                Box2DHelper.createBody(

                        new Box2DBody(

                                new Rectangle(
                                        rectangle.x + rectangle.width / 2,
                                        rectangle.y + rectangle.height / 2,
                                        rectangle.width, rectangle.height
                                ),
                                BodyDef.BodyType.StaticBody, 0,
                                gameScreen.getWorld(), ContactType.FLOOR
                        )
                );
            }
        }
    }
}



