package knight.arkham.helpers;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import knight.arkham.objects.box2D.Box2DEnemy;

import static knight.arkham.helpers.Constants.MID_SCREEN_WIDTH;
import static knight.arkham.helpers.Constants.PIXELS_PER_METER;

public class TileMapHelper {

    private final World world;
    private final TextureRegion enemyRegion;
    private final Array<Box2DEnemy> enemies;

    private TiledMap tiledMap;

    public TileMapHelper(World world, TextureRegion enemyRegion, Array<Box2DEnemy> enemies) {

        this.world = world;
        this.enemyRegion = enemyRegion;
        this.enemies = enemies;
    }

    public OrthogonalTiledMapRenderer setupMap(String mapFilePath) {

        tiledMap = new TmxMapLoader().load(mapFilePath);

        parseMapObjectsToBox2DBodies(tiledMap, "Collisions");
        parseMapObjectsToBox2DBodies(tiledMap, "Enemies");

        return new OrthogonalTiledMapRenderer(tiledMap, 1 / PIXELS_PER_METER);
    }

    private void parseMapObjectsToBox2DBodies(TiledMap tiledMap, String objectsName) {

        MapObjects mapObjects = tiledMap.getLayers().get(objectsName).getObjects();

        for (MapObject mapObject : mapObjects) {

            Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();

            if (objectsName.equals("Enemies"))
                createEnemyBody(rectangle);

            else
                Box2DHelper.createStaticBody(rectangle, world);
        }
    }

    private void createEnemyBody(Rectangle rectangle) {

        Box2DEnemy actualEnemy = new Box2DEnemy(
                new Rectangle (
                        rectangle.x + rectangle.width / 2,
                        rectangle.y + rectangle.height / 2,
                        rectangle.width, rectangle.height
                ),
                world, enemyRegion
        );

        enemies.add(actualEnemy);
    }

    public boolean checkIfPlayerIsInsideMapBounds(Vector2 playerPixelPosition){
        MapProperties properties = tiledMap.getProperties();

        int mapWidth = properties.get("width", Integer.class);
//        int mapHeight = properties.get("height", Integer.class);
        int tilePixelWidth = properties.get("tilewidth", Integer.class);
//        int tilePixelHeight = properties.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
//        int mapPixelHeight = mapHeight * tilePixelHeight;

        return playerPixelPosition.x > MID_SCREEN_WIDTH && playerPixelPosition.x < mapPixelWidth-MID_SCREEN_WIDTH;
    }
}
