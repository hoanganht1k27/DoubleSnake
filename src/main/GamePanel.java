package main;

import entity.Apple;
import entity.Snake;
import info.Score;
import info.SnakeInfo;
import tile.Tile;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel implements Runnable, ActionListener {

    public final int SCREEN_WIDTH = 800;
    public final int SCREEN_HEIGHT = 800;
    public final int UNIT_SIZE = 25;
    public final int SCREEN_MAX_COL = SCREEN_WIDTH / UNIT_SIZE;
    public final int SCREEN_MAX_ROW = SCREEN_HEIGHT / UNIT_SIZE;

    public final Color[] COLOR_TYPE = {Color.red, Color.blue, Color.yellow, Color.green, Color.pink, Color.black};
    final int FPS = 10;

    TileManager tiles = new TileManager(this);
    Score score = new Score(this, 0);

    Tile apple = new Tile(Color.yellow, Tile.Type.APPLE, 7 * UNIT_SIZE, 7 * UNIT_SIZE, this);

    SnakeInfo snakeInfo1 = new SnakeInfo("Anh", 5, 20, new Color(203, 222, 126), new Color(236, 212, 9));
    SnakeInfo snakeInfo2 = new SnakeInfo("Son", 25, 20, new Color(249, 92, 77), new Color(225, 25, 6));
    Snake snake = new Snake(this, tiles, score, apple, snakeInfo1);
    Snake snake2 = new Snake(this, tiles, score, apple, snakeInfo2);

    Thread gameThread;

    public boolean running = false;

    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true); // rendering better
        this.setFocusable(true);
        this.addKeyListener(snake.getKeyListener1());
        this.addKeyListener(snake2.getKeyListener2());
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        running = true;

        while(gameThread != null) {
//            System.out.println("Running");
//            1 UPDATE: update information such as character positions
            update();
//            2 DRAW: draw the screen with the updated information
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        snake.update();
        snake2.update();
    }

    private boolean compareTile(Tile a, Tile b) {
        if(a.x == b.x && a.y == b.y) return true;
        return false;
    }

    private void checkWinner(Graphics g) {
        g.setColor(Color.red);
        if(!snake.alive && !snake2.alive) {
            running = false;
            score.write(g, "Match draw");
            return;
        }
        if(!snake.alive) {
            running = false;
            score.write(g, snake2.getName() + " wins");
            return;
        }
        if(!snake2.alive) {
            running = false;
            score.write(g, snake.getName() + " wins");
            return;
        }
        if(compareTile(snake.snake.get(0), snake2.snake.get(0))) {
            running = false;
            score.write(g, "Match draw");
            return;
        }
        for(int i = 0; i < snake.snakeLength; i++) {
            if(compareTile(snake.snake.get(i), snake2.snake.get(0))) {
                running = false;
                score.write(g, snake.getName() + " wins");
                return;
            }
        }

        for(int i = 0; i < snake2.snakeLength; i++) {
            if (compareTile(snake2.snake.get(i), snake.snake.get(0))) {
                running = false;
                score.write(g, snake2.getName() + " wins");
                return;
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        tiles.draw(g);
        snake.draw(g);
        snake2.draw(g);

        for(int i = 0; i < SCREEN_MAX_ROW; i++) {
            g.setColor(new Color(143, 143, 143));
            g.drawLine(UNIT_SIZE, i * UNIT_SIZE, SCREEN_WIDTH - UNIT_SIZE, i * UNIT_SIZE);
        }

        for(int i = 0; i < SCREEN_MAX_COL; i++) {
            g.setColor(new Color(143, 143, 143));
            g.drawLine(i * UNIT_SIZE, UNIT_SIZE, i * UNIT_SIZE, SCREEN_HEIGHT - UNIT_SIZE);
        }

        checkWinner(g);

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snake.actionPerformed();
    }
}
