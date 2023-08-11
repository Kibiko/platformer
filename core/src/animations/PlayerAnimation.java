package animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import objects.player.Player;

public class PlayerAnimation extends AnimatorHelper {

    private static final int FRAME_COLS = 3, FRAME_ROWS = 1;

    // Objects used
    Animation<TextureRegion> walkAnimation; // Must declare frame type (TextureRegion)
    Texture walkSheet;
    SpriteBatch spriteBatch;

    // A variable for tracking elapsed time for the animation
    float stateTime;

    public void create(){

        // Load the sprite sheet as a Texture
        walkSheet = new Texture(Gdx.files.internal("fumiko_idle.png"));

        // Use the split utility method to create a 2D array of TextureRegions. This is
        // possible because this sprite sheet contains frames of equal size and they are
        // all aligned.
        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }

        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(0.12f, walkFrames);

        // Instantiate a SpriteBatch for drawing and reset the elapsed animation
        // time to 0
        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void render(float xPos, float yPos, OrthographicCamera camera, Player player) {
        boolean loop = true;
        float frameDuration = 0.12f;

        //swimming
//        if(player.isInWater()) {
//            if (player.getDirection() && player.getBody().getLinearVelocity().x == 0) {
//
//            } else if (!player.getDirection() && player.getBody().getLinearVelocity().x == 0) {
//
//            } else if (player.getDirection() && player.getBody().getLinearVelocity().x > 0) {
//
//            } else if (!player.getDirection() && player.getBody().getLinearVelocity().x < 0) {
//
//            } else if (player.getDirection() && player.getBody().getLinearVelocity().y < 0) {
//
//            } else if (!player.getDirection() && player.getBody().getLinearVelocity().y < 0) {
//
//            }
//        }

        //jumping
        if(player.isAirborne()) {
            loop = false;
            if (player.getDirection() && player.getBody().getLinearVelocity().y < 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_down_right.png"));
            } else if (!player.getDirection() && player.getBody().getLinearVelocity().y < 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_down_left.png"));
            } else if (player.getDirection() && player.getBody().getLinearVelocity().y > 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_up_right.png"));
            } else if (!player.getDirection() && player.getBody().getLinearVelocity().y > 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_up_left.png"));
            }
        //running
        } else if (!player.isAirborne()) {
            if (player.getBody().getLinearVelocity().x == 0 && player.getDirection()) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_idle_right.png"));
            } else if (player.getBody().getLinearVelocity().x == 0 && !player.getDirection()) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_idle_left.png"));
            } else if (player.getBody().getLinearVelocity().x > 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_animate_right.png"));
            } else if (player.getBody().getLinearVelocity().x < 0) {
                walkSheet = new Texture(Gdx.files.internal("fumiko_animate_left.png"));
            }
        }

        TextureRegion[][] tmp = TextureRegion.split(walkSheet,
                walkSheet.getWidth() / FRAME_COLS,
                walkSheet.getHeight() / FRAME_ROWS);

        // Place the regions into a 1D array in the correct order, starting from the top
        // left, going across first. The Animation constructor requires a 1D array.
        TextureRegion[] walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        // Initialize the Animation with the frame interval and array of frames
        walkAnimation = new Animation<TextureRegion>(frameDuration, walkFrames);

        spriteBatch.setProjectionMatrix(camera.combined);

        stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = walkAnimation.getKeyFrame(stateTime, loop);
        spriteBatch.begin();
        spriteBatch.draw(currentFrame, xPos, yPos); // Draw current frame at (50, 50)
        spriteBatch.end();
    }

    public void dispose(){
        spriteBatch.dispose();
        walkSheet.dispose();
    }
}
