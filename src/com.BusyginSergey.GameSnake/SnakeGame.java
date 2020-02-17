package com.BusyginSergey.GameSnake;

import com.javarush.engine.cell.*;


public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    public void initialize(){
        setScreenSize(15,15);
        createGame();
    }
    private void createGame(){
        snake = new Snake(WIDTH / 2,HEIGHT / 2);
        createNewApple();
        isGameStopped = false;
        score = 0;
        setScore(score);
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);


    }
    private void drawScene(){
        for (int x = 0; x < WIDTH; x++){
            for (int y = 0; y < HEIGHT; y++){
                setCellValueEx(x, y, Color.CORAL, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    private Snake snake;

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (apple.isAlive == false) {
            createNewApple();
            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (snake.isAlive == false) gameOver();
        if (snake.getLength() > GOAL) win();
        drawScene();

    }

    private int turnDelay;
    private boolean isGameStopped;

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) snake.setDirection(Direction.LEFT);
        else if (key == Key.RIGHT) snake.setDirection(Direction.RIGHT);
        else if (key == Key.DOWN) snake.setDirection(Direction.DOWN);
        else if (key == Key.UP) snake.setDirection(Direction.UP);

        if (key == Key.SPACE && isGameStopped == true) createGame();
    }
    private Apple apple;

    private void createNewApple() {
        do {
            apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));

    }



    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK,"GAME OVER", Color.WHITE, 80);
    }
    private static final int GOAL = 28;

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.BLACK, "CONGRATULATIONS", Color.WHITE, 60);
    }

    private int score;

}
