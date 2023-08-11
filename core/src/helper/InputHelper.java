package helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import objects.player.Player;

public class InputHelper {

    float velX = 0;
    float velY = 0;

    public InputHelper(){

    }

    public void groundInputChecker(Player player){
        if(!player.isAirborne() && !player.isClimbing()) {
            velX = 0;
            velY = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
                player.setDirection(true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
                player.setDirection(false);
            }
        }
        player.getBody().setLinearVelocity(velX * player.getSpeed(),player.getBody().getLinearVelocity().y+velY*player.getSpeed());
    }

    public void climbInputChecker(Player player){
        if(player.isClimbing()) {
            velX = 0;
            velY = 0;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
                player.setDirection(true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
                player.setDirection(false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                velY = 0.4f;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velY = -0.4f;
            }
        }
        player.getBody().setLinearVelocity(velX * player.getSpeed(),player.getBody().getLinearVelocity().y+velY*player.getSpeed());
    }

    public void airborneInputChecker(Player player){
        if(player.isAirborne() && !player.isClimbing()) {
            velY=0;
            if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                velX = 1;
                player.setDirection(true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                velX = -1;
                player.setDirection(false);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                velY = -0.1f;
            }
            velX *= 0.97;
        }

        if(player.getBody().getLinearVelocity().y == 0){ //if body hits the floor and has no y velocity, currently triggers when hitting block above
            player.setAirborne(false);
            player.setJumpCounter(0);
        }
        player.getBody().setLinearVelocity(velX * player.getSpeed(),player.getBody().getLinearVelocity().y+velY*player.getSpeed());
    }

    public void jumpAlgorithm(Player player){
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && player.getJumpCounter() <2 && !player.isClimbing()){ //release it to go for second jump, just pressed
            if(player.isAirborne()){
                player.setJumpCounter(player.getJumpCounter() + 1);
            }
            float force = player.getBody().getMass() * 7;
            player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x,0.1f); //allows double jump and doesn't clash with body hitting floor
            player.getBody().applyLinearImpulse(new Vector2(0,force), player.getBody().getPosition(), true); //apply force to y direction in vector2
            player.setJumpCounter(player.getJumpCounter() + 1);
        }

        //Variable jump ?


//        if((Gdx.input.isKeyPressed(Input.Keys.SPACE) && (jumpTime>=0 || !airborne) && jumpCounter<2)){
//            if (jumpTime == 0) {
//                jumpTime = 20;
//                body.setLinearVelocity(body.getLinearVelocity().x, jumpTime+0.1f);
//                jumpCounter++;
//                System.out.println(jumpCounter);
//            } else if (jumpTime > 0f) {
//                jumpTime--;
//                body.setLinearVelocity(body.getLinearVelocity().x, jumpTime / 2f);
//            } else {
//                body.setLinearVelocity(body.getLinearVelocity().x, 0.1f);
//            }
//        } else if(airborne){
//            if(jumpTime>0) {
//                float yVel = body.getLinearVelocity().y*=0.5f;
//                body.setLinearVelocity(body.getLinearVelocity().x, yVel);
//                this.jumpTime = 0;
//            }
//        }
    }
}
