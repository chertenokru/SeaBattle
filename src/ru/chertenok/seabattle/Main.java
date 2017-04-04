package ru.chertenok.seabattle;

import ru.chertenok.seabattle.controller.GameSeaBattle;
import ru.chertenok.seabattle.view.ConsoleIView;
import ru.chertenok.seabattle.view.FormIView;
import ru.chertenok.seabattle.view.IViewSeaBattle;

import java.util.Scanner;



/**
 * Created by 13th on 09.02.2017.
 */
public class Main {
// Идея - код переносимый между консолью, джавой, андроидом
// отличаются они условно говоря способом ввода и отображения

// уже понял, что копаю не в ту сторону, видимо надо переделать, чтобы основной код был во вью,
//  и вью запускала потоком работу GameDeaBattle а может и нет, запутался

//  графический режим не рабочий, только начал копать, отображает только форму и поля


    public static void main(String[] args) {
        IViewSeaBattle view;
        Scanner sc = new Scanner(System.in);
        System.out.printf("Выберите тип интерфейса: %n 1. Консоль %n 2. Графический %n :  ");
        Integer  mode = 0;
        try {
            mode =  sc.nextInt();
        }
        catch(Exception e) {}

        if (mode == 1) {
            view = new ConsoleIView();
        } else
            view = new FormIView();

        new GameSeaBattle(view);
    }



}