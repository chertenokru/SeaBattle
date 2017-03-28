package ru.chertenok.seabattle.model;

import ru.chertenok.seabattle.model.Field;

import java.awt.*;

/**
 * Created by 13th on 09.02.2017.
 */
public class Ship {
    private int firePoint = 0;
    private final ShipCell[] position;

    public Ship(Point[] position) {
        this.position = new ShipCell[position.length];
        for (int i = 0; i < position.length; i++) {
            this.position[i] = new ShipCell();
            this.position[i].x = position[i].x;
            this.position[i].y = position[i].y;
            this.position[i].status = Field.SHIP;
        }

    }

    public ShipCell[] getPositions() {
        return position;
    }

    public boolean checkFireToShip(int x, int y) {
        boolean result = false;
        for (int i = 0; i < this.position.length; i++) {
            if (x == position[i].x && y == position[i].y && position[i].status == Field.SHIP) {
                firePoint++;
                result = true;
                position[i].status = Field.SHIP_SHOOT;
                break;
            }
        }
        return result;
    }

    public boolean isShipFired() {
        return firePoint == position.length;
    }

    public int getShipSize() {
        return position.length;
    }

    class ShipCell {
        public int x;
        public int y;
        public int status;
    }

}