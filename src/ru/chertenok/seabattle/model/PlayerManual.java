package ru.chertenok.seabattle.model;

import java.awt.*;

/** Игрок ... получает ходы из вне
 */
public class PlayerManual extends PlayerBase {


    public PlayerManual(Field field) {
        super(field, field.getSIZE_X(), field.getSIZE_Y());
        isCanReturnCoordinate = false;
    }

    // заглушка
    @Override
    public Point getShootCoordinate() {
        return new Point(0, 0);
    }

    // заглушка
    @Override
    public void sendFireResult(int result) {
    }
}
