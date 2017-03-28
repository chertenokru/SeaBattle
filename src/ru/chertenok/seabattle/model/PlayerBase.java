package ru.chertenok.seabattle.model;

import ru.chertenok.seabattle.model.Field;

import java.awt.*;

/**
 * Created by 13th on 11.02.2017.
 */
public abstract class PlayerBase {

    public Field getField() {
        return field;
    }

    protected Field field;
    int maxX;
    int maxY;

    public String getName() {
        return name;
    }

    protected String name;

    public abstract Point getShootCoordinate();
    public abstract void sendFireResult(int result,Point coord);

    public PlayerBase(Field field, int maxX, int maxY) {
        this.field = field;
        this.maxX = maxX;
        this.maxY = maxY;
    }
}
