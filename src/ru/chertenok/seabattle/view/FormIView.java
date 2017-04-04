package ru.chertenok.seabattle.view;

import ru.chertenok.seabattle.controller.IModelData;
import ru.chertenok.seabattle.model.Field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by 13th on 29-Mar-17.
 */
public class FormIView extends JFrame implements IViewSeaBattle, MouseListener {
    // размер клетки
    private final int CELL_SIZE = 20;
    private final int TOP_MARGIN = 50;
    private final int LEFT_MARGIN_1 = 50;
    private final int LEFT_MARGIN_2 = 350;
    private boolean win = false;
    //
    private IModelData modelData = null;

    // размер поля
    private int maxX;
    private int maxY;

    private int x = 0;
    private int y = 0;
    private int fieldClickNum = -1;


    @Override
    public void startGame(IModelData date) {
        this.modelData = date;
        setSize(maxX * CELL_SIZE + 400, maxY * CELL_SIZE + 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addMouseListener(this);
        // setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        //  super.paint(g);
        Color color = getBackground();
        if (modelData == null) return;
        g.clearRect(0, 0, getWidth(), getHeight());
        DrawField(g, modelData.getFieldToFire(0), LEFT_MARGIN_1, TOP_MARGIN);
        DrawField(g, modelData.getFieldToFire(1), LEFT_MARGIN_2, TOP_MARGIN);


    }

    private void DrawField(Graphics g, Field field, int leftMargin, int topMargin) {


        for (int x = 1; x <= maxX; x++)
            for (int y = 1; y <= maxY; y++) {
                g.setColor(Color.black);

                g.draw3DRect(x * CELL_SIZE + leftMargin, y * CELL_SIZE + topMargin, CELL_SIZE, CELL_SIZE, false);


                switch (field.getCell(x - 1, y - 1)) {
                    case Field.EMPTY:
                        break;
                    case Field.EMPTY_BLOCK:
                    case Field.EMPTY_SHOOT:
                        g.setColor(Color.darkGray);
                        g.fillOval(x * CELL_SIZE + leftMargin + CELL_SIZE / 4, y * CELL_SIZE + topMargin + CELL_SIZE / 4, CELL_SIZE / 2, CELL_SIZE / 2);
                        break;
                    case Field.SHIP:
                        if (field.isShowShip()) {
                            g.setColor(Color.BLUE);
                            g.fill3DRect(x * CELL_SIZE + leftMargin + 2, y * CELL_SIZE + topMargin + 2, CELL_SIZE - 3, CELL_SIZE - 3, true);
                        }
                        break;
                    case Field.SHIP_SHOOT:
                        g.setColor(Color.RED);
                        g.fill3DRect(x * CELL_SIZE + leftMargin + 2, y * CELL_SIZE + topMargin + 2, CELL_SIZE - 3, CELL_SIZE - 3, true);
                        break;
                }

                //   g.draw3DRect(x * CELL_SIZE + 50, y * CELL_SIZE + 50, CELL_SIZE, CELL_SIZE, true);
            }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Tahome", Font.BOLD, 15));
        g.drawString("X : " + this.x, 290, 50);
        g.drawString("Y : " + this.y, 90, 50);
        if (win)
            g.drawString("Ура! Выиграл игрок : " + ((modelData.getFieldToFire(0).isGameOver()) ? modelData.getPlayer(0).getName() : modelData.getPlayer(1).getName()), 90, 550);
    }

    @Override
    public String getPlayerName() {
        return null;
    }

    @Override
    public void drawFields() {
        repaint();
    }


    @Override
    public void showResultFire(int result, int x, int y, int playerNum) {
        repaint();

    }

    @Override
    public void showWinner(int playerNum) {
        win = true;
    }

    @Override
    public void setFieldSize(int maxX, int maxY) {
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public Point getShotCoordinate(int fieldNum) {
        Point p = new Point(x - 1, y - 1);
        fieldClickNum = -1;

        return p;
    }

    @Override
    public boolean isCoordinateReady(int fieldNum) {
        return (fieldNum + 1 == this.fieldClickNum);
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        int x = mouseEvent.getX();
        int y = mouseEvent.getY();
        int cell_x = -1;
        int cell_y = -1;
        int fieldNum = -1;
        // определяем куда кликнули
        if (y >= TOP_MARGIN && y <= TOP_MARGIN + ((maxX + 1) * CELL_SIZE)) {
            cell_y = (y - TOP_MARGIN) / CELL_SIZE;
        }

        if (x >= LEFT_MARGIN_1 && x <= LEFT_MARGIN_1 + ((maxX + 1) * CELL_SIZE)) {
            fieldNum = 1;
            cell_x = (x - LEFT_MARGIN_1) / CELL_SIZE;
        } else if (x >= LEFT_MARGIN_2 && x <= LEFT_MARGIN_2 + ((maxX + 1) * CELL_SIZE)) {
            fieldNum = 2;
            cell_x = (x - LEFT_MARGIN_2) / CELL_SIZE;
        }

        if (cell_x > 0 && cell_y > 0 && fieldNum > -1) {
            this.x = cell_x;
            this.y = cell_y;
            fieldClickNum = fieldNum;
            repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
