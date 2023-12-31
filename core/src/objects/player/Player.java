package objects.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.platform.game.GameScreen;
import helper.InputHelper;

import static components.Constants.PPM;

public class Player extends GameEntity{

    protected boolean airborne;
    protected boolean grounded;
    protected boolean climbing;
    protected boolean swimming;
    protected boolean contactWithLadder;
    private int jumpCounter;
    private InputHelper input = new InputHelper();

    public Player(float width, float height, Body body) {
        super(width, height, body);
        this.speed = 4f;
        this.jumpCounter = 0;
        this.health = 3;
    }

    public void update() {
        x = body.getPosition().x * PPM;
        y = body.getPosition().y * PPM;

        checkPlayerOutBounds();
        checkPlayerAirborne();
        checkPlayerWater();
        checkPlayerClimbing();
        checkUserInput();
        maxSpeed();
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    private void checkPlayerAirborne(){
        if(body.getLinearVelocity().y !=0){
            this.airborne = true;
        } else{
            this.airborne = false;
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

    private void checkPlayerClimbing(){
        if(contactWithLadder && (Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S))) {
            this.climbing = true;
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 0.25f));
            jumpCounter = 0;
        }
        if(climbing){
            body.setLinearVelocity(new Vector2(body.getLinearVelocity().x, 0.25f));
        }
    }

    private void checkUserInput(){
        input.groundInputChecker(this);
        input.climbInputChecker(this);
        input.airborneInputChecker(this);
        input.jumpAlgorithm(this);
    }

    private void maxSpeed(){
        if (body.getLinearVelocity().y < -10){
            body.setLinearVelocity(body.getLinearVelocity().x,-10);
        }
    }

    private void checkPlayerOutBounds(){
        if (body.getPosition().y < -8){
            body.setTransform(1,5,0);
            health--;
        }
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getJumpCounter() {
        return jumpCounter;
    }

    public void setJumpCounter(int jumpCounter) {
        this.jumpCounter = jumpCounter;
    }

    public boolean isAirborne() {
        return this.airborne;
    }

    public void setAirborne(boolean airborne) {
        this.airborne = airborne;
    }

    public boolean isGrounded() {
        return this.grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public boolean isClimbing() {
        return this.climbing;
    }

    public void setClimbing(boolean climbing) {
        this.climbing = climbing;
    }

    public boolean isSwimming() {
        return this.swimming;
    }

    public void setSwimming(boolean swimming) {
        this.swimming = swimming;
    }

    public boolean isContactWithLadder() {
        return this.contactWithLadder;
    }

    public void setContactWithLadder(boolean contactWithLadder) {
        this.contactWithLadder = contactWithLadder;
    }
}
