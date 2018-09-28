package seb.algo.rithmen;

import javax.swing.*;
import java.awt.*;
import java.math.BigInteger;
import java.util.ArrayList;

public class Main extends JFrame {

    ArrayList<Ways[]> success = new ArrayList<>();

    int width = 500;

    enum Ways {
        RIGHT,
        UP,
        DOWN,
        UP_LEFT
    }

    public Main() {
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        goThrough(5);
    }

    public void goThrough(int n) {
        BigInteger posibilitys = new BigInteger("4");
        posibilitys = posibilitys.pow(2 * n);
        //BigInteger posibilitys = new BigInteger(Math.BigInteger.pow(4, (2*n)));
        System.out.println(posibilitys.longValue());
        Ways[] w = new Ways[2 * n];

        for (int i = 0; i < w.length; i++) {
            w[i] = Ways.RIGHT;
        }

        for (BigInteger i = new BigInteger("0"); i.compareTo(posibilitys) == -1; i = i.add(new BigInteger("1"))) {
            Ways[] path = new Ways[2 * n];

            int x = 0;
            int y = 0;

            for (int j = 0; j < 2 * n; j++) {
                path[j] = w[j];
                switch (path[j]) {
                    case RIGHT:
                        x++;
                        break;
                    case DOWN:
                        y--;
                        break;
                    case UP:
                        y++;
                        break;
                    case UP_LEFT:
                        x--;
                        y++;
                        break;
                }

                if (x < 0 || y < 0) {
                    break;
                }
            }

            if (x == 0 && y == n) {
                success.add(path);
            }

            int end = w.length - 1;

            for (int k = w.length - 1; k >= end; k--) {
                switch (w[k]) {
                    case RIGHT:
                        w[k] = Ways.DOWN;
                        break;
                    case DOWN:
                        w[k] = Ways.UP;
                        break;
                    case UP:
                        w[k] = Ways.UP_LEFT;
                        break;
                    case UP_LEFT:
                        w[k] = Ways.RIGHT;

                        if (end > 0) {
                            end--;
                        }
                        break;
                }
            }
        }


        System.out.println(success.size());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.

        Graphics2D g2 = (Graphics2D) g;
        int step =  (width - 10) / success.get(0).length;

//        for (int i = 0; i < success.size(); i++) {
//            Ways[] w = success.get(i);
//
//            int x = 10;
//            int y = width - 10;
//            int offsetX = 10;
//            int offsetY = width - 10;
//
//            for (int j = 0; j < w.length; j++) {
//                Line2D lin = null;
//                switch (w[j]) {
//                    case RIGHT:
//                        offsetX += step;
//                        break;
//                    case DOWN:
//                        offsetY += step;
//                        break;
//                    case UP:
//                        offsetY -= step;
//                        break;
//                    case UP_LEFT:
//                        offsetY -= step;
//                        offsetX -= step;
//                        break;
//                }
//                    lin = new Line2D.Float(x, y, offsetX, offsetY);
//                    g2.draw(lin);
//                    x = offsetX;
//                    y = offsetY;
//            }
//        }
    }


    public static void main(String[] args) {
        JFrame j = new Main();
    }
}
