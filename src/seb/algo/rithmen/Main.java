package seb.algo.rithmen;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Main extends JFrame {

    private Map<List<Integer>, BigInteger> memo = new HashMap<>();

    ArrayList<ArrayList<Ways[]>> success = new ArrayList<>();

    int[][] sets;

    BigInteger wege=BigInteger.ZERO;
    int width = 200;

    enum Ways {
        RIGHT,
        UP,
        DOWN,
        UP_LEFT
    }

    public Main() {
//        setSize(1000, 500);
//        setVisible(true);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        BigInteger before = BigInteger.ZERO;
        for (int i = 0; i <= 300; i++) {
            memo = new HashMap<>();
            BigInteger t = walk(0, 0 , i*2, i);
//            System.out.println("n=" + i + " : " + t.add(before));
            System.out.println("n=" + i + " : " + t);
            before = t;
        }
    }

    public BigInteger walk(int x, int y, int step, int n) {
        if (x < 0 || y < 0) {
            return BigInteger.ZERO;
        }

        if (step == 0) {
            if(x == 0 && y == n) {
                return BigInteger.ONE;
            }
            return BigInteger.ZERO;
        }
        if (x > n) {
            return BigInteger.ZERO;
        }
//        if (step < (n - y)) {
//            return BigInteger.ZERO;
//        }
//        if (step < x) {
//            return BigInteger.ZERO;
//        }
        if (!check(x, y, step, n)) {
            return BigInteger.ZERO;
        }


        step--;
        List<Integer> werte = new ArrayList<Integer>();
        werte.add(x);
        werte.add(y);
        werte.add(step);
        werte.add(n);

        if (memo.containsKey(werte)) {
            return memo.get(werte);
        }

        BigInteger result =  walk(x + 1, y, step, n)
                .add(walk(x, y + 1, step, n))
                .add(walk(x, y - 1, step, n))
                .add(walk(x - 1, y + 1, step, n));

        memo.put(werte, result);

        return result;
    }

    public boolean check(int x, int y, int step, int n) {
        if (x > 0) {
            step -= x;
            y += x;
        }

        while (y > n) {
            step--;
            y--;
        }
        while (y < n) {
            step--;
            y++;
        }
        while(step>3){
            step-=2;
        }
        if (step >= 0) {
            return true;
        }
        return false;
    }

    public void goThrough(int n) {
        ArrayList<Ways[]> success = new ArrayList<>();
        BigInteger posibilitys = new BigInteger("4");
        posibilitys = posibilitys.pow(2 * n);
        //BigInteger posibilitys = new BigInteger(Math.BigInteger.pow(4, (2*n)));
//        System.out.println(posibilitys.longValue());
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

        this.success.add(success);
        System.out.println("n=" + n + " Moegliche Pfade: " + success.size());
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);  // fixes the immediate problem.

        Graphics2D g2 = (Graphics2D) g;
        int right = 0;

        for(int p = 0; p < success.size(); p++) {
            int step =  (width - 10) / success.get(p).get(0).length;

            int startX = step * success.get(0).get(0).length + 10 + right;
            int startY = 500;

            for (int i = 0; i < success.get(p).size(); i++) {
                Ways[] w = success.get(p).get(i);

                int x = startX;
                int y = startY;

                int offsetX = x;
                int offsetY = y;

                for (int j = 0; j < w.length; j++) {
                    Line2D lin;
                    switch (w[j]) {
                        case RIGHT:
                            offsetX += step;
                            break;
                        case DOWN:
                            offsetY += step;
                            break;
                        case UP:
                            offsetY -= step;
                            break;
                        case UP_LEFT:
                            offsetY -= step;
                            offsetX -= step;
                            break;
                    }
                    lin = new Line2D.Float(x, y, offsetX, offsetY);
                    g2.draw(lin);
                    x = offsetX;
                    y = offsetY;
                }
            }

            right += width + 15;
        }
    }


    public static void main(String[] args) {
        JFrame j = new Main();
    }
}
