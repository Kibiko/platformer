package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import helper.TileMapHelper;
import objects.player.Player;

import static helper.Constants.PPM;

public class GameScreen implements Screen{

    private final Platformer game;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;

    //game objects
    private Player player;

    public GameScreen(final Platformer game){
        this.game = game;
        this.world = new World(new Vector2(0,-9.81f),false); //sets the world
        this.box2DDebugRenderer = new Box2DDebugRenderer(); //used to see how the world looks
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 960, 640);

        this.tileMapHelper = new TileMapHelper(this); //tiledmap class
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(); //sets up title map
    }

    @Override
    public void show() {

    }

    public void update(){ //used to update the world
        world.step(1/60f, 6, 2);
        cameraUpdate();
        game.batch.setProjectionMatrix(camera.combined); //sets camera each time
        orthogonalTiledMapRenderer.setView(camera);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
    }

    private void cameraUpdate(){ //updates camera if we wish to
        Vector3 position = camera.position;
        position.x = Math.round(player.getBody().getPosition().x * PPM *10)/10f; //more smooth camera movement
        position.y = Math.round(player.getBody().getPosition().y * PPM *10)/10f;
        camera.position.set(position);
        camera.update();
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render(); //renders the map

        game.batch.begin();
        //render objects

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
