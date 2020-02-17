package com.BusyginSergey.GameSnake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";

    private List<GameObject> snakeParts = new ArrayList<>();
    public Snake (int x, int y) {
        snakeParts.add(new GameObject(x, y));
        snakeParts.add(new GameObject(x + 1, y));
        snakeParts.add(new GameObject(x + 2, y));
    }

    public void draw(Game game) {
        if (isAlive == true) {
            for (int i = 0; i < snakeParts.size(); i++){
                if (i == 0){
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
                }
                else {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
                }
            }
        }
        else {
            for (int i = 0; i < snakeParts.size(); i++){
                if (i == 0){
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
                }
                else {
                    game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
                }
            }
        }

    }

    public boolean isAlive = true;

    private Direction direction = Direction.LEFT;

    public void setDirection(Direction direction){
        if (direction == Direction.UP && this.direction == Direction.DOWN) return;
        else if (direction == Direction.DOWN && this.direction == Direction.UP) return;
        else if (direction == Direction.RIGHT && this.direction == Direction.LEFT) return;
        else if (direction == Direction.LEFT && this.direction == Direction.RIGHT) return;

        if (this.direction ==  Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        else if (this.direction ==  Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x) return;
        else if (this.direction ==  Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y) return;
        else if (this.direction ==  Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) return;
        else this.direction = direction;
    }

    public void move(Apple apple) {
        GameObject head = createNewHead(); // создание новой головы
        if (apple.x == head.x && apple.y == head.y) {  // проверка новая голова там где яблоко?
            if (head.x >= 0 && head.x <SnakeGame.WIDTH && head.y >= 0 && head.y < SnakeGame.HEIGHT) { // проверка новая голова в игровом поле?
                apple.isAlive = false;  // яблоко съедено
                if (checkCollision(head)) { // проверка на поедания самого себя
                    this.isAlive = false;
                    return;
                }
                snakeParts.add(0, head); // добавление новой головы в начало списка
            }
            else this.isAlive = false; // конец игры если новая голова не в игровом поле
        }
        else { // если новая голова не там где яблоко то
            if (head.x >= 0 && head.x < SnakeGame.WIDTH && head.y >= 0 && head.y < SnakeGame.HEIGHT) {  // проверка новая голова в игровом поле?
                if (checkCollision(head)) { // проверка на поедания самого себя
                    this.isAlive = false;
                    return;
                }
                snakeParts.add(0, head); // добавление новой головы в начало списка
                removeTail(); // удаление последнего хвоста
            } else this.isAlive = false; // конец игры если новая голова не в игровом поле
        }
    }

    public GameObject createNewHead() {
        GameObject newHead = null;
        if (direction == Direction.LEFT) {
            newHead = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        }
        if (direction == Direction.UP) {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        }
        if (direction == Direction.DOWN) {
            newHead = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        if (direction == Direction.RIGHT) {
            newHead = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        }
        return newHead;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision(GameObject object){
        for (int i = 0; i < snakeParts.size(); i++) {
            if (object.x == snakeParts.get(i).x && object.y == snakeParts.get(i).y) {
                return true;
                }
            }
        return false;
        }

    public int getLength() {
        int length = snakeParts.size();
        return length;
    }

}
