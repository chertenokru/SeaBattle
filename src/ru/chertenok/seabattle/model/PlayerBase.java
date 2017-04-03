package ru.chertenok.seabattle.model;

import java.awt.*;

/**  Базовый класс Игрок, используется в игре, для обращения к конкретной реализации
 *  и реализует общий код
 */
public abstract class PlayerBase {

    /** поле */
    protected Field field;
    /** может ли  ходить сам или получает ход из вне */
    protected boolean isCanReturnCoordinate = false;
    protected String name;
    int maxX;
    int maxY;
    public PlayerBase(Field field, int maxX, int maxY) {
        this.field = field;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    /**Возвращает ссылку на поле игрока
     * @return поле игрока
     */
    public Field getField() {
        return field;
    }

    /** Может ли игрок сам ходить, если может, то можно вызвать у него getShootCoordinate
     * @return true - может вернуть ход
     */
    public boolean isCanReturnCoordinate() {
        return isCanReturnCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract Point getShootCoordinate();

    public abstract void sendFireResult(int result);
}
