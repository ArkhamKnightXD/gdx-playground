package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;

import java.io.*;
import java.util.Scanner;

public class GameDataHelper {

    public static void savePlayerPosition(String playerPosition, String filenameToSave){

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("data/" + filenameToSave + ".txt"));
            writer.write(playerPosition);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner loadFileData(String filenameToLoad) {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("data/" + filenameToLoad + ".txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return scanner;
    }

    public static Vector2 loadPlayerPosition(String filePath){

        Scanner playerData = loadFileData(filePath);

        Vector2 savedPosition = new Vector2();

        while (playerData.hasNextLine()) {

            String position = playerData.nextLine();

            int lastCharacter = position.length();

//  Todo evitar tener hard coded el valor al que asigno el 11
            if (position.contains("PositionX:"))
                savedPosition.x = Float.parseFloat(position.substring(11, lastCharacter));

            else
                savedPosition.y = Float.parseFloat(position.substring(11, lastCharacter));
        }

        playerData.close();

        return savedPosition;
    }
}
