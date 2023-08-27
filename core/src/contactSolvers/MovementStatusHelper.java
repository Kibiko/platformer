package contactSolvers;

import com.badlogic.gdx.physics.box2d.Fixture;
import objects.player.Player;

public class MovementStatusHelper {

    public MovementStatusHelper(){

    }

    public void checkGrounded(Player player, Fixture fixture, Fixture otherFixture){
        if(fixture.getBody().getUserData() =="PLATFORM" && otherFixture.getBody().getUserData() == "PLAYER" && otherFixture.getBody().getLinearVelocity().y <= 0){
            player.setGrounded(true);
            player.setAirborne(false);
        } else {
            player.setGrounded(true);
        }
    }

    public void checkSwimming(Player player, Fixture mainFixture, Fixture otherFixture) {
        if (mainFixture.getBody().getUserData() == "WATER") {
            player.setSwimming(true);
//            System.out.println("Player is swimming");
        } else if (otherFixture.getBody().getUserData() == "WATER") {
            player.setSwimming(true);
//            System.out.println("Player is swimming");
        }
    }

    public void checkClimbing(Player player, Fixture mainFixture, Fixture otherFixture) {
        if (mainFixture.getBody().getUserData() == "LADDER") {
            player.setContactWithLadder(true);
        } else if (otherFixture.getBody().getUserData() == "LADDER"){
            player.setContactWithLadder(true);
        }
    }

}
