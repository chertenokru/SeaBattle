package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerBase;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 13th on 29-Mar-17.
 */
public class FormIView extends JFrame implements IViewSeaBattle {
    private int maxX;
    private int maxY;

    @Override
    public void startGame() {
        setSize(maxX * 100, maxY * 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);

    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void drawFields(Field field1, Field field2) {

    }

    @Override
    public void showResultFire(int result, int x, int y, String playerName) {

    }

    @Override
    public void showWinner(PlayerBase player) {

    }

    @Override
    public void setFieldSize(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public Point getShotCoordinate(PlayerBase player) {
        return null;
    }

    @Override
    public boolean isCoorinateReady() {
        return false;
    }
}
