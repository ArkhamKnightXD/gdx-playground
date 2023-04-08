package knight.arkham.helpers;

import com.badlogic.gdx.math.Vector2;

import java.io.*;
import java.util.Scanner;

public class GameDataHelper {

    public static void savePlayerPosition(String playerData){

        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter("game-data.txt"));
            writer.write(playerData);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scanner loadFileData() {

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("game-data.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return scanner;
    }

    public static Vector2 loadPlayerPosition(){

        Scanner playerData = loadFileData();

        Vector2 savedPosition = new Vector2();

        while (playerData.hasNextLine()) {

            String position = playerData.nextLine();

            if (position.contains("PositionX:"))
                savedPosition.x = Float.parseFloat(position.substring(11,17));

            else
                savedPosition.y = Float.parseFloat(position.substring(11,17));
        }

        playerData.close();

        return savedPosition;
    }
}
