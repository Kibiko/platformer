package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import objects.player.Player;
import com.badlogic.gdx.audio.Music;

import static helper.Constants.*;

public class GameScreen implements Screen{

    private final Platformer game;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Music backMusic;
    private TiledMap tiledMap;
    private MapProperties mapProperties;

    //game objects
    private Player player;
    private Sprite sprite;
    private Texture playerImage;

    public GameScreen(final Platformer game){
        this.game = game;

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("8bit_game_music.wav"));
        backMusic.setLooping(true);

        this.world = new World(new Vector2(0,-15f),false); //sets the world
        this.box2DDebugRenderer = new Box2DDebugRenderer(); //used to see how the world looks
        camera = new OrthographicCamera();
        camera.setToOrtho(false, screenResWidth,screenResHeight);

        playerImage = new Texture("fumiko_run_right.png");
        sprite = new Sprite(playerImage);
        sprite.setPosition(Gdx.graphics.getWidth() / 2f - sprite.getWidth() / 2f,
                Gdx.graphics.getHeight() / 2f);

        this.tileMapHelper = new TileMapHelper(this); //tiledmap class
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(); //sets up title map
    }

    @Override
    public void show() {
        backMusic.play();
    }

    public void update(){ //used to update the world
        world.step(1/60f, 6, 2);
        cameraUpdate();
        game.batch.setProjectionMatrix(camera.combined); //sets camera each time
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){ //updates camera if we wish to
        tiledMap = new TmxMapLoader().load("new_map.tmx");
        mapProperties = tiledMap.getProperties();
        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        int tilePixelHeight = mapProperties.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        Vector3 position = camera.position;
        float xPos;
        float yPos;

        xPos = Math.round(player.getBody().getPosition().x * PPM *10)/10f;//more smooth camera movement
        yPos = Math.round(player.getBody().getPosition().y * PPM *10)/10f;
        camera.zoom = 0.5f;

        if(yPos < screenResHeight*camera.zoom/2) { //sets camera if player reaches the edge
            position.y = screenResHeight*camera.zoom/2;
        }
        else if(yPos > (mapPixelHeight/camera.zoom - screenResHeight/2f)/2) { // map height is in tiles
            position.y = (mapPixelHeight/camera.zoom - screenResHeight/2f)/2;
        }
        else {
            position.y = yPos; //if not set camera to player position
        }
        if(xPos < screenResWidth*camera.zoom/2){
            position.x = screenResWidth*camera.zoom/2;
        }
        else if(xPos > (mapPixelWidth/camera.zoom - screenResWidth/2f)/2){
            position.x = (mapPixelWidth/camera.zoom - screenResWidth/2f)/2;
        }
        else {
            position.x = xPos;
        }
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render(); //renders the map
        sprite.setPosition(player.getBody().getPosition().x*PPM - PPM/2,player.getBody().getPosition().y*PPM - PPM/2);

        game.batch.begin();
        //render objects
        game.batch.draw(sprite,sprite.getX(),sprite.getY());

        game.batch.end();
        box2DDebugRenderer.render(world, camera.combined.scl(PPM));

    }

    public World getWorld() {
        return world;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
