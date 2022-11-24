package entity;

import info.Score;
import info.SnakeInfo;
import main.GamePanel;
import tile.Tile;
import tile.TileManager;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class Snake {
    public int snakeLength = 4;

    public boolean alive = true;

    private String name;
    char direction = 'U';

    Queue<Character> q = new LinkedList<>();

    Tile apple;

    public List<Tile> snake = new ArrayList<>();

    GamePanel gamePanel;
    TileManager tiles;

    Score score;

    final int INIT_HEAD_X;
    final int INIT_HEAD_Y;

    final int INIT_APPLE_X = 7;
    final int INIT_APPLE_Y = 7;
    SnakeInfo snakeInfo;

    public Snake(GamePanel gamePanel, TileManager tiles, Score score, Tile apple, SnakeInfo snakeInfo) {
        this.snakeInfo = snakeInfo;
        this.INIT_HEAD_X = snakeInfo.getINIT_HEAD_X();
        this.INIT_HEAD_Y = snakeInfo.getINIT_HEAD_Y();
        this.name = snakeInfo.getName();
        this.gamePanel = gamePanel;
        this.snake.add(new Tile(
                snakeInfo.getHeadColor(),
                Tile.Type.SNAKE,
                INIT_HEAD_X * gamePanel.UNIT_SIZE,
                INIT_HEAD_Y * gamePanel.UNIT_SIZE,
                gamePanel
        ));
        this.snake.add(new Tile(
                snakeInfo.getHeadColor(),
                Tile.Type.SNAKE,
                INIT_HEAD_X * gamePanel.UNIT_SIZE,
                INIT_HEAD_Y * gamePanel.UNIT_SIZE,
                gamePanel
        ));
        for(int i = 2; i < snakeLength; i++) {
            this.snake.add(new Tile(
                    snakeInfo.getBodyColor(),
                    Tile.Type.SNAKE,
                    INIT_HEAD_X * gamePanel.UNIT_SIZE,
                    INIT_HEAD_Y * gamePanel.UNIT_SIZE,
                    gamePanel
            ));
        }
        this.tiles = tiles;
        this.apple = apple;
        this.score = score;


    }

    public String getName() {
        return name;
    }

    public void update() {
//        System.out.println("Update snake");
        if(!gamePanel.running) {
            return;
        }
        Character c = null;
        if(!q.isEmpty()) {
            c = q.remove();
        }
        if(c != null) {
            switch (c) {
                case 'U':
                    if(direction != 'D') direction = 'U';
                    break;
                case 'D':
                    if(direction != 'U') direction = 'D';
                    break;
                case 'L':
                    if(direction != 'R') direction = 'L';
                    break;
                case 'R':
                    if(direction != 'L') direction = 'R';
                    break;

            }
        }
        if(direction == 'U') {
            snake.get(0).y -= gamePanel.UNIT_SIZE;
        } else
        if(direction == 'D') {
            snake.get(0).y += gamePanel.UNIT_SIZE;
        } else
        if(direction == 'L') {
            snake.get(0).x -= gamePanel.UNIT_SIZE;
        } else
        if(direction == 'R') {
            snake.get(0).x += gamePanel.UNIT_SIZE;
        }

        if(checkCollision()) {
            gamePanel.running = false;
            alive = false;
        }

        Tile tail = snake.get(snakeLength - 1);

        for(int i = snakeLength - 1; i > 0; i--) {
            snake.get(i).x = snake.get(i - 1).x;
            snake.get(i).y = snake.get(i - 1).y;
        }

        if(checkApple()) {
//            snake.add(new Tile(Color.red, Tile.Type.SNAKE, snake.get(snakeLength - 1).x, snake.get(snakeLength - 1).y, gamePanel));
            snake.add(new Tile(snakeInfo.getBodyColor(), Tile.Type.SNAKE, tail.x, tail.y, gamePanel));
            score.addScore(1);
            snakeLength++;
            while (true) {
                if(newApple()) break;
            }
        }
    }

    private boolean newApple() {
        Random r = new Random();
        int x = r.nextInt(gamePanel.SCREEN_MAX_COL - 2) * gamePanel.UNIT_SIZE + gamePanel.UNIT_SIZE;
        int y = r.nextInt(gamePanel.SCREEN_MAX_ROW - 2) * gamePanel.UNIT_SIZE + gamePanel.UNIT_SIZE;
        for(Tile t : snake) {
            if(t.x == x && t.y == y) {
                return false;
            }
        }
        apple.x = x;
        apple.y = y;
        return true;
    }

    private boolean checkCollision() {
        for(int i = 0; i < gamePanel.SCREEN_MAX_ROW; i++) {
            for(int j = 0; j < gamePanel.SCREEN_MAX_COL; j++) {
                Tile t = tiles.tiles.get(i).get(j);
                if(t.x == snake.get(0).x && t.y == snake.get(0).y && t.type == Tile.Type.WALL) {
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(Graphics g) {
        for(int i = 0; i < snakeLength; i++) {
            snake.get(i).draw(g);
        }

        apple.draw(g);
        score.draw(g);
    }

    private boolean checkApple() {
        if(snake.get(0).x == apple.x && snake.get(0).y == apple.y) {
            return true;
        }
        return false;
    }

    public void actionPerformed() {

    }

    public MyKeyAdapter1 getKeyListener1() {
        return new MyKeyAdapter1();
    }

    public class MyKeyAdapter1 extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    q.add('L');
                    break;
                case KeyEvent.VK_RIGHT:
                    q.add('R');
                    break;
                case KeyEvent.VK_UP:
                    q.add('U');
                    break;
                case KeyEvent.VK_DOWN:
                    q.add('D');
                    break;
            }
        }
    }

    public MyKeyAdapter2 getKeyListener2() {
        return new MyKeyAdapter2();
    }

    public class MyKeyAdapter2 extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_A:
                    q.add('L');
                    break;
                case KeyEvent.VK_D:
                    q.add('R');
                    break;
                case KeyEvent.VK_W:
                    q.add('U');
                    break;
                case KeyEvent.VK_S:
                    q.add('D');
                    break;
            }
        }
    }
}
