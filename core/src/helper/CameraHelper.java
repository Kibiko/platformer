package helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector3;
import objects.player.Player;

import static helper.Constants.*;

public class CameraHelper extends OrthographicCamera {

    public void cameraUpdate(Player player){ //updates camera if we wish to
        TiledMap tiledMap = new TmxMapLoader().load("new_map.tmx");
        MapProperties mapProperties = tiledMap.getProperties();
        int mapWidth = mapProperties.get("width", Integer.class);
        int mapHeight = mapProperties.get("height", Integer.class);
        int tilePixelWidth = mapProperties.get("tilewidth", Integer.class);
        int tilePixelHeight = mapProperties.get("tileheight", Integer.class);

        int mapPixelWidth = mapWidth * tilePixelWidth;
        int mapPixelHeight = mapHeight * tilePixelHeight;

        Vector3 position = this.position;
        float xPos;
        float yPos;

        xPos = Math.round(player.getBody().getPosition().x * PPM *10)/10f;//more smooth camera movement
        yPos = Math.round(player.getBody().getPosition().y * PPM *10)/10f;
        this.zoom = 0.5f;

        if(yPos < screenResHeight*this.zoom/2) { //sets camera if player reaches the edge
            position.y = screenResHeight*this.zoom/2;
        }
        else if(yPos > (mapPixelHeight/this.zoom - screenResHeight/2f)/2) { // map height is in tiles
            position.y = (mapPixelHeight/this.zoom - screenResHeight/2f)/2;
        }
        else {
            position.y = yPos; //if not set camera to player position
        }
        if(xPos < screenResWidth*this.zoom/2){
            position.x = screenResWidth*this.zoom/2;
        }
        else if(xPos > (mapPixelWidth/this.zoom - screenResWidth/2f)/2){
            position.x = (mapPixelWidth/this.zoom - screenResWidth/2f)/2;
        }
        else {
            position.x = xPos;
        }
        this.position.set(position);
        this.update();
    }
}
