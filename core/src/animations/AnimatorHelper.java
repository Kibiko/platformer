package animations;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import objects.player.Player;
import org.w3c.dom.Text;

import static components.Constants.screenResHeight;
import static components.Constants.screenResWidth;

public abstract class AnimatorHelper implements ApplicationListener {

    // Constant rows and columns of the sprite sheet
    private static final int FRAME_COLS = 3, FRAME_ROWS = 1;

    // Objects used
    protected Animation<TextureRegion> animation; // Must declare frame type (TextureRegion)
    protected Texture sheet;
    protected SpriteBatch spriteBatch;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public void setUp(String path, int FRAME_COLS, int FRAME_ROWS, float duration, OrthographicCamera camera, SpriteBatch spriteBatch){
        sheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmp = TextureRegion.split(sheet,
                sheet.getWidth()/FRAME_COLS,
                sheet.getHeight()/FRAME_ROWS);

        TextureRegion[] frames = new TextureRegion[FRAME_COLS*FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }

        animation = new Animation<TextureRegion>(duration, frames);

        spriteBatch.setProjectionMatrix(camera.projection);

        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, -screenResWidth/4 + 13, +screenResHeight/4 - 29, 48,16); // Draw current frame at (50, 50)
        spriteBatch.end();
        sheet.dispose();
    }
    public void create() {

    }

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void render(){

    }

    public void render(float xPos, float yPos, OrthographicCamera camera, Player player) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed
        sheet.dispose();
    }

}
