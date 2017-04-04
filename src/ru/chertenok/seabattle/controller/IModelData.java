package ru.chertenok.seabattle.controller;

import ru.chertenok.seabattle.model.Field;
import ru.chertenok.seabattle.model.PlayerBase;

/** Интерфейс для получения вьюшкой данных для отрисовки *
 */
public interface IModelData {
     Field getMyField(int num);

     Field getFieldToFire(int num);
     PlayerBase getPlayer(int num);
}
