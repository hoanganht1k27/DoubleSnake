package tile;

import main.GamePanel;

import java.awt.*;

public class Tile {
    public final Color color;
    public final Type type;

    public int x;
    public int y;

    GamePanel gamePanel;
    public Tile(Color c, Type t, int x, int y, GamePanel gamePanel) {

        this.color = c;
        this.type = t;
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(x, y, gamePanel.UNIT_SIZE, gamePanel.UNIT_SIZE);
    }

    public enum Type {
        WALL,
        APPLE,
        SNAKE,
        BACKGROUND,
        NONE
    }
}
