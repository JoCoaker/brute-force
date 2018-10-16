package seb.algo.rithmen;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Walks extends JFrame {

  private Map<String, BigInteger> memory;

  private enum Direction {
    RIGHT,
    UP,
    UP_LEFT,
    DOWN_RIGHT,
    UP_RIGHT,
    DEFAULT,
  }

  public Walks() {
    for (int n = 0; n <= 20; n++) {
      memory = new HashMap<>();
      BigInteger t = walk(n, 0, 0, Direction.DEFAULT);
      System.out.println("(" + n + ", " + t + ")");
    }
    memory = new HashMap<>();
    int n = 500;
    BigInteger t = walk(n, 0, 0, Direction.DEFAULT);
    System.out.println("(" + n + ", " + t + ")");
  }

  public BigInteger walk(int n, int x, int y, Direction d) {
    if (x < 0 || y < 0 || x > n || y > n || n - x - y < 0) {
      return BigInteger.ZERO;
    }
    if (x == n && y == 0) {
      return BigInteger.ONE;
    }

    String compare = x + "|" + y + "|" + d + "|" + n;

    if (memory.containsKey(compare)) {
      return memory.get(compare);
    }
    BigInteger result;

    switch (d) {
      case UP:
        result = walk(n, x - 1, y + 1, Direction.DOWN_RIGHT)
            .add(walk(n, x, y + 1, Direction.UP))
            .add(walk(n, x + 1, y - 1, Direction.UP_LEFT));
        break;
      case UP_LEFT:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT)
            .add(walk(n, x + 1, y, Direction.RIGHT))
            .add(walk(n, x, y + 1, Direction.UP))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT));
        break;
      case RIGHT:
      case UP_RIGHT:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT)
            .add(walk(n, x + 1, y, Direction.RIGHT))
            .add(walk(n, x - 1, y + 1, Direction.DOWN_RIGHT))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT));
        break;
      case DOWN_RIGHT:
        result = walk(n, x - 1, y + 1, Direction.DOWN_RIGHT)
            .add(walk(n, x + 1, y, Direction.RIGHT))
            .add(walk(n, x, y + 1, Direction.UP))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT));
        break;
      default:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT)
            .add(walk(n, x + 1, y, Direction.RIGHT))
            .add(walk(n, x, y + 1, Direction.UP))
            .add(walk(n, x - 1, y + 1, Direction.DOWN_RIGHT))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT));
        break;
    }

    memory.put(compare, result);

    return result;
  }


  public static void main(String[] args) {
    new Walks();
  }
}