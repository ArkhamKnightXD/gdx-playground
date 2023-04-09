package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;
import java.io.*;
import java.util.Scanner;

// Save and load system using my own implementation.
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

            int valueStartingIndex = getValueStartingIndex(position);

            int lastCharacterIndex = position.length();

            float saveDataValue = Float.parseFloat(position.substring(valueStartingIndex, lastCharacterIndex));

            if (position.contains("PositionX:"))
                savedPosition.x = saveDataValue;

            else
                savedPosition.y = saveDataValue;
        }

        playerData.close();

        return savedPosition;
    }

    private static char[] convertStringToCharArray(String content) {

        char[] items = new char[content.length()];

        for (int i = 0; i < content.length(); i++)
            items[i] = content.charAt(i);

        return items;
    }

    private static int getValueStartingIndex(String content) {

        char[] items = convertStringToCharArray(content);

        for (int index = 0; index < items.length; index++){

            if (items[index] == ' ')
                return index + 1;
        }

        return 0;
    }
}
