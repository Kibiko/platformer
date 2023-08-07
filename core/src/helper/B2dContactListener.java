package helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.platform.game.GameScreen;
import objects.player.Player;

public class B2dContactListener implements ContactListener {

    private Player player;

    public B2dContactListener(Player player){
        this.player = player;
    }

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Contact");
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
//        System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

//        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUp(fb);
//        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
//            this.shootUp(fa);
//        }

        if(fa.getBody().getUserData() == "WATER"){
            player.setSwimming(true);
//            System.out.println("Player is swimming");
        }else if(fb.getBody().getUserData() == "WATER"){
            player.setSwimming(true);
//            System.out.println("Player is swimming");
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
//        System.out.println("Contact");
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

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
