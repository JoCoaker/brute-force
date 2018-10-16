import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Walks {

  Map<String, BigInteger> memory;

  private enum Direction {
    RIGHT,      // →
    UP,         // ↑
    UP_LEFT,    // ↖
    DOWN_RIGHT, // ↘
    UP_RIGHT,   // ↗
    DEFAULT,    // Tu nichts (Fuer den Start)
  }

  /**
   * Kontruktor Walks
   */
  public Walks() {
    // Gehe von 0 bis 20 ...
    for (int n = 0; n <= 20; n++) {
      memory = new HashMap<>();
      print(n, walk(n,0, 0, Direction.DEFAULT)); // Starte das durch "laufen" & drucke & starte von vorne.
    }
    memory = new HashMap<>();
    int n = 950;
    print(n, walk(n,0, 0, Direction.DEFAULT));
  }


  private BigInteger walk(int n, int x, int y, Direction d) {
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

  private void print (int n, BigInteger paths) {
    System.out.println("(" + n + ", " + paths + ")");
  }

  public static void main(String[] args) {
    new Walks();
  }
}
