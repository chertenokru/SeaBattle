package ru.chertenok.seabattle.controller;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerAutoFullStupid;
import ru.chertenok.seabattle.model.PlayerBase;
import ru.chertenok.seabattle.model.PlayerManual;
import ru.chertenok.seabattle.view.IViewSeaBattle;

import java.awt.*;


/**
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

    /**
     * ход переданного  параметром игрока
     */
    private void playerTurn(PlayerBase player) {
        Point firePoint = new Point(0, 0);
        // вытаскиваем поле из игрока
        Field field = player.getField();
        int result;
        do {
            //  если надо, то обновляем вывод поля
            if (!field.isShowShip()) view.drawFields(fieldAuto, fieldManual);

            // если игрок умеет выдавать координаты то спрашиваем их
            if (player.isCanReturnCoordinate()) {
                firePoint = player.getShootCoordinate();
            } else
            // если нет, то ждём координаты от вьюхи, пока она не выставит флаг что они готовы
            {
                while (!view.isCoorinateReady()) {
                }
                firePoint = view.getShotCoordinate(player);
            }

            // спрашиваем у поля - ну чё там ?
            result = field.fire(firePoint.x, firePoint.y);
            // сообщаем игроку результат стрельбы, по идее можно слушателем сделать внутри поля
            player.sendFireResult(result);
            // визуализация итога выстрела во вью
            view.showResultFire(result, firePoint.x, firePoint.y, player.getName());
        }
        // ну и цикл пока не промахнётся или игра не закончится
        while ((result == Field.SHIP_SHOOT || result == Field.SHIP_FIRED) && !field.isGameOver());
    }


    /**
     * создаём объекты
     */
    private void initGame() {
        // поле компьютера
        fieldAuto = CreateAndInitField(false, true);
        // поле игрока
        fieldManual = CreateAndInitField(true, true);
        // игрок компьютер
        playerAuto = new PlayerAutoFullStupid(fieldManual);
        // игрок
        playerManual = new PlayerManual(fieldAuto);

        // во вью размер передаём (ой ли... наоборот же)
        view.setFieldSize(maxX, maxY);
        // имя
        playerManual.setName(view.getPlayerName());
        // стартуем !
        view.startGame();

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

}
