package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.controller.IModelData;

import java.awt.*;

/**
 * Created by 13th on 29-Mar-17.
 */
public interface IViewSeaBattle {

    void startGame(IModelData modelData);

    String getPlayerName();

    void drawFields();

    void showResultFire(int result, int x, int y, int playerNum);

    void showWinner(int playerNum);

    void setFieldSize(int maxX, int MaxY);

    Point getShotCoordinate(int fieldNum);

    boolean isCoordinateReady(int fieldNum);
}
