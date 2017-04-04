package ru.chertenok.seabattle.controller;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerAutoFullStupid;
import ru.chertenok.seabattle.model.PlayerBase;
import ru.chertenok.seabattle.model.PlayerManual;
import ru.chertenok.seabattle.view.IViewSeaBattle;

import java.awt.*;


/** Игровой класс
 */
public class GameSeaBattle {

    // кол-во и размеры кораблей
    private final int[] shipConf = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    // размер поля
    private final int maxX = 10;
    private final int maxY = 10;
    // поле игрока 1
    private PlayerBase[] player = new PlayerBase[2];
    // интерфейс к данным
    private ModelData modelData = new ModelData();
    // интерфейс к вьюхе
    private IViewSeaBattle view;

    /**
     * Конструктор и сразу запускает игровой цикл     *
     *
     * @param view принимает ссылку на отрисовщик
     */
    public GameSeaBattle(IViewSeaBattle view) {
        this.view = view;
        initGame();
        startGame();
    }

    /**
     * игровой цикл
     */
    private void startGame() {

        // view.drawFields(fieldAuto, fieldManual);
        // игровой цикл
        do {
            // ходы игрока пока не промахнется
            playerTurn(0);

            // если игрок не выиграл
            if (!modelData.getMyField(0).isGameOver()) {
                // ходы след игрока
                playerTurn(1);
            }
        } while (!modelData.getMyField(0).isGameOver() && !modelData.getMyField(1).isGameOver());

        if (modelData.getMyField(0).isGameOver()) {
            view.showWinner(1);
        } else {
            view.showWinner(0);
        }

    }

    /**
     * ход переданного  параметром игрока
     */
    private void playerTurn(int playerNum) {
        Point firePoint;
        // вытаскиваем поле из игрока
        Field field = player[playerNum].getFieldToFire();
        int result;
        do {
            //  если надо, то обновляем вывод поля
            if (!field.isShowShip()) view.drawFields();

            // если игрок умеет выдавать координаты то спрашиваем их
            if (player[playerNum].isCanReturnCoordinate()) {
                firePoint = player[playerNum].getShootCoordinate();
            } else
            // если нет, то ждём координаты от вьюхи, пока она не выставит флаг что они готовы
            {
                while (!view.isCoordinateReady(playerNum)) {
                }
                firePoint = view.getShotCoordinate(playerNum);
            }

            // спрашиваем у поля - ну чё там ?
            result = field.fire(firePoint.x, firePoint.y);
            // сообщаем игроку результат стрельбы, по идее можно слушателем сделать внутри поля
            player[playerNum].sendFireResult(result);
            // визуализация итога выстрела во вью
            view.showResultFire(result, firePoint.x, firePoint.y, playerNum);
        }
        // ну и цикл пока не промахнётся или игра не закончится
        while ((result == Field.SHIP_SHOOT || result == Field.SHIP_FIRED) && !field.isGameOver());
    }

    /**
     * создаём объекты
     */
    private void initGame() {
        // поле компьютера
        Field field = CreateAndInitField(true, true);
        // игрок 1 - компьютер
        player[0] = new PlayerAutoFullStupid(field);

        field = CreateAndInitField(false, true);

        player[1] = new PlayerManual(field);

        // во вью размер передаём (ой ли... наоборот же)
        view.setFieldSize(maxX, maxY);
        // имя

        for (int i = 0; i < player.length; i++)
            if (!player[i].isCanReturnCoordinate()) player[i].setName(view.getPlayerName());

        // стартуем !
        view.startGame(modelData);

    }

    /**
     * Создаём  игровое поле  и расставляем корабли
     * todo - подумать как запросить корабли  у view
     * или переместить создание в объект игрока и там добавить признак кто корабли ставит
     * view или игрок
     *
     * @param showShip    показывать корабли на поле
     * @param showBlocked показывать
     * @return созданное поле
     */

    private Field CreateAndInitField(boolean showShip, boolean showBlocked) {
        // новое поле
        Field field = new Field(maxX, maxY, shipConf.length);
        // признаки
        field.setShowShip(showShip);
        field.setShowBlocked(showBlocked);
        // генерим корабли
        for (int i = 0; i < shipConf.length; i++) {
            field.generateNewShipBySize(shipConf[i]);
        }
        return field;
    }

    class ModelData implements IModelData {
        @Override
        synchronized public Field getMyField(int num) {
            if (num == 1) return player[0].getFieldToFire();
            else return player[1].getFieldToFire();
        }

        @Override
        synchronized public Field getFieldToFire(int num) {
            if (num == 1) return player[1].getFieldToFire();
            else return player[0].getFieldToFire();
        }

        @Override
        synchronized public PlayerBase getPlayer(int num) {
            if (num == 1) return player[0];
            else return player[1];
        }
    }

}
