import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Algorithmen Uebung 4.
 *
 * @author Tobias Müller              193683
 * @author Peter Tim Oliver Nauroth   198322
 * @author Lukas Reichert             199034
 */
public class WalkNodes {

  private Map<String, BigInteger> memory;

  private enum Direction {
    RIGHT,      // →
    UP,         // ↑
    UP_LEFT,    // ↖
    DOWN_RIGHT, // ↘
    UP_RIGHT,   // ↗
    DEFAULT,    // Tu nichts (Fuer den Start)
  }

  /**
   * Konstruktor.
   */
  public WalkNodes() {
    Scanner s = new Scanner(System.in);

    System.out.println("Bitte geben Sie \"n\" ein: (Zum beenden \"hguone\" eingeben)");
    while (true) {
      String input;
      try {
        // Eingabe einlesen.
        input = s.nextLine();

        //Überprüfen ob man rausgehen soll.
        if (input.equals("hguone")) {
          System.out.println("bye");
          return;
        }
        // Zu int konvertieren.
        int n = Integer.parseInt(input);

        memory = new HashMap<>();
        // Punkte zählen.
        print(n, walk(n, 0, 0, Direction.DEFAULT, BigInteger.ZERO));
      } catch (Exception e) {
        System.out.println("Unguelitige Eingabe.");
      }
    }
  }

  /**
   * Rekurive funktion fuer das laufen.
   *
   * @param n     int
   * @param x     int
   * @param y     int
   * @param d     Direction
   * @param steps BigInteger
   * @return BigInteger
   */
  private BigInteger walk(int n, int x, int y, Direction d, BigInteger steps) {
    steps = steps.add(BigInteger.ONE);

    // Ueberfluessige koennen von anfang an nicht beachtet werden.
    if (x < 0 || y < 0 || x > n || y > n || n - x - y < 0) {
      return BigInteger.ZERO;
    }
    // Ueberpruefen ob man am ziel angekommen ist.
    if (x == n && y == 0) {
      return steps;
    }

    // Ueberpruefen ob der Wert schon im cache liegt. (D.P.)
    String compare = d.toString() + "|" + x + "|" + y + "|" + steps;
    if (memory.containsKey(compare)) {
      return memory.get(compare);
    }

    BigInteger result;

    // Einschraenkungen beachten und rekursiv weiter laufen.
    switch (d) {
      case UP:
        result = walk(n, x - 1, y + 1, Direction.DOWN_RIGHT, steps)
            .add(walk(n, x, y + 1, Direction.UP, steps))
            .add(walk(n, x + 1, y - 1, Direction.UP_LEFT, steps));
        break;
      case UP_LEFT:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT, steps)
            .add(walk(n, x + 1, y, Direction.RIGHT, steps))
            .add(walk(n, x, y + 1, Direction.UP, steps))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT, steps));
        break;
      case RIGHT:
      case UP_RIGHT:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT, steps)
            .add(walk(n, x + 1, y, Direction.RIGHT, steps))
            .add(walk(n, x - 1, y + 1, Direction.DOWN_RIGHT, steps))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT, steps));
        break;
      case DOWN_RIGHT:
        result = walk(n, x - 1, y + 1, Direction.DOWN_RIGHT, steps)
            .add(walk(n, x + 1, y, Direction.RIGHT, steps))
            .add(walk(n, x, y + 1, Direction.UP, steps))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT, steps));
        break;
      default:
        result = walk(n, x + 1, y - 1, Direction.UP_LEFT, steps)
            .add(walk(n, x + 1, y, Direction.RIGHT, steps))
            .add(walk(n, x, y + 1, Direction.UP, steps))
            .add(walk(n, x - 1, y + 1, Direction.DOWN_RIGHT, steps))
            .add(walk(n, x + 1, y + 1, Direction.UP_RIGHT, steps));
        break;
    }

    // Ergebnis im cache speichern. (D.P.)
    memory.put(compare, result);

    return result;
  }

  /**
   * Ausdrucken in der Konsole.
   *
   * @param n      int
   * @param points BigInteger
   */
  private void print(int n, BigInteger points) {
    System.out.println("n=" + n + " -> Punkte: " + points);
  }

  /**
   * main Methode.
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    new WalkNodes(); // Neues Objekt erzeugen.
  }
}
