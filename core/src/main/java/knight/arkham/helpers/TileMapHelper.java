package knight.arkham.helpers;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.platformerBox2D.Box2DEnemy;
import knight.arkham.screens.TileMapBox2DScreen;

import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final TileMapBox2DScreen gameScreen;

    private final Array<Box2DEnemy> enemies;


    public TileMapHelper(TileMapBox2DScreen gameScreen) {

        this.gameScreen = gameScreen;
        enemies = new Array<>();
    }

    public OrthogonalTiledMapRenderer setupMap() {

        TiledMap tiledMap = new TmxMapLoader().load("maps/test.tmx");

        parseMapObjectsToBox2DBodies(tiledMap, "collisions");
        parseMapObjectsToBox2DBodies(tiledMap, "enemies");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (objectsName.equals("enemies")) {

                Box2DEnemy actualEnemy = new Box2DEnemy(
                        new Rectangle(
                                rectangle.x + rectangle.width / 2,
                                rectangle.y + rectangle.height / 2,
                                rectangle.width, rectangle.height
                        ),
                        gameScreen.getWorld(), ContactType.ENEMY, gameScreen.getTextureAtlas());

                enemies.add(actualEnemy);
            }

            else {
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

        gameScreen.setEnemies(enemies);
    }
}



