import main.GamePanel;

import javax.swing.*;

public class GameFrame extends JFrame{
    GamePanel gamePanel;
    GameFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setTitle("Snake 2");
        gamePanel = new GamePanel();
        this.add(gamePanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        gamePanel.startGameThread();
    }
}
