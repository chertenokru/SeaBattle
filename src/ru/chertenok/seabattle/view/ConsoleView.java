package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerBase;

/**
 * Created by 13th on 28.03.2017.
 */
public class ConsoleView {

    private final char CHAR_DRAW_EMPTY = 9617;
    private final char CHAR_DRAW_EMPTY_SHOOT = 9728;
    private final char CHAR_DRAW_SHIP = 9744;
    private final char CHAR_DRAW_SHIP_SHOOT = 9746;
    private int maxX;
    private int maxY;

    public ConsoleView(int maxX,int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    public  void startGame() {
        System.out.println("Игра началась !");
    }

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


    public void showResultFire(int result,int x, int y, String playerName) {
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

}
