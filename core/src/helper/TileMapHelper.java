package helper;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platform.game.GameScreen;
import objects.player.Player;

import static helper.Constants.PPM;


public class TileMapHelper {

    private TiledMap tiledMap;
    private GameScreen gameScreen;

    public TileMapHelper(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }

    public OrthogonalTiledMapRenderer setupMap(){
        tiledMap = new TmxMapLoader().load("new_map.tmx");
        parseMapObjects(tiledMap.getLayers().get("objects").getObjects()); //loads new map and parses the objects
        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObjects(MapObjects mapObjects){ //for map objects we create a static body
        for (MapObject mapObject : mapObjects){

            if(mapObject instanceof PolygonMapObject){
                String polygonName = mapObject.getName();
                if(polygonName.equals("water")){
                    createWaterBody((PolygonMapObject) mapObject);
                } else if (polygonName.equals("platform") || polygonName.equals("slope")){
                    createPlatformBody((PolygonMapObject) mapObject);
                } else{
                    createStaticBody((PolygonMapObject) mapObject);
                }
            }

            if(mapObject instanceof RectangleMapObject){ //this represents our player
                Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                String rectangleName = mapObject.getName();

                if(rectangleName.equals("player")){
                    Body body = BodyHelperService.createBody(rectangle.getX() + rectangle.getWidth()/2,
                            rectangle.getY() + rectangle.getHeight()/2,
                            rectangle.getWidth(),
                            rectangle.getHeight(),
                            false,
                            gameScreen.getWorld());
                    body.setUserData("PLAYER");
                    gameScreen.setPlayer(new Player(rectangle.getWidth(), rectangle.getHeight(), body));
                }
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject){ //method for creating new static body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private void createPlatformBody(PolygonMapObject polygonMapObject){ //method for creating new static body
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        body.setUserData("PLATFORM");
        shape.dispose();
    }

    private void createWaterBody(PolygonMapObject polygonMapObject){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = gameScreen.getWorld().createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 0);
        makeAllFixturesSensors(body);
        body.setUserData("WATER");
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) { //sets the polygon shape
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length /2];

        for(int i = 0; i< vertices.length/2; i++){
            Vector2 current = new Vector2(vertices[i*2]/PPM,vertices[i*2+1]/PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    public void makeAllFixturesSensors(Body body){
        for(Fixture fix :body.getFixtureList()){
            fix.setSensor(true);
        }
    }
}
