package contactSolvers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.*;
import objects.player.Player;

public class B2dContactListener implements ContactListener {

    private Player player;
    private MovementStatusHelper movementStatusHelper;
    private OneWayPlatformHelper oneWayPlatformHelper;

    public B2dContactListener(Player player){
        this.player = player;
        this.movementStatusHelper = new MovementStatusHelper();
        this.oneWayPlatformHelper = new OneWayPlatformHelper();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
//        System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

        movementStatusHelper.checkSwimming(player, fa, fb);
        movementStatusHelper.checkClimbing(player, fa, fb);
        movementStatusHelper.checkGrounded(player, fa, fb);
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

        if(fa.getBody().getUserData() == "LADDER"){
            player.setContactWithLadder(false);
            player.setClimbing(false);
            if(oneWayPlatformHelper.minPlayerHeight(fb) > oneWayPlatformHelper.maxPlatform(fa)){ //prevents player from shooting up from ladder at the top
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x,player.getBody().getLinearVelocity().y/2f);
            } else {
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y);
            }
        }else if(fb.getBody().getUserData() == "LADDER"){
            player.setContactWithLadder(false);
            player.setClimbing(false);
            if(oneWayPlatformHelper.minPlayerHeight(fa) > oneWayPlatformHelper.maxPlatform(fb)){
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x,player.getBody().getLinearVelocity().y/2f);
            } else {
                player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, player.getBody().getLinearVelocity().y);
            }

        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            oneWayPlatformHelper.oneWayDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            oneWayPlatformHelper.oneWayDisable(contact, fb, fa);
        }

        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            oneWayPlatformHelper.upPlatformDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            oneWayPlatformHelper.upPlatformDisable(contact, fb, fa);
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

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if (fa.getBody().getType() == BodyDef.BodyType.StaticBody &&
                    fa.getBody().getUserData() == "LADDER"){
                contact.setEnabled(false);
            }
            if (fb.getBody().getType() == BodyDef.BodyType.StaticBody &&
                    fb.getBody().getUserData() == "LADDER"){
                contact.setEnabled(false);
            }
        }
    }


    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
