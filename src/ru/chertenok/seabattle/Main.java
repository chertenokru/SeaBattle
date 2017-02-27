package ru.chertenok.seabattle;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by 13th on 09.02.2017.
 */
public class Main {
    private Field fieldAuto;
    private Field fieldManual;
    private PlayerBase playerManual;
    private PlayerBase playerAuto;
    // кол-во и размеры кораблей
    private int[] shipConf = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private int maxX = 10;
    private int maxY = 10;

    private final char CHAR_DRAW_EMPTY = 9617;
    private final char CHAR_DRAW_EMPTY_SHOOT = 9728;
    private final char CHAR_DRAW_SHIP = 9744;
    private final char CHAR_DRAW_SHIP_SHOOT = 9746;


    public static void main(String[] args) {
        Main m = new Main();
        m.go();
    }


    private void go() {
        //создаём объекты
        initGame();
        //   drawFields();
        // игровой цикл
        do {
            // ходы игрока пока не промахнется
            playerTurn(playerManual);

            // если игрок не выиграл
            if (!fieldAuto.isGameOver()) {
                // ходы компьютера пока не промахнется
                playerTurn(playerAuto);
            }
        } while (!fieldAuto.isGameOver() && !fieldManual.isGameOver());

        if (fieldAuto.isGameOver()) {
            showWinner(playerManual);
        } else {
            showWinner(playerAuto);
        }

    }

    private void showWinner(PlayerBase player) {
        System.out.println();
        // если корабли проигравшей стороны скрыты, то показываем их
        if (!player.getField().isShowShip()) {
            player.getField().setShowShip(true);
            drawFields();
        }
        System.out.println();
        System.out.println("Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура!");
        System.out.println(player.getName() + ", Вы потопили все корабли и победили ! \n Поздравляю !");
    }

    private void playerTurn(PlayerBase player) {
        Point firePoint;
        Field field;
        int result;
        String stringResult = "";
        field = player.getField();
        do {
            if (!field.isShowShip()) drawFields();
            firePoint = player.getShootCoordinate();

            result = field.fire(firePoint.x, firePoint.y);
            switch (result) {
                case Field.EMPTY:
                    stringResult = "Мимо!";
                    break;
                case Field.SHIP_SHOOT:
                    stringResult = "Ранен !";
                    break;
                case Field.SHIP_FIRED:
                    stringResult = "Убит !";
                    break;
            }
            player.sendFireResult(result, firePoint);
            System.out.printf("%s сходил %d,%d  - %s \n", player.getName(), firePoint.x + 1, firePoint.y + 1, stringResult);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                System.out.println(e.fillInStackTrace());
            }

        } while ((result == Field.SHIP_SHOOT || result == Field.SHIP_FIRED) && !field.isGameOver());
    }

    private void initGame() {
        fieldAuto = CreateAndInitField(false, true);
        fieldManual = CreateAndInitField(true, true);
        playerAuto = new PlayerAuto(fieldManual);
        playerManual = new PlayerManual(fieldAuto);
        System.out.println("Игра началась !");
    }

    private Field CreateAndInitField(boolean showShip, boolean showBlocked) {
        Field field = new Field(maxX, maxY, shipConf.length);
        field.setShowShip(showShip);
        field.setShowBlocked(showBlocked);

        for (int i = 0; i < shipConf.length; i++) {
            field.generateNewShipBySize(shipConf[i]);
        }

        return field;
    }

    private void drawFields() {
        char s;
        String s1;
        System.out.println();
        for (int i = 0; i < maxY; i++) {
            if (i == 0) {
                System.out.println("    1 2  3  4  5  6 7  8 9 10" + "              " + "    1 2  3  4  5  6 7  8 9 10");
                System.out.println();
            }

            for (int j = 0; j < maxX; j++) {
                if (j == 0) {
                    s1 = Integer.toString(i + 1);
                    if (s1.length() == 1) s1 = " " + s1;
                    System.out.print(s1 + " ");
                }

                s = getDrawChar(fieldAuto.getCell(j, i), fieldAuto.isShowShip(), fieldAuto.isShowBlocked());
                System.out.print(" " + s);
            }
            System.out.print("              ");
            for (int j = 0; j < maxX; j++) {
                if (j == 0) {
                    s1 = Integer.toString(i + 1);
                    if (s1.length() == 1) s1 = " " + s1;
                    System.out.print(s1 + " ");
                }
                s = getDrawChar(fieldManual.getCell(j, i), fieldManual.isShowShip(), fieldManual.isShowBlocked());
                System.out.print(" " + s);
            }

            System.out.println();

        }
    }

    private char getDrawChar(int value, boolean showShip, boolean showBlocked) {
        char s = ' ';
        switch (value) {
            case Field.EMPTY:
                s = 9617;
                break;
            case Field.EMPTY_BLOCK:
                if (showBlocked) {
                    s = 9728;
                } else {
                    s = 2921;
                }
                break;

            case Field.EMPTY_SHOOT:
                s = 9728;
                break;
            case Field.SHIP:
                if (showShip) {
                    s = 9744;
                } else {
                    s = 9617;
                }
                break;
            case Field.SHIP_SHOOT:
                s = 9746;
                break;

        }
        return s;
    }


}