package info;

import java.awt.*;

public class SnakeInfo {
    private String name;
    private int INIT_HEAD_X;
    private int INIT_HEAD_Y;
    private Color bodyColor;
    private Color headColor;

    public SnakeInfo(String name, int INIT_HEAD_X, int INIT_HEAD_Y, Color bodyColor, Color headColor) {
        this.name = name;
        this.INIT_HEAD_X = INIT_HEAD_X;
        this.INIT_HEAD_Y = INIT_HEAD_Y;
        this.bodyColor = bodyColor;
        this.headColor = headColor;
    }

    public String getName() {
        return name;
    }

    public int getINIT_HEAD_X() {
        return INIT_HEAD_X;
    }

    public int getINIT_HEAD_Y() {
        return INIT_HEAD_Y;
    }

    public Color getBodyColor() {
        return bodyColor;
    }

    public Color getHeadColor() {
        return headColor;
    }
}
