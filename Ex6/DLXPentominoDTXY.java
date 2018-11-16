import java.util.*;

/**
 * Algorithmen Uebung 6.
 *
 * @author Tobias Müller              193683
 * @author Peter Tim Oliver Nauroth   198322
 * @author Lukas Reichert             199034
 */
public class DLXPentominoDTXY {

  private int n;
  private int columns = 6;

  private DLXNode h;
  private int cnt;
  private HashMap<String, int[][]> shapes;
  private List<int[]> coverageList;

  /**
   * Konstruktor.
   */
  public DLXPentominoDTXY() {
    // Erstelle die Formen. (Pentomino's + Domino)
    init();

    Scanner s = new Scanner(System.in);
    System.out.println("Bitte geben Sie \"n\" ein: (Zum beenden \"hguone\" eingeben)");

    // Programm loop.
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
        n = Integer.parseInt(input);

        // Objekte zuruecksetzten.
        coverageList = new ArrayList<>();
        cnt = 0;

        // Jede Form in die Matrix stellen und heraus finde ob diese hinein passt.
        // Daraus ergibt sich dann die Ueberdeckungsmatrix. ("coverageList")
        shapes.forEach((key, shape) -> createCoverageMatrix(shape));

        // Ueberdeckungsmatrix in int[][] array umwandeln.
        int[][] coverageMat = new int[coverageList.size()][n * columns];
        coverageList.toArray(coverageMat);

        // LinkedList Matrix erstellen.
        createMatrix(coverageMat);
        // Durch die LinkedList gehen und die Moelichkeiten zaehlen.
        search(0);

        // Anzahl der Moeglichkeiten anzeigen.
        System.out.println("a(" + n + ") = " + cnt);
        System.out.println();

      } catch (Exception e) {
        System.out.println("Unguelitige Eingabe.");
      }
    }

  }

  /**
   * Erstellt die Formen die Einzusetzten sind.
   */
  private void init() {
    // Shapes Objekt inizialisieren.
    shapes = new HashMap<>();

    // Basis Formen erstellen.
    int[][] domi = new int[][]{{1}, {1}}; // Domion
    int[][] t = new int[][]{{1, 1, 1}, {0, 1, 0}, {0, 1, 0}}; // T-Pentomino
    int[][] x = new int[][]{{0, 1, 0}, {1, 1, 1}, {0, 1, 0}}; // X-Pentomino
    int[][] y = new int[][]{{0, 1}, {1, 1}, {0, 1}, {0, 1}}; // Y-Pentomino

    // Formen hinzufügen.
    addShapes(domi);
    addShapes(t);
    addShapes(x);
    addShapes(y);
  }

  /**
   * Rotiert und Spiegelt jede uebergebene form und speichert diese in die
   * "shapes" HashMap.
   *
   * @param mat Form die hinzuzufuegen ist.
   */
  private void addShapes(int[][] mat) {
    // Durch jede Spiegelung gehen.
    for (int i = 0; i < 2; i++) {
      // Matrix spiegeln.
      mat = MatOp.mirror(mat);
      // Hinzufuegen falls nicht vorhanden.
      if (!shapes.containsKey(MatOp.toString(mat))) {
        shapes.put(MatOp.toString(mat), mat);
      }
      int[][] temp = mat;
      for (int j = 0; j < 3; j++) {
        // Matrix rotieren.
        temp = MatOp.rotate(temp);
        // Hinzufuegen falls nicht vorhanden.
        if (!shapes.containsKey(MatOp.toString(temp))) {
          shapes.put(MatOp.toString(temp), temp);
        } else if (i == 1) {
          // Falls das gespiegelt schon darin ist sind alle rotationen auch schon darin
          // deswegen kann man es abbrechnen.
          break;
        }
      }
    }
  }

  /**
   * Schaut wo die Form ueberall in die Matrix passt.
   *
   * @param shape die zu ueberpruefen ist
   */
  private void createCoverageMatrix(int[][] shape) {
    // Wie oft man es versuchen muss. (1 mal durch die ganze Matrix (Oben links bis unten rechts))
    int trys = n * columns;

    // Um wie viel die Form verschoben wird in der Matrix.
    int offsetX = 0;
    int offsetY = 0;

    for (int t = 0; t < trys; t++) {
      // Ergebnisse speichern.
      int[] result = new int[n * columns];
      boolean added = false;

      // Form einsetzten in die Matrix. (Zeile)
      for (int i = 0; i < shape.length; i++) {
        // Aus der Matrix so abbrechen.
        if (offsetX + shape[0].length > n || offsetY + shape.length > columns) {
          break;
        }
        // Form einsetzten in die Matrix. (Spalte)
        for (int j = 0; j < shape[i].length; j++) {
          // Schauen ob es die Form ist. (0 = platzhalter; 1 = Form)
          if (shape[i][j] == 1) {
            added = true;
            // In das Ergebnis hinzufuegen.
            result[(offsetX + j) + ((i + offsetY) * n)] = 1;
          }
        }
      }

      // Loesung hinzufugen in die Ueberdeckungsmatrix.
      if (added) {
        coverageList.add(result);
      }

      // x Achse weitergehen.
      offsetX++;

      // Am rechten Rand ueberpruefen.
      if (offsetX >= n) {
        // Wieder vorne anfangen und 1 nachunten gehen.
        offsetX = 0;
        offsetY++;
      }

    }

  }

  /**
   * Erstellt die LinkedList Matrix (DLX)
   *
   * @param coverageMat Ueberdeckungsmatrix
   */
  private void createMatrix(int[][] coverageMat) {
    // H node erstellen.
    h = new DLXNode();

    // Spalten header zwischen speichern.
    DLXNode[] headers = new DLXNode[columns * n];

    // Durch jede Zeile gehen.
    for (int r = 0; r < coverageMat.length; r++) {
      // Zeilen anfang zwischenspeichern.
      DLXNode rowN = null;

      // Durch jede Spalte gehen.
      for (int c = 0; c < coverageMat[0].length; c++) {
        // Wenn es die erste Zeile ist:
        if (r == 0) {
          // Spalten Header hinzuefuegen.
          DLXNode header = new DLXNode();

          // Links setzten.
          header.L = h.L;
          header.R = h;
          h.L.R = header;
          h.L = header;

          // In Array einfuegen.
          headers[c] = header;
        }

        // Ueberpruefen ob ein DLXNode eingefuegt werden muss.
        if (coverageMat[r][c] == 1) {
          DLXNode n = new DLXNode();
          // Ueberpruefen ob es schon ein Zeilen anfang gibt,
          // falls nicht ist dieser jetzt der Zeilen anfang.
          if (rowN == null) {
            rowN = n;
          }

          // Links setzten.
          n.C = headers[c];
          n.D = headers[c];
          n.U = headers[c].U;
          n.L = rowN.L;
          n.R = rowN;

          // Links vom Spalten und Zeilen Header setzten.
          headers[c].U.D = n;
          headers[c].U = n;
          rowN.L.R = n;
          rowN.L = n;
        }
      }
    }
  }


  /**
   * search tries to find and count all complete coverings of the DLX matrix.
   * Is a recursive, depth-first, backtracking algorithm that finds
   * all solutions to the exact cover problem encoded in the DLX matrix.
   * each time all columns are covered, static long cnt is increased
   *
   * @param k: number of level
   */
  private void search(int k) { // finds & counts solutions
    if (h.R == h) {
      cnt++;
      return;
    }     // if empty: count & done
    DLXNode c = h.R;                   // choose next column c
    cover(c);                          // remove c from columns
    for (DLXNode r = c.D; r != c; r = r.D) {  // forall columns with 1 in c
      for (DLXNode j = r.R; j != r; j = j.R) // forall 1-elements in row
        cover(j.C);                    // remove column
      search(k + 1);                    // recursion
      for (DLXNode j = r.L; j != r; j = j.L) // forall 1-elements in row
        uncover(j.C);                  // backtrack: un-remove
    }
    uncover(c);                        // un-remove c to columns
  }

  /**
   * cover "covers" a column c of the DLX matrix
   * column c will no longer be found in the column list
   * columns i with 1 element in column c will no longer be found
   * in other column lists than c
   * so column c and columns i are invisible after execution of cover
   *
   * @param c: header element of column that has to be covered
   */
  private void cover(DLXNode c) { // remove column c
    c.R.L = c.L;                         // remove header
    c.L.R = c.R;                         // .. from row list
    for (DLXNode i = c.D; i != c; i = i.D)      // forall columns with 1
      for (DLXNode j = i.R; i != j; j = j.R) {   // forall elem in row
        j.D.U = j.U;                     // remove row element
        j.U.D = j.D;                     // .. from column list
      }
  }

  /**
   * uncover "uncovers" a column c of the DLX matrix
   * all operations of cover are undone
   * so column c and columns i are visible again after execution of uncover
   *
   * @param c: header element of column that has to be uncovered
   */
  private void uncover(DLXNode c) {//undo remove col c
    for (DLXNode i = c.U; i != c; i = i.U)      // forall columns with 1
      for (DLXNode j = i.L; i != j; j = j.L) {   // forall elem in row
        j.D.U = j;                       // un-remove row elem
        j.U.D = j;                       // .. to column list
      }
    c.R.L = c;                           // un-remove header
    c.L.R = c;                           // .. to row list
  }

  /**
   * Methode "main".
   *
   * @param args String[]
   */
  public static void main(String[] args) {
    new DLXPentominoDTXY(); // Neues Objekt erzeugen.
  }
}


/**
 * Class DLXNode
 * represents a matrix element of the cover matrix with value 1
 * links go to up down left right neigbors, and column header
 * can also be used as colm header or root of column headers
 * matrix is sparsely coded
 * try to do all operations very efficiently
 * see:
 * http://en.wikipedia.org/wiki/Dancing_Links
 * http://arxiv.org/abs/cs/0011047
 */
class DLXNode {       // represents 1 element or header
  DLXNode C;           // reference to column-header
  DLXNode L, R, U, D;  // left, right, up, down references

  DLXNode() {
    C = L = R = U = D = this;
  } // supports circular lists
}

/**
 * Klass mit statischen methoden um eine Matrix zu manipulieren & anzeigen.
 * Dies wird benoetigt, damit die Objekte leichter erstellt werden können.
 */
abstract class MatOp {

  /**
   * Rotiert die Matrix um 90 Grad im Uhrzeigersinn.
   *
   * @param mat input Matrix
   * @return rotierte Matrix
   */
  public static int[][] rotate(int[][] mat) {
    int m = mat.length;
    int n = mat[0].length;
    // Neue  n x m  Matrix erstellen.
    int[][] ret = new int[n][m];
    for (int r = 0; r < m; r++) {
      for (int c = 0; c < n; c++) {
        ret[c][m - 1 - r] = mat[r][c]; // Wert eintragen.
      }
    }
    return ret;
  }

  /**
   * Druckt die Matrix aus.
   *
   * @param mat zu druckende Matrix
   */
  public static void print(int[][] mat) {
    for (int i = 0; i < mat.length; i++) {
      System.out.println(Arrays.toString(mat[i]));
    }
  }

  /**
   * Konvertiert die Matrix zu einem String.
   *
   * @param mat die zum String gewandelt werden soll.
   */
  public static String toString(int[][] mat) {
    String matrix = "";
    for (int i = 0; i < mat.length; i++) {
      matrix = matrix.concat(Arrays.toString(mat[i]));
    }

    return matrix;
  }

  /**
   * Spiegelt die eingegbene Matrix.
   *
   * @param mat Matrix zum spiegeln
   * @return gespiegelte Matrix
   */
  public static int[][] mirror(int[][] mat) {
    // Neue Matrix erstellen.
    int[][] ret = new int[mat.length][mat[0].length];

    // Externer Zähler. (Reihe)
    int r = 0;
    for (int i = 0; i < mat.length; i++) {
      int c = 0; // Interner Zähler. (Spalte)
      for (int j = (mat[0].length - 1); j >= 0; j--) {
        ret[r][c++] = mat[i][j]; // Austauschen.
      }
      r++;
    }

    return ret;
  }
}