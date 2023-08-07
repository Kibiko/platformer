package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import helper.CameraHelper;

import static helper.Constants.PPM;

public class Player extends GameEntity{

    private int jumpCounter;

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
        this.jumpCounter = 0;
    }

    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkPlayerOutBounds();
        checkPlayerAirborne();
        checkPlayerWater();
        checkUserInput();
        maxSpeed();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkPlayerAirborne(){
        if(body.getLinearVelocity().y !=0){
            airborne = true;
        } else{
            airborne = false;
        }
    }

    private void checkPlayerWater(){
        if(isSwimming()){
            float playerMotion = getBody().getLinearVelocity().y;
            if(playerMotion<0) {
                getBody().applyForceToCenter(0, 25 - playerMotion, true);
            } else if (playerMotion >0){
                getBody().applyForceToCenter(0,17 - playerMotion, true);
            } else {
                getBody().applyForceToCenter(0,-playerMotion,true);
            }
            jumpCounter = 0;
        }
    }

    private void checkUserInput(){
        if(!airborne) {
            velX = 0;
            velY = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
                direction = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
                direction = false;
            }
        }

        if(airborne) {
            velY=0;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
                direction = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
                direction = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velY = -0.1f;
            }
                velX *= 0.97;
            }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && jumpCounter <2){ //release it to go for second jump, just pressed
            float force = body.getMass() * 7;
            body.setLinearVelocity(body.getLinearVelocity().x,0.1f); //allows double jump and doesn't clash with body hitting floor
            body.applyLinearImpulse(new Vector2(0,force), body.getPosition(), true); //apply force to y direction in vector2
            jumpCounter++;
        }

        if(body.getLinearVelocity().y == 0){ //if body hits the floor and has no y velocity, currently triggers when hitting block above
            airborne = false;
            jumpCounter = 0;
        }

        body.setLinearVelocity(velX * speed,body.getLinearVelocity().y+velY*speed);
    }

    private void maxSpeed(){
        if (body.getLinearVelocity().y < -10){
            body.setLinearVelocity(body.getLinearVelocity().x,-10);
        }
    }

    private void checkPlayerOutBounds(){
        if (body.getPosition().y < -8){
             body.setTransform(1,5,0);
        }
    }
}
