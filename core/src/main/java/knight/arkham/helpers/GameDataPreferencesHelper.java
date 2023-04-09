package knight.arkham.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.Vector2;

// Save and load system using libgdx preferences
public class GameDataPreferencesHelper {

    public static void saveGameData(String filename, GameData gameData){

// Crea un archivo xml si este no existe, con el nombre que indiquemos en mi path user/.pref/filename.xml
        Preferences preferences = Gdx.app.getPreferences(filename);

        preferences.putString("screenName", gameData.screenName);

        preferences.putFloat("positionX", gameData.position.x);
        preferences.putFloat("positionY", gameData.position.y);

//        Save data to file
        preferences.flush();
    }

    public static GameData loadGameData(String filename){

        Preferences preferences = Gdx.app.getPreferences(filename);

        float positionX = preferences.getFloat("positionX");
        float positionY = preferences.getFloat("positionY");

        String screenName = preferences.getString("screenName");

        return new GameData(screenName, new Vector2(positionX, positionY));
    }
}
