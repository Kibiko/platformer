package com.platform.game;

import animations.HealthBar;
import animations.PlayerAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import contactSolvers.B2dContactListener;
import helper.CameraHelper;
import helper.TileMapHelper;
import objects.player.Player;
import com.badlogic.gdx.audio.Music;

import static components.Constants.*;

public class GameScreen implements Screen{

    private final Platformer game;
    private World world;
    private Box2DDebugRenderer box2DDebugRenderer;
    private CameraHelper camera;
    private OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private TileMapHelper tileMapHelper;
    private Music backMusic;

    //game objects
    private Player player;
    private Sprite sprite;
    private Texture playerImage;

    //animation test
    private PlayerAnimation playerAnimation;
    private HealthBar healthBar;

    private boolean debug = false;

    public GameScreen(final Platformer game){
        this.game = game;

        backMusic = Gdx.audio.newMusic(Gdx.files.internal("8bit_game_music.wav"));
        backMusic.setVolume(masterVolume);
        backMusic.setLooping(true);

        this.world = new World(new Vector2(0,-15f),false); //sets the world
        if(debug) {
            this.box2DDebugRenderer = new Box2DDebugRenderer(); //used to see how the world looks
        }
        camera = new CameraHelper();
        camera.setToOrtho(false, screenResWidth,screenResHeight);

//        playerImage = new Texture("fumiko_run_right.png");
//        sprite = new Sprite(playerImage);
//        sprite.setPosition(Gdx.graphics.getWidth() / 2f - sprite.getWidth() / 2f,
//                Gdx.graphics.getHeight() / 2f);

        this.tileMapHelper = new TileMapHelper(this); //tiledmap class
        this.orthogonalTiledMapRenderer = tileMapHelper.setupMap(); //sets up title map

        playerAnimation = new PlayerAnimation();
        healthBar = new HealthBar();
        playerAnimation.create();
        healthBar.create();
        world.setContactListener(new B2dContactListener(player));
    }

    @Override
    public void show() {
        backMusic.play();
    }

    public void update(){ //used to update the world
        world.step(1/60f, 6, 2);
        camera.cameraUpdate(player);
        game.batch.setProjectionMatrix(camera.combined); //sets camera each time
        orthogonalTiledMapRenderer.setView(camera);
        player.update();

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
            dispose();
        }
    }

    @Override
    public void render(float delta) {
        this.update();

        Gdx.gl.glClearColor(0.1f,0.1f,0.1f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        orthogonalTiledMapRenderer.render(); //renders the map
//        sprite.setPosition(player.getBody().getPosition().x*PPM - PPM/2,player.getBody().getPosition().y*PPM - PPM/2);

        playerAnimation.render(player.getBody().getPosition().x*PPM - PPM/2,
                player.getBody().getPosition().y*PPM - PPM/2,
                camera,
                player);
        healthBar.renderBar(camera, player);

//        game.batch.begin();
//        //render objects
//        game.batch.draw(sprite,sprite.getX(),sprite.getY());
//        game.batch.end();
        if(debug) {
            box2DDebugRenderer.render(world, camera.combined.scl(PPM));
        }

        if(player.getHealth() == 0){
            game.setScreen(new GameOverScreen(game));
            dispose();
        }
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
        backMusic.dispose();
        world.dispose();
    }
}
