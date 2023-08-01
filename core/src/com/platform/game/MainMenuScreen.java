package com.platform.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen {

    Texture titleImage;
    private final Platformer game;
    private OrthographicCamera camera;

    public MainMenuScreen(final Platformer game){
        this.game= game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,960,640);
        titleImage = new Texture(Gdx.files.internal("title.png"));
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f,0.1f,0.1f,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(titleImage,80,350,800,200);
        game.font.draw(game.batch, "Tap Anywhere To Begin!",400,200);
        game.batch.end();

        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
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

    }
}
