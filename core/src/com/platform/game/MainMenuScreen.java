package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import static helper.Constants.*;


public class MainMenuScreen implements Screen {

    Texture titleImage;
    private final Platformer game;
    private OrthographicCamera camera;
    private Music introMusic;
    private int viewPortHeight;
    private int viewPortWidth;

    public MainMenuScreen(final Platformer game){
        this.game= game;

        introMusic = Gdx.audio.newMusic(Gdx.files.internal("8bit_forest_intro.mp3"));
        introMusic.setVolume(masterVolume);
        introMusic.setLooping(true);

        camera = new OrthographicCamera(); //orthographic camera is the same scale and not perspective
        camera.setToOrtho(false,screenResWidth,screenResHeight);
        titleImage = new Texture(Gdx.files.internal("title.png"));
    }

    @Override
    public void show() {
        introMusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f,0.1f,0.1f,1); //background
        camera.update(); //update the camera
        game.batch.setProjectionMatrix(camera.combined); //represents the combied view and projection matrix, instructs batch to use that combined matrix

        game.batch.begin();
        game.batch.draw(titleImage,screenResWidth/2 - 400,350,800,200);
        game.font.draw(game.batch, "Click Anywhere To Begin!",screenResWidth/2 - 100,200);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game)); //set new screen if it is touched
            dispose();
        }
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
        introMusic.dispose();
    }
}
