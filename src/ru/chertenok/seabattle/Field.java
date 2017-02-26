package ru.chertenok.seabattle;

import java.awt.*;
import java.util.Random;

/**
 * Created by 13th on 09.02.2017.
 */
public class Field {

    public static final int EMPTY = 0;
    public static final int EMPTY_SHOOT = 1; //мимо
    public static final int SHIP = 2;
    public static final int SHIP_SHOOT = 3; // ранен/убит
    public static final int EMPTY_BLOCK = 5; //мимо
    public static final int SHIP_FIRED = 4; // убит, только для результата стрельбы

    private final int SIZE_X;
    private final int SIZE_Y;
    private final int MAX_SHIP_COUNT;
    private Ship[] ships;
    private int[][] cells;
    private boolean showShip = true;
    private boolean showBlocked = false;
    private int shipCount = 0;


    public boolean isShowBlocked() {
        return showBlocked;
    }

    public void setShowBlocked(boolean showBlocked) {
        this.showBlocked = showBlocked;
    }

    public int getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean isShowShip() {
        return showShip;
    }

    public void setShowShip(boolean showShip) {
        this.showShip = showShip;
    }

    public void addShip(Ship ship) {
        if (shipCount < MAX_SHIP_COUNT) {
            this.ships[shipCount] = ship;
            shipCount++;
            drawShipToCells(ship);
        }
    }

    private void drawShipToCells(Ship ship) {
        Ship.ShipCell[] c = ship.getPositions();
        for (int i = 0; i < c.length; i++) {
            cells[c[i].x][c[i].y] = c[i].status;
        }

    }

    public boolean isGameOver() {
        boolean result = true;
        for (int i = 0; i < shipCount; i++) {
            if (!ships[i].isShipFired()) {
                result = false;
                break;
            }
        }
        return result;
    }

    public Field(int SIZE_X, int SIZE_Y, int maxShipCount) {
        this.SIZE_X = SIZE_X;
        this.SIZE_Y = SIZE_Y;
        this.MAX_SHIP_COUNT = maxShipCount;
        cells = new int[SIZE_X][SIZE_Y];

        ships = new Ship[maxShipCount];
    }


    public int getSIZE_X() {
        return SIZE_X;
    }

    public int getSIZE_Y() {
        return SIZE_Y;
    }


    public Ship getShip(int index) {
        return ships[index];
    }

    public int getShipCount() {
        return shipCount;
    }

    public void generateNewShipBySize(int size) {
        Random r = new Random();
        boolean result = true;
        Point[] point = new Point[size];
        do {
            // 0 - по оси y
            // 1 - по оси X
            int vector = r.nextInt(2);
            int x = r.nextInt(getSIZE_X() - ((vector == 0) ? size : 0));
            int y = r.nextInt(getSIZE_Y() - ((vector == 1) ? size : 0));

            for (int i = 0; i < size; i++) {
                point[i] = new Point(x, y);
                x = x + ((vector == 0) ? 1 : 0);
                y = y + ((vector == 1) ? 1 : 0);
            }

            result = checkChipOnField(point);

        } while (!result);
        ships[shipCount] = new Ship(point);
        drawShipToCells(ships[shipCount]);
        shipCount++;
    }

    private boolean checkChipOnField(Point[] point) {
        boolean result = true;
        int x;
        int y;
        for (int i = 0; i < point.length; i++) {

            x = point[i].x;
            y = point[i].y;

            if ( // -1
                    cells[(x > 0) ? x - 1 : x][(y > 0) ? y - 1 : y] == SHIP || cells[x][(y > 0) ? y - 1 : y] == SHIP || cells[(x < (SIZE_X - 1)) ? x + 1 : x][(y > 0) ? y - 1 : y] == SHIP ||
                            // 0
                            cells[(x > 0) ? x - 1 : x][y] == SHIP || cells[x][y] == SHIP || cells[(x < (SIZE_X - 1)) ? x + 1 : x][y] == SHIP ||
                            // +1
                            cells[(x > 0) ? x - 1 : x][y < (SIZE_Y - 1) ? y + 1 : y] == SHIP || cells[x][y < (SIZE_Y - 1) ? y + 1 : y] == SHIP || cells[(x < (SIZE_X - 1)) ? x + 1 : x][y < (SIZE_Y - 1) ? y + 1 : y] == SHIP
                    ) {
                result = false;
                break;
            }
        }
        return result;
    }


    public int fire(int x, int y) {

        if (cells[x][y] == EMPTY_SHOOT) return EMPTY_SHOOT;
        if (cells[x][y] == EMPTY || cells[x][y] == EMPTY_BLOCK) {
            cells[x][y] = EMPTY_SHOOT;
            return EMPTY;
        }

        for (int i = 0; i < shipCount; i++) {
            if (ships[i].checkFireToShip(x, y)) {
                cells[x][y] = SHIP_SHOOT;
                if (ships[i].isShipFired())
                {
                    drawBlockFieldAroundShip(ships[i]);
                    return SHIP_FIRED;
                }else
                {
                    return SHIP_SHOOT;
                }
            }
        }

        return EMPTY;

    }

    public void drawBlockFieldAroundShip(Ship ship) {
        Ship.ShipCell[] positions = ship.getPositions();
        int x;
        int y;
        for (int i = 0; i < positions.length; i++) {
            x = positions[i].x;
            y = positions[i].y;

            setFieldBlock(x - 1, y - 1);
            setFieldBlock(x - 1, y);
            setFieldBlock(x - 1, y + 1);

            setFieldBlock(x, y - 1);
            setFieldBlock(x, y);
            setFieldBlock(x, y + 1);

            setFieldBlock(x + 1, y - 1);
            setFieldBlock(x + 1, y);
            setFieldBlock(x + 1, y + 1);
        }
    }

    private void setFieldBlock(int x, int y) {
        if ((x >= 0) && (x < this.SIZE_X) && (y >= 0) && (y < this.SIZE_Y) && cells[x][y] == EMPTY)
            cells[x][y] = EMPTY_BLOCK;
    }
}