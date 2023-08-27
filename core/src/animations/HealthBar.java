package animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import objects.player.Player;
import org.w3c.dom.Text;

import static components.Constants.screenResHeight;
import static components.Constants.screenResWidth;

public class HealthBar extends AnimatorHelper {

    private static final int FRAME_COLS = 1, FRAME_ROWS = 1;

    protected Animation<TextureRegion> healthBar; // Must declare frame type (TextureRegion)
    protected Texture healthBarSheet;
    float stateTime;
    @Override
    public void create() {
        healthBarSheet = new Texture(Gdx.files.internal("health_3.png"));

        TextureRegion[][] tmp = TextureRegion.split(healthBarSheet,
                healthBarSheet.getWidth()/FRAME_COLS,
                healthBarSheet.getHeight()/FRAME_ROWS);

        TextureRegion[] healthFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                healthFrames[index++] = tmp[i][j];
            }
        }
        healthBar = new Animation<TextureRegion>(0.5f, healthFrames);

        spriteBatch = new SpriteBatch();
        stateTime = 0f;
    }

    public void renderBar(OrthographicCamera camera, Player player) {
        if (player.getHealth() == 3) {
            setUp("health_3.png", 1, 1, 0.5f, camera, spriteBatch);
        } else if(player.getHealth() == 2){
            setUp("health_2.png", 1, 1, 0.5f, camera, spriteBatch);
        } else if(player.getHealth() == 1){
            setUp("health_1.png", 1, 1, 0.5f, camera, spriteBatch);
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        sheet.dispose();
    }
}
