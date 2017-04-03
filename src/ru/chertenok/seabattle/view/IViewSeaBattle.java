package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerBase;

import java.awt.*;

/**
 * Created by 13th on 29-Mar-17.
 */
public interface IViewSeaBattle {

    void startGame();

    String getPlayerName();

    void drawFields(Field field1, Field field2);

    void showResultFire(int result, int x, int y, String playerName);

    void showWinner(PlayerBase player);

    void setFieldSize(int maxX, int MaxY);

    Point getShotCoordinate(PlayerBase player);

    boolean isCoordinateReady();
}
