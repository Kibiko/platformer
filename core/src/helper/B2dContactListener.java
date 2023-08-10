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
    private ContactListenerHelper contactListenerHelper;

    public B2dContactListener(Player player){
        this.player = player;
        this.contactListenerHelper = new ContactListenerHelper();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
//        System.out.println(fa.getBody().getType() + " has hit " + fb.getBody().getType());

        contactListenerHelper.checkSwimming(player, fa, fb);
        contactListenerHelper.checkClimbing(player, fa, fb);
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
        }else if(fb.getBody().getUserData() == "LADDER"){
            player.setContactWithLadder(false);
            player.setClimbing(false);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            contactListenerHelper.oneWayDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            contactListenerHelper.oneWayDisable(contact, fb, fa);
        }

        if (fa.getBody().getType() == BodyDef.BodyType.StaticBody){
            contactListenerHelper.upPlatformDisable(contact, fa, fb);
        } else if (fb.getBody().getType() == BodyDef.BodyType.StaticBody){
            contactListenerHelper.upPlatformDisable(contact, fb, fa);
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
