package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerBase;

import java.awt.*;
import java.util.Scanner;

/**
 * Консольный вывод / ввод
 */
public class ConsoleIView implements IViewSeaBattle {

    private static Scanner scanner = new Scanner(System.in);
    //  символы для отрисовки
    private final char CHAR_DRAW_EMPTY = 9617;
    private final char CHAR_DRAW_EMPTY_SHOOT = 9728;
    private final char CHAR_DRAW_SHIP = 9744;
    private final char CHAR_DRAW_SHIP_SHOOT = 9746;

//    private final char CHAR_DRAW_EMPTY = '_';
//    private final char CHAR_DRAW_EMPTY_SHOOT = '.';
//    private final char CHAR_DRAW_SHIP = '@';
//    private final char CHAR_DRAW_SHIP_SHOOT = '#';

    private int maxX;
    private int maxY;

    /**
     * Инициализация игры
     */
    @Override
    public void startGame() {
        System.out.println("Игра началась !");
    }

    /**
     * запрос имени игрока
     */
    @Override
    public String getPlayerName() {
        System.out.println();
        System.out.println("Введите Ваше имя: ");
        return scanner.next();
    }

    /**
     * отрисовка полей
     *
     * @param field1 поле 1
     * @param field2 поле 2
     */
    @Override
    public void drawFields(Field field1, Field field2) {
        System.out.println();
        for (int i = 0; i < maxY; i++) {
            if (i == 0) {
                System.out.println("    1 2  3  4  5  6 7  8 9 10" + "              " + "    1 2  3  4  5  6 7  8 9 10");
                System.out.println();
            }
            drawLineField(field1, i);
            System.out.print("              ");
            drawLineField(field2, i);
            System.out.println();
        }
    }


    /**
     * отрисовка строки, вынес для лучшей читаемости
     */
    private void drawLineField(Field field, int i) {
        String s1;
        char s;
        for (int j = 0; j < maxX; j++) {
            if (j == 0) {
                s1 = Integer.toString(i + 1);
                if (s1.length() == 1) s1 = " " + s1;
                System.out.print(s1 + " ");
            }

            s = getDrawChar(field.getCell(j, i), field.isShowShip(), field.isShowBlocked());
            System.out.print(" " + s);
        }
    }

    private char getDrawChar(int value, boolean showShip, boolean showBlocked) {
        char s = ' ';
        switch (value) {
            case Field.EMPTY:
                s = CHAR_DRAW_EMPTY;
                break;
            case Field.EMPTY_BLOCK:
                if (showBlocked) {
                    s = CHAR_DRAW_EMPTY_SHOOT;
                } else {
                    s = CHAR_DRAW_EMPTY;
                }
                break;

            case Field.EMPTY_SHOOT:
                s = CHAR_DRAW_EMPTY_SHOOT;
                break;
            case Field.SHIP:
                if (showShip) {
                    s = CHAR_DRAW_SHIP;
                } else {
                    s = CHAR_DRAW_EMPTY;
                }
                break;
            case Field.SHIP_SHOOT:
                s = CHAR_DRAW_SHIP_SHOOT;
                break;

        }
        return s;
    }

    /**
     * вывод результата хода
     */
    @Override
    public void showResultFire(int result, int x, int y, String playerName) {
        String stringResult = "";
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
        System.out.printf("%s сходил %d,%d  - %s \n", playerName, x + 1, y + 1, stringResult);
    }


    @Override
    public void showWinner(PlayerBase player) {
        System.out.println();
        // если корабли проигравшей стороны скрыты, то показываем их
        if (!player.getField().isShowShip()) {
            player.getField().setShowShip(true);
            //  drawFields();
        }
        System.out.println();
        System.out.println("Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура! Ура!");
        System.out.println(player.getName() + ", Вы потопили все корабли и победили ! \n Поздравляю !");
    }

    @Override
    public void setFieldSize(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public Point getShotCoordinate(PlayerBase player) {
        boolean res;
        int x = 0;
        int y = 0;

        do {
            res = true;
            try {
                System.out.println();
                System.out.println(player.getName() + ", укажите координаты X,Y через запятую ( 1-10,1-10 ):");
                String[] s = scanner.next().split(",");
                if (s.length > 1) {
                    x = Integer.parseInt(s[0]) - 1;
                    y = Integer.parseInt(s[1]) - 1;
                } else res = false;
                if (x < 0 || x > maxX - 1 || y < 0 || y > maxY - 1) res = false;
            } catch (Exception e) {
                res = false;
            }
            if (!res) {
                System.out.println();
                System.out.print("Не допустимые значения координат!");
            } else if (player.getField().getCell(x, y) == Field.EMPTY_SHOOT || player.getField().getCell(x, y) == Field.SHIP_SHOOT) {
                System.out.println();
                System.out.print(player.getName() + ", сюда уже стреляли!");
                res = false;
            }


        } while (!res);
        return new Point(x, y);
    }

    @Override
    public boolean isCoorinateReady() {
        return true;
    }

}
