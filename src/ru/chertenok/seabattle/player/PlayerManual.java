package ru.chertenok.seabattle.player;

import ru.chertenok.seabattle.Field;

import java.awt.*;
import java.util.Scanner;

/**
 * Created by 13th on 11.02.2017.
 */
public class PlayerManual extends PlayerBase {
    private static Scanner scanner = new Scanner(System.in);


    public PlayerManual(Field field) {
        super(field, field.getSIZE_X(), field.getSIZE_Y());
        System.out.println();
        System.out.println("Введите Ваше имя: ");
        name = scanner.next();
    }

    @Override
    public Point getShootCoordinate() {
        boolean res;
        int x = 0;
        int y = 0;

        do {
            res = true;
            try {
                System.out.println();
                System.out.print(name + ", укажите координаты X,Y через запятую ( 1-10,1-10 ):");
                String[] s = scanner.next().split(",");
                if (s.length >1) {
                x = Integer.parseInt(s[0]) - 1;
                y = Integer.parseInt(s[1]) - 1;
                } else  res = false;
                if (x < 0 || x > maxX - 1 || y < 0 || y > maxY - 1) res = false;
            } catch (Exception e) {
                res = false;
            }
            if (!res) {
                System.out.println();
                System.out.print("Не допустимые значения координат!");
            } else
            if (field.getCell(x, y) == Field.EMPTY_SHOOT || field.getCell(x, y) == Field.SHIP_SHOOT) {
                System.out.println();
                System.out.print(name + ", сюда уже стреляли!");
                res = false;
            }


        } while (!res);

        return new Point(x, y);
    }

    @Override
    public void sendFireResult(int result, Point coord) {

    }
}
