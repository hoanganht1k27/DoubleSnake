package tile;

import main.GamePanel;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TileManager {
    GamePanel gamePanel;
    public List<List<Tile>> tiles = new ArrayList<List<Tile>>();
    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        loadMap("C:\\Users\\hoang\\AnhNguyen\\20221\\OOP\\Snake2\\resource\\map\\map01.txt");
    }

    private void loadMap(String filePath) {
        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);

            int col = 0;
            int row = 0;

            while(col < gamePanel.SCREEN_MAX_COL && row < gamePanel.SCREEN_MAX_ROW) {
                String line = br.readLine();
                String numbers[] = line.split(" ");
                tiles.add(new ArrayList<Tile>());

                while(col < gamePanel.SCREEN_MAX_COL) {
                    Random t = new Random();
                    if(numbers[col].equals("0"))
                    tiles.get(row).add(new Tile(
                                Color.black,
                                Tile.Type.BACKGROUND,
                                col * gamePanel.UNIT_SIZE, row * gamePanel.UNIT_SIZE, gamePanel
                            ));
                    if(numbers[col].equals("1"))
                        tiles.get(row).add(new Tile(
                                Color.red,
                                Tile.Type.SNAKE,
                                col * gamePanel.UNIT_SIZE, row * gamePanel.UNIT_SIZE, gamePanel
                        ));

                    if(numbers[col].equals("2"))
                        tiles.get(row).add(new Tile(
                                Color.yellow,
                                Tile.Type.APPLE,
                                col * gamePanel.UNIT_SIZE, row * gamePanel.UNIT_SIZE, gamePanel
                        ));

                    if(numbers[col].equals("3"))
                        tiles.get(row).add(new Tile(
                                Color.blue,
                                Tile.Type.WALL,
                                col * gamePanel.UNIT_SIZE, row * gamePanel.UNIT_SIZE, gamePanel
                        ));
                    col++;
                }
                if(col == gamePanel.SCREEN_MAX_COL) {
                    col = 0;
                    row++;
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        for(int i = 0; i < gamePanel.SCREEN_MAX_ROW; i++) {
            for(int j = 0; j < gamePanel.SCREEN_MAX_COL; j++) {
                tiles.get(i).get(j).draw(g);
            }
        }
    }
}
