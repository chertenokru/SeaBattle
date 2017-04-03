package ru.chertenok.seabattle.controller;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerAutoFullStupid;
import ru.chertenok.seabattle.model.PlayerBase;
import ru.chertenok.seabattle.model.PlayerManual;
import ru.chertenok.seabattle.view.IViewSeaBattle;

import java.awt.*;


/**
 * Created by 13th on 28.03.2017.
 */
public class GameSeaBattle {

    // кол-во и размеры кораблей
    private final int[] shipConf = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private final int maxX = 10;
    private final int maxY = 10;
    private Field fieldAuto;
    private Field fieldManual;
    private PlayerBase playerManual;
    private PlayerBase playerAuto;
    private IViewSeaBattle view;


    public GameSeaBattle(IViewSeaBattle view) {
        this.view = view;

        initGame();
        startGame();


    }

    private void startGame() {
        view.drawFields(fieldManual,fieldAuto);

        // игровой цикл
        do {
            // ходы игрока пока не промахнется
            playerTurn(playerManual);

            // если игрок не выиграл
            if (!fieldAuto.isGameOver()) {
                // ходы компьютера пока не промахнется
                playerTurn(playerAuto);
            }
        } while (!fieldAuto.isGameOver() && !fieldManual.isGameOver());

        if (fieldAuto.isGameOver()) {
            view.showWinner(playerManual);
        } else {
            view.showWinner(playerAuto);
        }

    }

    private void playerTurn(PlayerBase player) {
        Point firePoint = new Point(0,0);
        Field field;
        int result;
        field = player.getField();
        do {
            if (!field.isShowShip()) view.drawFields(fieldAuto, fieldManual);

            if (player.isCanReturnCoordinate()) {
                firePoint = player.getShootCoordinate();
            } else {
                while (view.isCoorinateReady()) {
                    firePoint = view.getShotCoordinate(player);
                }
            }



            result = field.fire(firePoint.x, firePoint.y);
            view.showResultFire(result, firePoint.x, firePoint.y, player.getName());
            player.sendFireResult(result);
        } while ((result == Field.SHIP_SHOOT || result == Field.SHIP_FIRED) && !field.isGameOver());
    }


    //создаём объекты
    private void initGame() {
        fieldAuto = CreateAndInitField(false, true);
        fieldManual = CreateAndInitField(true, true);
        playerAuto = new PlayerAutoFullStupid(fieldManual);
        playerManual = new PlayerManual(fieldAuto);

        view.setFieldSize(maxX, maxY);
        playerManual.setName(view.getPlayerName());

        view.startGame();

    }

    private Field CreateAndInitField(boolean showShip, boolean showBlocked) {
        Field field = new Field(maxX, maxY, shipConf.length);
        field.setShowShip(showShip);
        field.setShowBlocked(showBlocked);

        for (int i = 0; i < shipConf.length; i++) {
            field.generateNewShipBySize(shipConf[i]);
        }

        return field;
    }


}
