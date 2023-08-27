package contactSolvers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Transform;

public class OneWayPlatformHelper{

    public OneWayPlatformHelper(){

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

    public void upPlatformDisable(Contact contact, Fixture mainFixture, Fixture otherFixture) {
        if (mainFixture.getBody().getUserData() == "PLATFORM" &&
                otherFixture.getBody().getUserData() == "PLAYER" &&
                minPlayerHeight(otherFixture) <= maxPlatform(mainFixture)) {
            contact.setEnabled(false);
        }
    }
}
