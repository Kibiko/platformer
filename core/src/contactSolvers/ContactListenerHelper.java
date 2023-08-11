package contactSolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;
import objects.player.Player;

public class ContactListenerHelper {

    public ContactListenerHelper(){

    }

    public float maxPlatform(Fixture mainFixture){
        float maxPlatHeight;
        Vector2 vec = new Vector2();
        Transform transform = mainFixture.getBody().getTransform();
        PolygonShape shape = (PolygonShape) mainFixture.getShape();
        shape.getVertex(0,vec);
        transform.mul(vec);
        maxPlatHeight = vec.y;
        for (int i = 1; i< shape.getVertexCount(); i++){
            shape.getVertex(1,vec);
            transform.mul(vec);
            if(vec.y > maxPlatHeight){
                maxPlatHeight = vec.y;
            }
        }
        return maxPlatHeight;
    }

    public float minPlayerHeight(Fixture mainFixture){
        float minPlayerHeight;
        Vector2 vec = new Vector2();
        Transform transform = mainFixture.getBody().getTransform();
        PolygonShape shape = (PolygonShape) mainFixture.getShape();
        shape.getVertex(0,vec);
        transform.mul(vec);
        minPlayerHeight = vec.y;
        for (int i = 1; i< shape.getVertexCount(); i++){
            shape.getVertex(1,vec);
            transform.mul(vec);
            if(vec.y > minPlayerHeight){
                minPlayerHeight = vec.y;
            }
        }
        return minPlayerHeight;
    }

    public void oneWayDisable(Contact contact, Fixture mainFix, Fixture otherFixture) {
        if (mainFix.getBody().getUserData() == "PLATFORM" &&
                otherFixture.getBody().getUserData() == "PLAYER") {
            contact.setEnabled(true);
        }
    }

    public void upPlatformDisable(Contact contact, Fixture mainFixture, Fixture otherFixture){
        if(mainFixture.getBody().getUserData() == "PLATFORM" &&
                otherFixture.getBody().getUserData() == "PLAYER" &&
                minPlayerHeight(otherFixture) <= maxPlatform(mainFixture)){
            contact.setEnabled(false);
        }

    }

    public void groundedCheck(Player player, Fixture fixture, Fixture otherFixture){
        if(fixture.getBody().getUserData() =="PLATFORM" && otherFixture.getBody().getUserData() == "PLAYER" && otherFixture.getBody().getLinearVelocity().y <= 0){
            player.setGrounded(true);
        } else{
            player.setGrounded(false);
        }
    }

    public void shootUp(Fixture otherFixture){
        System.out.println("Adding Force");
        otherFixture.getBody().applyLinearImpulse(new Vector2(0,
                        10-otherFixture.getBody().getLinearVelocity().y),
                otherFixture.getBody().getPosition(), true);
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
