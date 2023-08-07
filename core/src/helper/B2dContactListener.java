package helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platform.game.GameScreen;
import objects.player.Player;

import java.awt.geom.RectangularShape;

public class B2dContactListener implements ContactListener {

    private Player player;

    public B2dContactListener(Player player){
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
//        System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

//        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUp(fb);
//        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUp(fa);
//        }

//        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.groundedCheck(fa,fb);
//        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.groundedCheck(fb,fa);
//        }

        if(fa.getBody().getUserData() == "WATER"){
            player.setSwimming(true);
//            System.out.println("Player is swimming");
        }else if(fb.getBody().getUserData() == "WATER"){
            player.setSwimming(true);
//            System.out.println("Player is swimming");
        }
    }

    private void groundedCheck(Fixture fixture, Fixture otherFixture){
        if(fixture.getBody().getUserData() =="PLATFORM" && otherFixture.getBody().getUserData() == "PLAYER" && otherFixture.getBody().getLinearVelocity().y <= 0){
            player.setGrounded(true);
        } else{
            player.setGrounded(false);
        }
    }

    private void shootUp(Fixture otherFixture){
        System.out.println("Adding Force");
        otherFixture.getBody().applyLinearImpulse(new Vector2(0,
                10-otherFixture.getBody().getLinearVelocity().y),
                otherFixture.getBody().getPosition(), true);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa.getBody().getUserData() == "WATER"){
            player.setSwimming(false);
        }else if(fb.getBody().getUserData() == "WATER"){
            player.setSwimming(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.oneWayDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.oneWayDisable(contact, fb, fa);
        }

        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.upPlatformDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            this.upPlatformDisable(contact, fb, fa);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            if (fa.getBody().getType() == BodyDef.BodyType.StaticBody &&
                    fa.getBody().getUserData() == "PLATFORM"){
                    contact.setEnabled(false);
                }
            if (fb.getBody().getType() == BodyDef.BodyType.StaticBody &&
                    fb.getBody().getUserData() == "PLATFORM"){
                contact.setEnabled(false);
            }
        }
    }


    private void oneWayDisable(Contact contact, Fixture mainFix, Fixture otherFixture) {
        if (mainFix.getBody().getUserData() == "PLATFORM" &&
                otherFixture.getBody().getUserData() == "PLAYER") {
            contact.setEnabled(true);
        }
    }

    private void upPlatformDisable(Contact contact, Fixture mainFixture, Fixture otherFixture){
        if(mainFixture.getBody().getUserData() == "PLATFORM" &&
                otherFixture.getBody().getUserData() == "PLAYER" &&
                    minPlayerHeight(otherFixture) <= maxPlatform(mainFixture)){
            contact.setEnabled(false);
        }

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    private float maxPlatform(Fixture mainFixture){
        float maxPlatHeight;
        Vector2 vec = new Vector2();
        Transform transform = mainFixture.getBody().getTransform();
        PolygonShape shape = (PolygonShape) mainFixture.getShape();
        shape.getVertex(0,vec);
        transform.mul(vec);
        maxPlatHeight = vec.y;
        shape.getVertex(1,vec);
        transform.mul(vec);
        if(vec.y > maxPlatHeight){
            maxPlatHeight = vec.y;
        }
        shape.getVertex(2,vec);
        transform.mul(vec);
        if(vec.y > maxPlatHeight){
            maxPlatHeight = vec.y;
        }
        shape.getVertex(3,vec);
        transform.mul(vec);
        if(vec.y > maxPlatHeight){
            maxPlatHeight = vec.y;
        }
        return maxPlatHeight;
    }

    private float minPlayerHeight(Fixture mainFixture){
        float minPlayerHeight;
        Vector2 vec = new Vector2();
        Transform transform = mainFixture.getBody().getTransform();
        PolygonShape shape = (PolygonShape) mainFixture.getShape();
        shape.getVertex(0,vec);
        transform.mul(vec);
        minPlayerHeight = vec.y;
        shape.getVertex(1,vec);
        transform.mul(vec);
        if(vec.y < minPlayerHeight){
            minPlayerHeight = vec.y;
        }
        shape.getVertex(2,vec);
        transform.mul(vec);
        if(vec.y < minPlayerHeight){
            minPlayerHeight = vec.y;
        }
        shape.getVertex(3,vec);
        transform.mul(vec);
        if(vec.y < minPlayerHeight){
            minPlayerHeight = vec.y;
        }
        return minPlayerHeight;
    }
}
