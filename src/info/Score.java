package info;

import main.GamePanel;

import java.awt.*;

public class Score{
    GamePanel gamePanel;

    int score = 0;
    int highScore = 0;
    public Score(GamePanel gamePanel, int score) {
        this.gamePanel = gamePanel;
        this.score = score;
        this.highScore = score;
    }

    public void addScore(int x) {
        this.score += x;
    }

    public void write(Graphics g, String s) {
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        int w = g.getFontMetrics().stringWidth(s);
        g.drawString(s, (gamePanel.SCREEN_WIDTH - w) / 2, gamePanel.UNIT_SIZE);
    }

    public void draw(Graphics g) {
        g.setFont(new Font("Ink Free", Font.BOLD, 20));
        int w = g.getFontMetrics().stringWidth("Eaten apples: " + score);
        g.drawString("Eaten apples: " + score, gamePanel.UNIT_SIZE, gamePanel.UNIT_SIZE);
    }
}
