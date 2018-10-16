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

    public BigInteger walks(int n) {
        if (n == 0) return BigInteger.ZERO;
        if (n % 2 == 0) return walk(n, 0, 0,0, n).multiply(new BigInteger("2"));
        return walk(n-1, 0, 0,0, n).multiply(new BigInteger("2"));
    }

    public Main() {
        BigInteger before = BigInteger.ZERO;
        for (int i = 1; i <= 20; i++) {
            memo = new HashMap<>();
            BigInteger t = walk(0, 0,0,0, i);
//            System.out.println("n=" + i + " : " + t.add(before));
            System.out.println("(" + i + ", " + t + ")");
            before = t;
        }
        memo = new HashMap<>();
        BigInteger t = walk(0, 0,0,0, 500);
//            System.out.println("n=" + i + " : " + t.add(before));
        System.out.println("(" + 500 + ", " + t + ")");
    }

    public BigInteger walk(int x, int y, int offsetX, int offsetY, int n) {
        if (x < 0 || y < 0) {
            return BigInteger.ZERO;
        }
        if (x > n || y > n) {
            return BigInteger.ZERO;
        }
        if (x == n && y == 0) {
            return BigInteger.ONE;
        }


        List<Integer> werte = new ArrayList<Integer>();
        werte.add(x);
        werte.add(y);
        werte.add(offsetX);
        werte.add(offsetY);
        werte.add(n);

        if (memo.containsKey(werte)) {
            return memo.get(werte);
        }
        BigInteger result;

        if (offsetX == 1 && offsetY == 0) {
            result = walk(x - 1, y + 1, -1, 1, n)
                    .add(walk(x + 1, y, 1, 0, n))
                    .add(walk(x + 1, y - 1, 1, -1, n))
                    .add(walk(x + 1, y + 1, 1, 1, n));
        }else if (offsetX == 0 && offsetY == 1) {
            result = walk(x - 1, y + 1, -1, 1, n)
                    .add(walk(x, y + 1, 0, 1, n))
                    .add(walk(x + 1, y - 1, 1, -1, n));
        }else if (offsetX == -1 && offsetY == 1) {
            result= walk(x - 1, y + 1, -1, 1, n)
                    .add(walk(x + 1, y, 1, 0, n))
                    .add(walk(x, y + 1, 0, 1, n))
                    .add(walk(x + 1, y + 1, 1, 1, n));
        }else if (offsetX == 1 && offsetY == -1) {
            result = walk(x + 1, y - 1, 1, -1, n)
                    .add(walk(x + 1, y, 1, 0, n))
                    .add(walk(x, y + 1, 0, 1, n))
                    .add(walk(x + 1, y + 1, 1, 1, n));
        }else if (offsetX == 1 && offsetY == 1) {
            result = walk(x + 1, y - 1, 1, -1, n)
                    .add(walk(x + 1, y, 1, 0, n))
                    .add(walk(x - 1, y + 1, -1, 1, n))
                    .add(walk(x + 1, y + 1, 1, 1, n));
        }else {
            result = walk(x + 1, y - 1, 1, -1, n)
                    .add(walk(x + 1, y, 1, 0, n))
                    .add(walk(x, y + 1, 0, 1, n))
                    .add(walk(x - 1, y + 1, -1, 1, n))
                    .add(walk(x + 1, y + 1, 1, 1, n));
        }

        memo.put(werte, result);

        return result;
    }


    public static void main(String[] args) {
        JFrame j = new Main();
    }
}
