package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import static components.Constants.*;

public class GameOverScreen implements Screen {

    Texture titleImage;
    private final Platformer game;
    private OrthographicCamera camera;
    private Music outroMusic;

    public GameOverScreen(final Platformer game) {
        this.game= game;

        outroMusic = Gdx.audio.newMusic(Gdx.files.internal("8bit_forest_intro.mp3"));
        outroMusic.setVolume(masterVolume);
        outroMusic.setLooping(true);

        camera = new OrthographicCamera(); //orthographic camera is the same scale and not perspective
        camera.setToOrtho(false,screenResWidth,screenResHeight);
        titleImage = new Texture(Gdx.files.internal("title.png"));
    }

    @Override
    public void show() {
        outroMusic.play();
    }

    @Override
    public void render(float delta) {
        this.update();
        ScreenUtils.clear(0.1f,0.1f,0.1f,1); //background

        camera.update(); //update the camera
        game.batch.setProjectionMatrix(camera.combined); //represents the combined view and projection matrix, instructs batch to use that combined matrix

        game.batch.begin();
        game.batch.draw(titleImage,screenResWidth/2 - 400,350,800,200);
        game.font.draw(game.batch, "GAME OVER!",screenResWidth/2 - 50,200);
        game.batch.end();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
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

    }
}
