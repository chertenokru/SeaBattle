package ru.chertenok.seabattle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 13th on 11.02.2017.
 */
public class PlayerAuto extends PlayerBase {

    private static int VECTOR_NONE = 0;
    private static int VECTOR_HOR = 1;
    private static int VECTOR_VERT = 2;
    private static int VECTOR_TO_BEGIN = 3;
    private static int VECTOR_TO_END = 4;

    // есть ли ранненый корабль
    private boolean isFireTrue = false;
    // его посл координаты
    private Point pointFire = null;
    // направление его расположения
    private int vectorFire = VECTOR_NONE;

    private static Random random = new Random();


    public PlayerAuto(Field field) {
        super(field, field.getSIZE_X(), field.getSIZE_Y());
        name = "Компьютер";

    }

    @Override
    public Point getShootCoordinate() {
        if (isFireTrue) {
            return findNextCoordinate();
        } else {
            return getRandomCoordinate();
            //return getRandomDiv2Coordinate();
        }
    }

    @Override
    public void sendFireResult(int result, Point coord) {
        if (result == Field.SHIP_FIRED) {
            isFireTrue = false;
            vectorFire = VECTOR_NONE;
        }
    }

    private boolean ChangeCoordIfCoordGood(Point value, int maxValue, int vectorMove) {
        boolean isValid = true;

        if (vectorMove == VECTOR_TO_BEGIN) {
            if (value.x - 1 < 0) {
                isValid = false;
            } else {
                value.setLocation(value.x - 1, 0);
            }
        }

        if (vectorMove == VECTOR_TO_END) {
            if (value.x + 1 > maxValue) {
                isValid = false;
            } else {
                value.setLocation(value.x + 1, 0);
            }
        }


        return isValid;
    }

    private Point findNextCoordinate() {
        int x = 0;
        int y = 0;
        Point coord = null;
        boolean res;
        // если зашли в тупик, на входе всегда истина
        boolean needChangeMove = true;
        res = false;
        int vectorFirePlane = VECTOR_NONE;
        int vectorFireMove = VECTOR_NONE;

        do {
            // сбрасываем на начало и ищем новый вариант
            if (needChangeMove) {
                x = pointFire.x;
                y = pointFire.y;
                if (vectorFire == VECTOR_NONE) {
                    vectorFirePlane = 1 + random.nextInt(2);
                } else {
                    vectorFirePlane = vectorFire;
                }
                vectorFireMove = 3 + random.nextInt(2);
                needChangeMove = false;
            }


            if (vectorFirePlane == VECTOR_HOR) {
                coord = new Point(x, 0);

                if (ChangeCoordIfCoordGood(coord, maxX - 1, vectorFireMove)) {
                    x = coord.x;
                } else {
                    needChangeMove = true;
                }
            }
            if (vectorFirePlane == VECTOR_VERT) {
                coord = new Point(y, 0);
                if (ChangeCoordIfCoordGood(coord, maxY - 1, vectorFireMove)) {
                    y = coord.x;
                } else {
                    needChangeMove = true;
                }
            }


            if ((field.getCell(x, y) == Field.EMPTY) || (field.getCell(x, y) == Field.SHIP)) {
                res = true;
                coord = new Point(x, y);
                if (field.getCell(x, y) == Field.SHIP) {
                    pointFire = coord;
                    vectorFire = vectorFirePlane;
                }
            }

            if ((field.getCell(x, y) == Field.EMPTY_BLOCK) || (field.getCell(x, y) == Field.EMPTY_SHOOT)) {
                needChangeMove = true;
            }

        }
        while (!res);
        return coord;
    }

    private Point getRandomCoordinate() {
        int x;
        int y;
        boolean res;
        do {
            res = true;
            x = random.nextInt(maxX);
            y = random.nextInt(maxY);
            // возможен ли выстрел
            if (field.getCell(x, y) == Field.EMPTY_SHOOT || field.getCell(x, y) == Field.SHIP_SHOOT || field.getCell(x, y) == Field.EMPTY_BLOCK) {
                res = false;
            }
            // если попадание, то сохраняем инфу
            if (field.getCell(x, y) == Field.SHIP) {
                isFireTrue = true;
                pointFire = new Point(x, y);
                vectorFire = VECTOR_NONE;
            }
        } while (!res);
        return new Point(x, y);
    }


    private void checkCell(int x, int y, Integer tempLenght, Integer maxLenght, ArrayList<Point> listTemp, ArrayList<ArrayList<Point>> list) {
        if (field.getCell(x, y) == Field.EMPTY) {
            tempLenght++;
            listTemp.add(new Point(x, y));
        }

        if ((field.getCell(x, y) != Field.EMPTY) || (y == maxY - 1)) //цепочка собрана
        {// есть ли новый рекорд длины?
            if (maxLenght.intValue() < tempLenght.intValue()) {
                // сохраняем новую длину
                maxLenght = tempLenght;
                // очищаем более короткие цепочки
                list.clear();
                // сохраняем новую цепочку
                list.add(listTemp);
            }


            // длина такая же, просто добавляем в список
            if (maxLenght.intValue() == tempLenght.intValue()) {
                list.add(listTemp);
            }

            // очищаем временный список и длину
            listTemp.clear();
            tempLenght = 0;
        }


    }

    private Point getRandomDiv2Coordinate() {
        Integer maxLenght = new Integer(0);
        Integer tempLenghtY = new Integer(0);
        Integer tempLenghtX = new Integer(0);
        ArrayList<Point> listTempX = new ArrayList<Point>();
        ArrayList<Point> listTempY = new ArrayList<Point>();
        ArrayList<ArrayList<Point>> list = new ArrayList<ArrayList<Point>>();


        for (int i = 0; i < maxX; i++) {
            for (int j = 0; j < maxY; j++) {
                //горизонталь
                checkCell(i, j, tempLenghtX, maxLenght, listTempX, list);
                //вертикаль
                checkCell(j, i, tempLenghtY, maxLenght, listTempY, list);
            }
        }

        System.out.printf("x = %d, y = %d  count = %d",tempLenghtX,tempLenghtY,list.size());


            return new Point(0, 0);
    }

}
