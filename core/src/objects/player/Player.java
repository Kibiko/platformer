package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import static helper.Constants.PPM;

public class Player extends GameEntity{

    private int jumpCounter;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
        this.jumpCounter = 0;
    }

    @Override
    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkPlayerOutBounds();
        checkUserInput();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkUserInput(){
        velX = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.D) && body.getPosition().x <= 19.5){
            velX = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && body.getPosition().x >= 0.5){
            velX = -1;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter <2){ //release it to go for second jump, just pressed
            float force = body.getMass() * 7;
            body.setLinearVelocity(body.getLinearVelocity().x,0); //allows double jump
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true); //apply force to y direction in vector2
            jumpCounter++;
        }

        if(body.getLinearVelocity().y == 0){ //if body hits the floor and has no y velocity
            jumpCounter = 0;
        }

        body.setLinearVelocity(velX * speed,body.getLinearVelocity().y);
    }

    private void checkPlayerOutBounds(){
        if (body.getPosition().y < 0){
             body.setTransform(1,5,0);
        }
    }
}
